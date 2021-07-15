package za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses;

public class DeleteParkResponse {
    private String status;
    private Boolean success;

    public DeleteParkResponse(String status, Boolean success) {
        this.status = status;
        this.success = success;
    }

    public DeleteParkResponse() {
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
