package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * This class is used for internal testing purposes only.
 */
public class DeleteWaterSiteInternalRequest {
    private UUID id;

    public DeleteWaterSiteInternalRequest() {
    }

    public DeleteWaterSiteInternalRequest(@JsonProperty("id") UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}