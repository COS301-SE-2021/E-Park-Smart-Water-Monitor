package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses;

public class DeleteDeviceResponse {
    private String status;
    private Boolean success;

    public DeleteDeviceResponse(String status, Boolean success) {
        this.status = status;
        this.success = success;
    }

    public DeleteDeviceResponse() {
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
