package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses;

/**
 * The response to a deleting a water site request.
 */
public class DeleteWaterSiteResponse {
    /**
     * Attributes:
     */
    private String status;
    private Boolean success;

    /**
     * The custom constructor
     * @param status The success status of the request.
     * @param success The success value of the request
     */
    public DeleteWaterSiteResponse(String status, Boolean success) {
        this.status = status;
        this.success = success;
    }

    /**
     * The default constructor
     */
    public DeleteWaterSiteResponse() {
    }

    /**
     * Getters and Setters:
     */
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
