package za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class SetInspectionStatusRequest {

    private UUID inspectionId;
    private String status;

    public SetInspectionStatusRequest(
            @JsonProperty("inspectionId") UUID inspectionId,
            @JsonProperty("status") String status) {
        this.inspectionId = inspectionId;
        this.status = status;
    }

    public UUID getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(UUID inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
