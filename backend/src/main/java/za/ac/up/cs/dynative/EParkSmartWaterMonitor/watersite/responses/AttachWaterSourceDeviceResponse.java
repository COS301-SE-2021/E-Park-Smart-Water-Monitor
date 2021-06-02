package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses;

public class AttachWaterSourceDeviceResponse {
    private String status;
    private Boolean success;

    public AttachWaterSourceDeviceResponse(String status, Boolean success) {
        this.status = status;
        this.success = success;
    }

    public String getStatus() {
        return status;
    }

    public Boolean getSuccess() {
        return success;
    }
}
