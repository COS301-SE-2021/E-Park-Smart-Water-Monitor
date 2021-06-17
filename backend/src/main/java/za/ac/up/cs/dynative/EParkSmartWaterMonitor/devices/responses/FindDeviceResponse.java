package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceDevice;

public class FindDeviceResponse
{

    private String status;
    private Boolean success;
    private WaterSourceDevice device;

    public FindDeviceResponse(String status, Boolean success, WaterSourceDevice device)
    {
        this.status = status;
        this.success = success;
        this.device = device;
    }

    public FindDeviceResponse()
    {
    }

    public WaterSourceDevice getDevice() {
        return device;
    }

    public void setDevice(WaterSourceDevice device) {
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
