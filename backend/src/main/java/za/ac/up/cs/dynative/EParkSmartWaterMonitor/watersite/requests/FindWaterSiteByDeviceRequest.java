package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests;

import java.util.UUID;

/**
 * This class will represent the find water site device by id requests
 */
public class FindWaterSiteByDeviceRequest {
    /**
     * Attribute:
     */
    private UUID deviceID;

    /**
     * Custom constructor
     * @param deviceID the device id to be searched.
     */
    public FindWaterSiteByDeviceRequest(UUID deviceID) {
        this.deviceID = deviceID;
    }

    /**
     * Getters and setters:
     */
    public UUID getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(UUID deviceID) {
        this.deviceID = deviceID;
    }
}
