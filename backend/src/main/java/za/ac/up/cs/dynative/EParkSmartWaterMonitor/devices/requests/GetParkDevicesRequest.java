package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class GetParkDevicesRequest {

    UUID parkId;
    public GetParkDevicesRequest(@JsonProperty("parkId") UUID parkId)
    {
        this.parkId=parkId;

    }

    public UUID getParkId() {
        return parkId;
    }
}
