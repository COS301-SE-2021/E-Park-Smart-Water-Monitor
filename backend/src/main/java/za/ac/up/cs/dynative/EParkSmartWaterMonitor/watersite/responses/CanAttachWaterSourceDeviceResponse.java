package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses;

/**
 * The response of the testing request.
 */
public class CanAttachWaterSourceDeviceResponse {
    /**
     * Attributes:
     */
    private String status;
    private Boolean success;

    /**
     * The custom constructor:
     * @param status The success status of the request.
     * @param success The success value of the request.
     */
    public CanAttachWaterSourceDeviceResponse(String status, Boolean success) {
        this.status = status;
        this.success = success;
    }

    /**
     * Getters of the attributes:
     */
    public String getStatus() {
        return status;
    }

    public Boolean getSuccess() {
        return success;
    }
}
