package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.UUID;

@Node
public class WaterSite {

    @Id
    private UUID id;

    private String waterSiteName;

    private double latitude;

    private double longitude;

    public WaterSite(UUID id, String waterSiteName, double latitude, double longitude) {
        this.id = id;
        this.waterSiteName = waterSiteName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public WaterSite() {
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

    @Override
    public String toString() {
        return "WaterSite{" +
                "id=" + id +
                ", waterSiteName='" + waterSiteName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
