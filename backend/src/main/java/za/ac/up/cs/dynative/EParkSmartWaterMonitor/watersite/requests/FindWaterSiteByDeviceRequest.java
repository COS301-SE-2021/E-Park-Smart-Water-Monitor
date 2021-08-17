package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests;


import java.util.UUID;

public class FindWaterSiteByDeviceRequest {
    private UUID deviceID;

    public FindWaterSiteByDeviceRequest(UUID deviceID)
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
