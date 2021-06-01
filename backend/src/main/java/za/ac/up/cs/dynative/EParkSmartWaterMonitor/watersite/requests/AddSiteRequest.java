package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddSiteRequest {

    private String parkName;
    private String siteName;
    private double latitude;
    private double longitude;

    public AddSiteRequest(@JsonProperty("parkName") String parkName,
                          @JsonProperty("siteName") String siteName,
                          @JsonProperty("latitude") double latitude,
                          @JsonProperty("longitude")  double longitude) {
        this.parkName = parkName;
        this.siteName = siteName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public AddSiteRequest() {
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
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
