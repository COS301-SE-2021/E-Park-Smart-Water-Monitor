package za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class SetInspectionCommentsRequest {

    private UUID inspectionId;
    private String comments;

    public SetInspectionCommentsRequest(
            @JsonProperty("inspectionId") UUID inspectionId,
            @JsonProperty("comments") String comments) {
        this.inspectionId = inspectionId;
        this.comments = comments;
    }

    public UUID getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(UUID inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
