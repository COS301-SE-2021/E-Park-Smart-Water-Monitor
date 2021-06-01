package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetNumDevicesRequest {

    private String parkName;

    public GetNumDevicesRequest(@JsonProperty("parkName") String parkName) {
        this.parkName = parkName;
    }

    public GetNumDevicesRequest() {
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }
}
