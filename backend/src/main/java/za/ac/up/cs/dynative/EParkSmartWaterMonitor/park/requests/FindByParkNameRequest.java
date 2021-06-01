package za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests;

public class FindByParkNameRequest {

    private String parkName;

    public FindByParkNameRequest(String parkName) {
        this.parkName = parkName;
    }

    public FindByParkNameRequest() {
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }
}
