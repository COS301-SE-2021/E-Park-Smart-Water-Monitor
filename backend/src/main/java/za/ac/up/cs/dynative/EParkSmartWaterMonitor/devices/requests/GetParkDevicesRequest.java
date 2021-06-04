package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class GetParkDevicesRequest {

    UUID siteId;
    public GetParkDevicesRequest(@JsonProperty("siteId") UUID siteId)
    {
        this.siteId=siteId;

    }

    public UUID getSiteId() {
        return siteId;
    }
}
