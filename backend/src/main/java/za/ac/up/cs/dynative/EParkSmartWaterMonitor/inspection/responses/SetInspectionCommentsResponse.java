package za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.responses;

public class SetInspectionCommentsResponse {
    private String status;
    private Boolean success;

    public SetInspectionCommentsResponse(String status, Boolean success) {
        this.status = status;
        this.success = success;
    }

    public SetInspectionCommentsResponse() { }

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
