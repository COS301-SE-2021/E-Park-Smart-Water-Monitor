package za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class CreateUserResponse {

    private String status;
    private Boolean success;

    public CreateUserResponse(String status, Boolean success) {
        this.status = status;
        this.success = success;
    }

    public CreateUserResponse() {
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
