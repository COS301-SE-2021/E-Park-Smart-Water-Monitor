package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses;

/**
 * This class is used for internal testing purposes only.
 */
public class DeleteWaterSiteInternalResponse {
    private Boolean success;

    public DeleteWaterSiteInternalResponse(Boolean success) {
        this.success = success;
    }

    public DeleteWaterSiteInternalResponse() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
