package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses;

public class AddSiteResponse {
    private String status;
    private Boolean success;

    public AddSiteResponse(String status, Boolean success) {
        this.status = status;
        this.success = success;
    }

    public AddSiteResponse() {
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
