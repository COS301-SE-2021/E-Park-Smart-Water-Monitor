package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class GetNumDevicesRequest {

    private UUID parkId;

    public GetNumDevicesRequest(@JsonProperty("parkId") UUID parkId) {
        this.parkId = parkId;
    }

    public GetNumDevicesRequest() {
    }

    public UUID getParkId() {
        return parkId;
    }

    public void setParkName(UUID parkId) {
        this.parkId = parkId;
    }
}
