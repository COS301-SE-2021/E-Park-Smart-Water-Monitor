package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class PingDeviceRequest {
    private UUID deviceID;

    public PingDeviceRequest(@JsonProperty("deviceID")UUID deviceID)
    {
        this.deviceID = deviceID;
    }

    public UUID getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(UUID deviceID) {
        this.deviceID = deviceID;
    }
}
