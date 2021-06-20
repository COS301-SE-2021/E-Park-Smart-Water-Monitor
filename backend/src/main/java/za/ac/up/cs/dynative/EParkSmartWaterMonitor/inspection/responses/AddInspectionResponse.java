package za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.responses;

public class AddInspectionResponse {
    private String status;
    private Boolean success;

    public AddInspectionResponse(String status, Boolean success) {
        this.status = status;
        this.success = success;
    }

    public AddInspectionResponse() { }

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