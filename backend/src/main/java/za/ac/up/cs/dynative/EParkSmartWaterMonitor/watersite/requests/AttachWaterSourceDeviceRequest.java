package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Device;

import java.util.UUID;

public class AttachWaterSourceDeviceRequest {

    UUID siteId;
    Device device;

    public AttachWaterSourceDeviceRequest(UUID siteId, Device device) {
        this.siteId = siteId;
        this.device = device;
    }

    public UUID getSiteId() {
        return siteId;
    }

    public Device getDevice() {
        return device;
    }
}
