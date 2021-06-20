package za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses;

public class DeleteUserResponse {
    private String status;
    private Boolean success;

    public DeleteUserResponse(String status, Boolean success) {
        this.status = status;
        this.success = success;
    }

    public DeleteUserResponse() {
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
