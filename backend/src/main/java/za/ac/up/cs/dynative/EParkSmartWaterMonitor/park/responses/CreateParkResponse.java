package za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses;

import java.util.UUID;

public class CreateParkResponse {
    private String status;
    private Boolean success;
    private UUID id;

    public CreateParkResponse(String status, Boolean success) {
        this.status = status;
        this.success = success;
    }

    public CreateParkResponse() {
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

    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id=id;
    }
}
