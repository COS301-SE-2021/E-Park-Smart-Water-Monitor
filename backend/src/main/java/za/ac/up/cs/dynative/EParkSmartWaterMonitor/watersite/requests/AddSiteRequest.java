package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * This request class will contain the details needed to add a new water site.
 */
public class AddSiteRequest {

    /**
     * attributes:
     */
    private UUID parkId;
    private String siteName;
    private double latitude;
    private double longitude;
    private String shape;
    private double length;
    private double width;
    private double radius;

    /**
     * The custom constructor, initializing the object.
     * @param parkId The id of the park the site will be added to.
     * @param siteName The name of the newly created water site.
     * @param latitude The coordinate of the new water site.
     * @param longitude The coordinate of the new water site.
     * @param shape Shape the water site will be (rectangular or circle)
     * @param length Measurements if the shape (applicable to the square).
     * @param width  Measurements if the shape (applicable to the square).
     * @param radius  Measurements if the shape (only applicable to the circle)
     */
    public AddSiteRequest(@JsonProperty("parkId") UUID parkId,
                          @JsonProperty("siteName") String siteName,
                          @JsonProperty("latitude") double latitude,
                          @JsonProperty("longitude")  double longitude,
                          @JsonProperty("shape") String shape,
                          @JsonProperty("length") double length,
                          @JsonProperty("width") double width,
                          @JsonProperty("radius") double radius) {
        this.parkId = parkId;
        this.siteName = siteName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.shape = shape;
        this.length = length;
        this.width = width;
        this.radius = radius;
    }

    /**
     * Default constructor
     */
    public AddSiteRequest() {
    }

    /**
     * getters and setters:
     */
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

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
