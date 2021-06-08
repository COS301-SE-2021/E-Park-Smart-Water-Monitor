package za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;

public class FindByParkIdResponse {
    private Park park;

    public FindByParkIdResponse(Park park) {
        this.park = park;
    }

    public FindByParkIdResponse() {
    }

    public Park getPark() {
        return park;
    }

    public void setStatus(Park park) {
        this.park = park;
    }
}
