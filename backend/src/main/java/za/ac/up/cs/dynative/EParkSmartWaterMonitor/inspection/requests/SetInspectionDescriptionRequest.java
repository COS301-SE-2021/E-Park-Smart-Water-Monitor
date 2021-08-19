package za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class SetInspectionDescriptionRequest {

        private UUID inspectionId;
        private String description;

        public SetInspectionDescriptionRequest(
                @JsonProperty("inspectionId") UUID inspectionId,
                @JsonProperty("description") String description) {
            this.inspectionId = inspectionId;
            this.description = description;
        }

    public UUID getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(UUID inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
