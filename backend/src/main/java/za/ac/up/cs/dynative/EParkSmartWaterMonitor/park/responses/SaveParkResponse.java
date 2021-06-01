package za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses;

public class SaveParkResponse {

    private String status;
    private boolean success;

    public SaveParkResponse(String status, boolean success) {
        this.status = status;
        this.success = success;
    }

    public SaveParkResponse() {
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
