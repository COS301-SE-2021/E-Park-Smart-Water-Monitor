package za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.responses;

public class SMSResponse {

    private String status;
    private Boolean success;

    public SMSResponse(String status, Boolean success) {
        this.status = status;
        this.success = success;
    }

    public SMSResponse() {
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
