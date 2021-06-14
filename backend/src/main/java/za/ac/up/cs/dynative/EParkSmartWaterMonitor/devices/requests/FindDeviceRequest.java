package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests;

import java.util.UUID;

public class FindDeviceRequest
{
    private UUID deviceID;

    public FindDeviceRequest(UUID deviceID)
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
