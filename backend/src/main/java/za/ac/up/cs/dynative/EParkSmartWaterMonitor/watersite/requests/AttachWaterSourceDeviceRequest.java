package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Device;

import java.util.UUID;

/**
 * This class will represent the request object to attach a new esp device to the system.
 */
public class AttachWaterSourceDeviceRequest {
    /**
     * Attributes:
     */
    UUID siteId;
    Device device;

    /**
     * Custom constructor to initialize the attributes.
     * @param siteId The UUID of the site the device will be attached to.
     * @param device The device object to attach to the water site.
     */
    public AttachWaterSourceDeviceRequest(UUID siteId, Device device) {
        this.siteId = siteId;
        this.device = device;
    }

    /**
     * getters:
     */
    public UUID getSiteId() {
        return siteId;
    }

    public Device getDevice() {
        return device;
    }
}
