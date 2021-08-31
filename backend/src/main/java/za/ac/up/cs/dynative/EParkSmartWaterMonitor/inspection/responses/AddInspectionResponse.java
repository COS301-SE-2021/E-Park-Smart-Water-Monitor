package za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.responses;

import java.util.UUID;

public class AddInspectionResponse {
    private String status;
    private Boolean success;
    private UUID id;

    public AddInspectionResponse(String status, Boolean success, UUID id) {
        this.status = status;
        this.success = success;
        this.id=id;
    }

    public AddInspectionResponse() { }

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