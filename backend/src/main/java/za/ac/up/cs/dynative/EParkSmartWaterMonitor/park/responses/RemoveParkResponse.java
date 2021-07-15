package za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses;

public class RemoveParkResponse {
    private String status;
    private Boolean success;

    public RemoveParkResponse(String status, Boolean success) {
        this.status = status;
        this.success = success;
    }

    public RemoveParkResponse() {
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
