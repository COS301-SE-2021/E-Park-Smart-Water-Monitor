package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses;

public class AddDeviceResponse {

    private String status;
    private Boolean success;

    public AddDeviceResponse(String status, Boolean success) {
        this.status = status;
        this.success = success;
    }

    public AddDeviceResponse() {
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
