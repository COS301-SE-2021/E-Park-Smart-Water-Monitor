package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Get water site by id request representation
 */
public class GetSiteByIdRequest {
    /**
     * Attribute:
     */
    UUID siteId;

    /**
     * The custom constructor:
     * @param siteId The id to be searched.
     */
    public GetSiteByIdRequest(@JsonProperty("siteId") UUID siteId) {
        this.siteId=siteId;

    }

    /**
     * getter for the attribute:
     */
    public UUID getSiteId() {
        return siteId;
    }
}
