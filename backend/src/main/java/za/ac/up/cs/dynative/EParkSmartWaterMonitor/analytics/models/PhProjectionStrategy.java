package za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.models;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.requests.DeviceProjectionRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.responses.DeviceProjectionResponse;

public class PhProjectionStrategy implements ProjectionStrategyInterface {

    private final DeviceProjectionRequest deviceProjectionRequest;

    public PhProjectionStrategy(DeviceProjectionRequest deviceProjectionRequest) {
        this.deviceProjectionRequest = deviceProjectionRequest;
    }

    public DeviceProjectionRequest getDeviceProjectionRequest() {
        return deviceProjectionRequest;
    }
    @Override
    public DeviceProjectionResponse predict() {
        return new DeviceProjectionResponse(
                "PH",
                true,
                "ph",
                deviceProjectionRequest.getLength(),
                null,
                null,
                null);    }
}
