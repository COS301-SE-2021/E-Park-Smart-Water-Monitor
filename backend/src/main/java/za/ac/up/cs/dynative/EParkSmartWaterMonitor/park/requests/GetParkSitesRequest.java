package za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class GetParkSitesRequest {

    private UUID parkId;

    public GetParkSitesRequest(@JsonProperty("parkId") UUID parkId) {
        this.parkId = parkId;
    }


    public UUID getParkId() {
        return parkId;
    }

    public void setParkId(UUID parkId) {
        this.parkId = parkId;
    }
}
