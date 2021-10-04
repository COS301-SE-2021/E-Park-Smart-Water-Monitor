package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses;

/**
 * This class will represent the response of the attach site request
 */
public class AttachWaterSourceDeviceResponse {
    /**
     * Attributes:
     */
    private String status;
    private Boolean success;

    public AttachWaterSourceDeviceResponse(String status, Boolean success) {
        this.status = status;
        this.success = success;
    }

    /**
     * getters and setters:
     */
    public String getStatus() {
        return status;
    }

    public Boolean getSuccess() {
        return success;
    }
}
