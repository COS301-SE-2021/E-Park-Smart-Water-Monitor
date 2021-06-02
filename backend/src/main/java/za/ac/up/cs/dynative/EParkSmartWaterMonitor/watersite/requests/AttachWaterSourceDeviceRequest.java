package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceDevice;

import java.util.UUID;

public class AttachWaterSourceDeviceRequest {

    UUID siteId;
    WaterSourceDevice waterSourceDevice;

    public AttachWaterSourceDeviceRequest(UUID siteId, WaterSourceDevice waterSourceDevice) {
        this.siteId = siteId;
        this.waterSourceDevice = waterSourceDevice;
    }

    public UUID getSiteId() {
        return siteId;
    }

    public WaterSourceDevice getWaterSourceDevice() {
        return waterSourceDevice;
    }
}
