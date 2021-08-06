package za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.models;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.requests.DeviceProjectionRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.responses.DeviceProjectionResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Measurement;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.GetDeviceDataResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.GetDeviceInnerResponse;

import java.util.ArrayList;

public class TemperatureProjectionStrategy implements ProjectionStrategyInterface {

    private final DeviceProjectionRequest deviceProjectionRequest;
    private final GetDeviceDataResponse deviceDataResponse;

    public TemperatureProjectionStrategy(DeviceProjectionRequest deviceProjectionRequest, GetDeviceDataResponse deviceDataResponse) {
        this.deviceProjectionRequest = deviceProjectionRequest;
        this.deviceDataResponse = deviceDataResponse;
    }

    public DeviceProjectionRequest getDeviceProjectionRequest() {
        return deviceProjectionRequest;
    }

    @Override
    public DeviceProjectionResponse predict() {
        ArrayList<Double> temperatureData = new ArrayList<>();
        for (GetDeviceInnerResponse innerData :
                deviceDataResponse.getInnerResponses()) {
            for (Measurement waterLevelMeasurement :
                    innerData.getMeasurements()) {
                if (waterLevelMeasurement.getType().equals("WATER_TEMP")) {
                    temperatureData.add(waterLevelMeasurement.getEstimateValue());
                }
            }
            System.out.println(innerData.getMeasurements());
        }
        return new DeviceProjectionResponse(
                "temperature",
                true,
                "temperature",
                deviceProjectionRequest.getLength(),
                null,
                temperatureData,
                null);
    }
}
