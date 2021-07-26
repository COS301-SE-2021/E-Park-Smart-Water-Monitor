package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class DeleteWaterSiteRequest {
    private UUID id;

    public DeleteWaterSiteRequest(@JsonProperty("id") UUID id) {
        this.id = id;
    }

    public UUID getWaterSiteId() {
        return id;
    }

    public void setWaterSiteRequestId(UUID id) {
        this.id = id;
    }
}
