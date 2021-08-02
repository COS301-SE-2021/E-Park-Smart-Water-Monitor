package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses;

public class DeleteWaterSiteResponse {
    private String status;
    private Boolean success;

    public DeleteWaterSiteResponse(String status, Boolean success) {
        this.status = status;
        this.success = success;
    }

    public DeleteWaterSiteResponse() {
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
