package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests;

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
