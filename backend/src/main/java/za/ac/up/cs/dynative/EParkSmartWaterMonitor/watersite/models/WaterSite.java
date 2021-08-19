package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Device;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.models.Inspection;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Node
public class WaterSite {

    @Id
    private UUID id;

    private String waterSiteName;

    private double latitude;

    private double longitude;

    @Relationship(type = "WATER_MONITORED_BY", direction = Relationship.Direction.OUTGOING)
    private Set<Device> devices;

    @Relationship(type = "HAS", direction = Relationship.Direction.OUTGOING)
    private Set<Inspection> inspections;

    public WaterSite(UUID id, String waterSiteName, double latitude, double longitude) {
        this.id = id;
        this.waterSiteName = waterSiteName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public WaterSite() {
    }

    public void addWaterSourceDevice(Device device) {
        if ( devices == null) {
            devices = new HashSet<>();
        }
        devices.add(device);
    }

    public void addInspection(Inspection inspection) {
        if ( inspections == null) {
            inspections = new HashSet<>();
        }
        inspections.add(inspection);
    }

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

    @Override
    public String toString() {
        return "WaterSite{" +
                "id=" + id +
                ", waterSiteName='" + waterSiteName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", waterSourceDevices=" + devices +
                '}';
    }
}
