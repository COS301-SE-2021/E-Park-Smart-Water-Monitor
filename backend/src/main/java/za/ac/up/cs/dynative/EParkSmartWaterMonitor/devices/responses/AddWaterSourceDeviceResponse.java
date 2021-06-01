package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses;

public class AddWaterSourceDeviceResponse {

    private String status;
    private Boolean success;

    public AddWaterSourceDeviceResponse(String status, Boolean success) {
        this.status = status;
        this.success = success;
    }

    public AddWaterSourceDeviceResponse() {
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
