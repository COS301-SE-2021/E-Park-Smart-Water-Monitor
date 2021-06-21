package za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests;

import java.util.UUID;

public class FindByParkIdRequest {

    private UUID parkId;

    public FindByParkIdRequest(UUID parkId) {
        this.parkId = parkId;
    }

    public FindByParkIdRequest() {
    }

    public UUID getParkId() {
        return parkId;
    }

    public void setParkId(UUID parkId) {
        this.parkId = parkId;
    }
}
