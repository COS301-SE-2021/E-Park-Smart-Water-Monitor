package za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;

public class SaveParkRequest {

    private Park park;

    public SaveParkRequest(Park park) {
        this.park = park;
    }

    public SaveParkRequest() {
    }

    public Park getPark() {
        return park;
    }

    public void setPark(Park park) {
        this.park = park;
    }
}
