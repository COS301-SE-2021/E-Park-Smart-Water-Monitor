package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * This class will represent the edit request objects.
 */
public class EditWaterSiteRequest {
    /**
     * Attributes:
     */
    private UUID id;
    private String siteName;
    private double latitude;
    private double longitude;

    /**
     * Custom Constructor to initialize the attributes:
     * @param id The id of the watersite to be updated.
     * @param siteName The new name of the site.
     * @param latitude The new coordinates of the site.
     * @param longitude The new coordinates of the site.
     */
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

    /**
     * Getters and setters:
     */
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
