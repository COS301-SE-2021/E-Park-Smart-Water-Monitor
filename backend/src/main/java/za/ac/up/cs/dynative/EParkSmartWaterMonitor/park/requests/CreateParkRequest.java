package za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateParkRequest {

    private String parkName;
    private double latitude;
    private double longitude;

    public CreateParkRequest(@JsonProperty("newParkId") String parkName, @JsonProperty("latitude") double latitude, @JsonProperty("longitude")  double longitude) {
        this.parkName = parkName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public CreateParkRequest() {
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
