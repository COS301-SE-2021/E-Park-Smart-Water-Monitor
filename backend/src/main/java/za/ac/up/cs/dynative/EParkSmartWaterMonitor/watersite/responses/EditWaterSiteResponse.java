package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses;

/**
 * This class represents the response of the edit water site request.
 */
public class EditWaterSiteResponse {
    /**
     * Attributes:
     */
    private String status;
    private Boolean success;

    /**
     * Custom constructor:
     * @param status The status value of the request
     * @param success The success value of the request
     */
    public EditWaterSiteResponse(String status, Boolean success) {
        this.status = status;
        this.success = success;
    }

    /**
     * Default constructor
     */
    public EditWaterSiteResponse() {
    }

    /**
     * Getters and setters:
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
