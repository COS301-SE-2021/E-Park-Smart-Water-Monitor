package za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.models;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.requests.DeviceProjectionRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.responses.DeviceProjectionResponse;

public class WaterLevelProjectionStrategy implements ProjectionStrategyInterface {

    private final DeviceProjectionRequest deviceProjectionRequest;

    public WaterLevelProjectionStrategy(DeviceProjectionRequest deviceProjectionRequest) {
        this.deviceProjectionRequest = deviceProjectionRequest;
    }

    public DeviceProjectionRequest getDeviceProjectionRequest() {
        return deviceProjectionRequest;
    }


    @Override
    public DeviceProjectionResponse predict() {
        return new DeviceProjectionResponse(
                "WaterLevel",
                true,
                "waterlevel",
                deviceProjectionRequest.getLength(),
                null,
                null,
                null);
    }
}
