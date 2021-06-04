package za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;

public class FindByParkNameResponse {
    private Park park;

    public FindByParkNameResponse(Park park) {
        this.park = park;
    }

    public FindByParkNameResponse() {
    }

    public Park getPark() {
        return park;
    }

    public void setStatus(Park park) {
        this.park = park;
    }

}
