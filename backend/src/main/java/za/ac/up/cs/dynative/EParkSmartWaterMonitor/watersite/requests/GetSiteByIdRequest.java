package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class GetSiteByIdRequest
{
    UUID siteId;
    public GetSiteByIdRequest(@JsonProperty("siteId") UUID siteId)
    {
        this.siteId=siteId;

    }

    public UUID getSiteId() {
        return siteId;
    }
}
