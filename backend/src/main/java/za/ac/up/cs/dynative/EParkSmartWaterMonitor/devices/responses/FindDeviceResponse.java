package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Device;

public class FindDeviceResponse
{

    private String status;
    private Boolean success;
    private Device device;

    public FindDeviceResponse(String status, Boolean success, Device device)
    {
        this.status = status;
        this.success = success;
        this.device = device;
    }

    public FindDeviceResponse()
    {
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
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

}
