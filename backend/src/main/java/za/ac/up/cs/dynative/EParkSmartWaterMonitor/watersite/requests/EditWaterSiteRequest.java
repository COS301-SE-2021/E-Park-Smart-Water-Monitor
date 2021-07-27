package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class EditWaterSiteRequest {
    private UUID id;
    private String siteName;
    private double latitude;
    private double longitude;

    public EditWaterSiteRequest(@JsonProperty("id") UUID id,
                                @JsonProperty("siteName") String siteName,
                                @JsonProperty("latitude") double latitude,
                                @JsonProperty("longitude") double longitude)
    {
        this.id = id;
        this.siteName = siteName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
