package za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class GetDeviceInspectionsRequest {

    private UUID deviceId;

    public GetDeviceInspectionsRequest(@JsonProperty("deviceId") UUID deviceId) {
        this.deviceId = deviceId;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }
}
