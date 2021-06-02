package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests;

import java.util.UUID;

public class GetSiteByIdRequest
{
    UUID siteId;
    public GetSiteByIdRequest(UUID siteId)
    {
        this.siteId=siteId;

    }

    public UUID getSiteId() {
        return siteId;
    }
}
