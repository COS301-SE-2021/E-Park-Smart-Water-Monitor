package za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.models.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.models.WaterLevelProjectionStrategy;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.requests.DeviceProjectionRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.responses.DeviceProjectionResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.DevicesService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.FindDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.FindWaterSiteByDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.GetDeviceDataRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.FindDeviceResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.FindWaterSiteByDeviceResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.GetDeviceDataResponse;

@Service("AnalyticsServiceImpl")
public class AnalyticsServiceImpl implements AnalyticsService
{

    @Value("${weather.apikey}")
    private String apikey;
    @Value("${weather.url}")
    private String weatherUrl;
    @Value("${arima.model.url}")
    private String arimaUrl;
    private final DevicesService devicesService;
    private final WaterSiteService waterSiteService;
    private ProjectionStrategyInterface projectionStrategy;

    @Autowired
    public AnalyticsServiceImpl(@Qualifier("DeviceServiceImpl") DevicesService devicesService,
                                @Qualifier("WaterSiteServiceImpl") WaterSiteService waterSiteService)
    {
        this.devicesService = devicesService;
        this.waterSiteService = waterSiteService;
    }

    /**
     * AnalyticsServiceImpl/deviceProjection acts as the entry point for the Prediction Strategy.
     * Provides initial error checking
     * Act as the entry point for the various prediction strategies.
     * Temperature Prediction Strategy
     * Water Quality Prediction Strategy
     * Water Level Prediction Strategy
     *
     * @param request DeviceProjectionRequest object. Provides the length of the prediction i.e. days into the
     *                future user wants a prediction for.
     *                String type of projection they want. "waterlevel", "ph", "temperature"
     *                UUID id of the specific device(which monitors a water site) they want a prediction for
     *
     * @return returns a DeviceProjectionResponse which contains
     * 3 arrays of length n where n is max 30 or n is equal to the number of days a device has measurements for
     * plus the length of the prediction
     * optimistic = []
     * realistic = []
     * conservative = []
     * labelDates = []
     * and some other peripheral variables
     */
    @Override
    public DeviceProjectionResponse deviceProjection(DeviceProjectionRequest request)
    {
        if (request.getId() != null)
        {
            FindDeviceResponse findDeviceResponse = devicesService.findDevice(new FindDeviceRequest(request.getId()));
            FindWaterSiteByDeviceResponse waterSiteByDeviceResponse = waterSiteService.findWaterSiteByDeviceId(new FindWaterSiteByDeviceRequest(request.getId()));

            if ((findDeviceResponse.getSuccess() && findDeviceResponse.getDevice() != null)
                    && (waterSiteByDeviceResponse.getSuccess() && waterSiteByDeviceResponse.getWaterSite() != null))
            {
                /*
                Take note:
                    The sorted boolean should be set to true once in production.
                    It is set to false, because we have to simulate timeseries data (data over a period of time).
                    This is done by changing the dates on the measurements a device sends in.

                    0 returns all data of a device.
                    true returns the latest data first.
                */
                GetDeviceDataResponse deviceDataResponse = devicesService.getDeviceData(
                        new GetDeviceDataRequest(findDeviceResponse.getDevice().getDeviceName(),0, false));

                if (deviceDataResponse.getSuccess())
                {
                    if (request.getLength() >= 0 && request.getLength() <= 10)
                    {
                        switch (request.getType().toLowerCase())
                        {
                            case "ph":
                                projectionStrategy = new PhProjectionStrategy(request,
                                        deviceDataResponse,
                                        waterSiteByDeviceResponse,
                                        arimaUrl);
                                return projectionStrategy.predict();
                            case "waterlevel":
                                projectionStrategy = new WaterLevelProjectionStrategy(request,
                                        deviceDataResponse,
                                        waterSiteByDeviceResponse,
                                        apikey,
                                        weatherUrl);
                                return projectionStrategy.predict();
                            case "temperature":
                                projectionStrategy = new TemperatureProjectionStrategy(request,
                                        deviceDataResponse,
                                        waterSiteByDeviceResponse);
                                return projectionStrategy.predict();
                            default:
                                return failedProjection("Invalid prediction strategy specified, must be of type temperature, ph or waterlevel", request.getType(), request.getLength());
                        }
                    }
                    else
                    {
                        return failedProjection("Invalid prediction length specified, must be within the range 0-10.", request.getType(), request.getLength());
                    }
                }
                else
                {
                    return failedProjection(deviceDataResponse.getStatus(), request.getType(), request.getLength());
                }
            }
            else
            {
                return failedProjection("Could not get device data or the water site it monitors.", request.getType(), request.getLength());
            }
        }
        return failedProjection("No device id specified", request.getType(), request.getLength());
    }

    /**
     * Utility method
     * @param status string of the failed projection
     * @param type string of what type of projection they attempted
     * @param length integer of what period a user wants to predict for
     * @return DeviceProjectionResponse object
     */
    public DeviceProjectionResponse failedProjection(String status, String type, int length)
    {
        return new DeviceProjectionResponse(status, false, type, length, null, null, null, null);
    }
}
