package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.InfrastructureDevice;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.SourceData;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceDevice;

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
    private Set<WaterSourceDevice> waterSourceDevices;

    @Relationship(type = "INFRASTRUCTURE_MONITORED_BY", direction = Relationship.Direction.OUTGOING)
    private Set<InfrastructureDevice> infrastructureDevices;

    @Relationship(type = "HAS_WATER_DATA", direction = Relationship.Direction.OUTGOING)
    private Set<SourceData> waterSiteData;

    public WaterSite(UUID id, String waterSiteName, double latitude, double longitude, Set<WaterSourceDevice> waterSourceDevices, Set<InfrastructureDevice> infrastructureDevices, Set<SourceData> sourceData) {
        this.id = id;
        this.waterSiteName = waterSiteName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.waterSourceDevices = waterSourceDevices;
        this.infrastructureDevices = infrastructureDevices;
        this.waterSiteData = sourceData;
    }

    public WaterSite(UUID id, String waterSiteName, double latitude, double longitude) {
        this.id = id;
        this.waterSiteName = waterSiteName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public WaterSite() {
    }

    public void addWaterSiteData(SourceData sourceData) {
        if (waterSiteData == null) {
            waterSiteData = new HashSet<>();
        }
        waterSiteData.add(sourceData);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getwaterSiteName() {
        return waterSiteName;
    }

    public void setwaterSiteName(String waterSiteName) {
        this.waterSiteName = waterSiteName;
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

    public Set<WaterSourceDevice> getWaterSourceDevices() {
        return waterSourceDevices;
    }

    public void setWaterSourceDevices(Set<WaterSourceDevice> waterSourceDevices) {
        this.waterSourceDevices = waterSourceDevices;
    }

    public Set<InfrastructureDevice> getInfrastructureDevices() {
        return infrastructureDevices;
    }

    public void setInfrastructureDevices(Set<InfrastructureDevice> infrastructureDevices) {
        this.infrastructureDevices = infrastructureDevices;
    }

    public Set<SourceData> getWaterSiteData() {
        return waterSiteData;
    }

    public void setWaterSiteData(Set<SourceData> waterSiteData) {
        this.waterSiteData = waterSiteData;
    }

    @Override
    public String toString() {
        return "WaterSite{" +
                "id=" + id +
                ", waterSiteName='" + waterSiteName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", waterSourceDevices=" + waterSourceDevices +
                ", infrastructureDevices=" + infrastructureDevices +
                '}';
    }
}
