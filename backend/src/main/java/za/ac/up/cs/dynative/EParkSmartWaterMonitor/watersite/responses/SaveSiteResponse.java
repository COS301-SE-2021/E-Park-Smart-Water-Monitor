package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses;

/**
 * The response to saving a water site to the database.
 */
public class SaveSiteResponse {
    /**
     * Attributes:
     */
    private String status;
    private boolean success;

    /**
     * The custom constructor:
     * @param status The success status of the request
     * @param success The success value of the request
     */
    public SaveSiteResponse(String status, boolean success) {
        this.status = status;
        this.success = success;
    }

    /**
     * Default constructor:
     */
    public SaveSiteResponse() {
    }

    /**
     * Getters and setters:
     * @return
     */
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
