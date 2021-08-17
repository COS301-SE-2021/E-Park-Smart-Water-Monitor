package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses;

import java.util.ArrayList;

public class PingDeviceResponse {
    private String status;
    private Boolean success;
    private String deviceName;
    private GetDeviceInnerResponse innerResponses;

    public PingDeviceResponse(String status, Boolean success, String deviceName, GetDeviceInnerResponse innerResponses) {
        this.status = status;
        this.success = success;
        this.deviceName = deviceName;
        this.innerResponses = innerResponses;
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

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public GetDeviceInnerResponse getInnerResponses() {
        return innerResponses;
    }

    public void setInnerResponses(GetDeviceInnerResponse innerResponses) {
        this.innerResponses = innerResponses;
    }
}
