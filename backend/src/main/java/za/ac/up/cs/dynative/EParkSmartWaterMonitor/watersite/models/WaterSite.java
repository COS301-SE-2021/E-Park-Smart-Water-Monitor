package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Device;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.models.Inspection;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * This class represents a water site in the neo4j database.
 */
@Node
public class WaterSite {
    /** Attributes **/
    @Id
    private UUID id;
    private String waterSiteName;
    private double latitude;
    private double longitude;
    private String shape;
    private double length;
    private double width;
    private double radius;

    /**
     * Relationships that needs to be respected in the db.
     */
    @Relationship(type = "WATER_MONITORED_BY", direction = Relationship.Direction.OUTGOING)
    private Set<Device> devices;
    @Relationship(type = "HAS", direction = Relationship.Direction.OUTGOING)
    private Set<Inspection> inspections;

    /**
     * overloaded constructor that will be used to set the values of the attributed.
     */
    public WaterSite(UUID id,
                     String waterSiteName,
                     double latitude,
                     double longitude,
                     String shape,
                     double length,
                     double width,
                     double radius) {
        this.id = id;
        this.waterSiteName = waterSiteName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.shape = shape;
        if (Objects.equals(shape, "circle")) {
            this.length = 0;
            this.width = 0;
            this.radius = radius;
        }
        else if (Objects.equals(shape, "rectangle")) {
            this.radius = 0;
            this.length = length;
            this.width = width;
        }
    }

    public WaterSite() {
    }

    /**
     * This function will be used to add a device to the water site
     * @param device this will be added to the hash set
     */
    public void addWaterSourceDevice(Device device) {
        if ( devices == null) {
            devices = new HashSet<>();
        }
        devices.add(device);
    }

    /**
     * This function will be used to add an inspection to the water site according to a specific device
     * @param inspection this will be added to the hash set
     */
    public void addInspection(Inspection inspection) {
        if ( inspections == null) {
            inspections = new HashSet<>();
        }
        inspections.add(inspection);
    }

    /**
     * Getters and setters of attributes
     */
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
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
    public String getWaterSiteName() {
        return waterSiteName;
    }
    public void setWaterSiteName(String waterSiteName) {
        this.waterSiteName = waterSiteName;
    }
    public Set<Device> getDevices() {
        return devices;
    }
    public void setDevices(Set<Device> devices) {
        this.devices = devices;
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
    public Set<Inspection> getInspections() {
        return inspections;
    }
    public void setInspections(Set<Inspection> inspections) {
        this.inspections = inspections;
    }

    /**
     * The attributes of a water site is formatted to display formally and readable.
     * @return The formatted string will be returned.
     */
    @Override
    public String toString() {
        return "WaterSite{" +
                "id=" + id +
                ", waterSiteName='" + waterSiteName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", shape='" + shape + '\'' +
                ", length=" + length +
                ", width=" + width +
                ", radius=" + radius +
                ", devices=" + devices +
                ", inspections=" + inspections +
                '}';
    }
}
