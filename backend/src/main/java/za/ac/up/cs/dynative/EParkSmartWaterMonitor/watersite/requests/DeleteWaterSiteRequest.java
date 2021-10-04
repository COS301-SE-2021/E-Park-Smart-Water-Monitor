package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * This class will represent the request object to delete a specified water site.
 */
public class DeleteWaterSiteRequest {
    /**
     * attribute:
     */
    private UUID id;

    /**
     * The custom constructor initializing the attribute.
     * @param id
     */
    public DeleteWaterSiteRequest(@JsonProperty("id") UUID id) {
        this.id = id;
    }

    /**
     * Getters and setters:
     */
    public UUID getWaterSiteId() {
        return id;
    }

    public void setWaterSiteRequestId(UUID id) {
        this.id = id;
    }
}
