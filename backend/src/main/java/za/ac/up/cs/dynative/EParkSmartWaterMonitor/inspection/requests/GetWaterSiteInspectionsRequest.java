package za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class GetWaterSiteInspectionsRequest {

    private UUID waterSiteId;

    public GetWaterSiteInspectionsRequest(@JsonProperty("siteId") UUID waterSiteId) {
        this.waterSiteId = waterSiteId;
    }

    public UUID getWaterSiteId() {
        return waterSiteId;
    }

    public void setWaterSiteId(UUID waterSiteId) {
        this.waterSiteId = waterSiteId;
    }
}
