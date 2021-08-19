package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class AddSiteRequest {

    private UUID parkId;
    private String siteName;
    private double latitude;
    private double longitude;

    public AddSiteRequest(@JsonProperty("parkId") UUID parkId,
                          @JsonProperty("siteName") String siteName,
                          @JsonProperty("latitude") double latitude,
                          @JsonProperty("longitude")  double longitude) {
        this.parkId = parkId;
        this.siteName = siteName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public AddSiteRequest() {
    }

    public UUID getParkId() {
        return parkId;
    }

    public void setParkId(UUID parkId) {
        this.parkId = parkId;
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
