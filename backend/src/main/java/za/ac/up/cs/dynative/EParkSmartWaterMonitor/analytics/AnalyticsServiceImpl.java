package za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.models.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.models.WaterLevelProjectionStrategy;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.requests.DeviceProjectionRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.responses.DeviceProjectionResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.DevicesService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.FindDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.GetDeviceDataRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.FindDeviceResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.GetDeviceDataResponse;

@Service("AnalyticsServiceImpl")
public class AnalyticsServiceImpl implements AnalyticsService {

    private DevicesService devicesService;
    private ProjectionStrategyInterface projectionStrategy;

    public AnalyticsServiceImpl(@Qualifier("DeviceServiceImpl") DevicesService devicesService) {
        this.devicesService = devicesService;
    }

    @Override
    public DeviceProjectionResponse deviceProjection(DeviceProjectionRequest request) {
        if (request.getId() != null) {
            FindDeviceResponse findDeviceResponse = devicesService.findDevice(new FindDeviceRequest(request.getId()));
            if (findDeviceResponse.getSuccess() && findDeviceResponse.getDevice() != null) {
                GetDeviceDataResponse deviceDataResponse = devicesService.getDeviceData(
                        new GetDeviceDataRequest(findDeviceResponse.getDevice().getDeviceName(),0));
                if (deviceDataResponse.getSuccess()) {
                    switch (request.getType()) {
                        case "ph":
                            projectionStrategy = new PhProjectionStrategy(request, deviceDataResponse);
                            return projectionStrategy.predict();
                        case "waterlevel":
                            projectionStrategy = new WaterLevelProjectionStrategy(request, deviceDataResponse);
                            return projectionStrategy.predict();
                        case "temperature":
                            projectionStrategy = new TemperatureProjectionStrategy(request, deviceDataResponse);
                            return projectionStrategy.predict();
                    }
                }
                else {
                    return new DeviceProjectionResponse(
                            deviceDataResponse.getStatus(),
                            false,
                            request.getType(),
                            request.getLength(),
                            null,
                            null,
                            null);
                }
            }
            else {
                return new DeviceProjectionResponse(
                        findDeviceResponse.getStatus(),
                        false,
                        request.getType(),
                        request.getLength(),
                        null,
                        null,
                        null);
            }
        }
        return new DeviceProjectionResponse(
                "No device id specified",
                false,
                request.getType(),
                request.getLength(),
                null,
                null,
                null);
    }
}
