package za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Device;

import java.util.Collection;

public class GetAllDevicesResponse {
    private String status;
    private Boolean success;
    private Collection<Device> devices;

    public GetAllDevicesResponse(String status, Boolean success, Collection<Device> devices) {
        this.status = status;
        this.success = success;
        this.devices = devices;
    }

    public GetAllDevicesResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Collection<Device> getSite() {
        return devices;
    }

    public void setSite(Collection<Device> devices) {
        this.devices = devices;
    }
}
