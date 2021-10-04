package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests;

import java.util.UUID;

/**
 * This class will be used to test if a device can be added to a specified site.
 */
public class CanAttachWaterSourceDeviceRequest {
    /**
     * attribute:
     */
    UUID siteId;

    /**
     * Custom constructor:
     * @param siteId The site id to test with.
     */
    public CanAttachWaterSourceDeviceRequest(UUID siteId) {
        this.siteId = siteId;
    }

    /**
     * Getter of the attribute.
     */
    public UUID getSiteId() {
        return siteId;
    }

}
