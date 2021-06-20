package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses;

public class SaveSiteResponse {

    private String status;
    private boolean success;

    public SaveSiteResponse(String status, boolean success) {
        this.status = status;
        this.success = success;
    }

    public SaveSiteResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
