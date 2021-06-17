package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceDevice;

import java.util.UUID;

public class CanAttachWaterSourceDeviceRequest {
    UUID siteId;

    public CanAttachWaterSourceDeviceRequest(UUID siteId) {
        this.siteId = siteId;
    }

    public UUID getSiteId() {
        return siteId;
    }

}
