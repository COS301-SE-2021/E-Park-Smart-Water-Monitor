package za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.models.Inspection;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;

import java.util.*;

@Node
public class Park {

    @Id
    private UUID id;

    private String parkName;

    private double latitude;

    private double longitude;

    @Relationship(type = "HAS_WEATHER_DATA", direction = Relationship.Direction.OUTGOING)
    private Set<WeatherData> parkWeather;

    @Relationship(type = "HAS_WATER_SITE", direction = Relationship.Direction.OUTGOING)
    private Set<WaterSite> parkWaterSites;

    public Park(String parkName,
                double latitude,
                double longitude,
                Set<WeatherData> parkWeather,
                Set<WaterSite> parkWaterSites) {

        this.id = UUID.randomUUID();
        this.parkName = parkName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.parkWeather = parkWeather;
        this.parkWaterSites = parkWaterSites;
    }

    private List<Inspection> InspectionsList;

    public Park(String parkName, double latitude, double longitude) {
        this.id = UUID.randomUUID();
        this.parkName = parkName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.InspectionsList= new ArrayList<>();
    }

    public Park() {
    }

    public void addWaterSite(WaterSite waterSite) {
        if (parkWaterSites == null) {
            parkWaterSites = new HashSet<>();
        }
        parkWaterSites.add(waterSite);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Set<WeatherData> getParkWeather() {
        return parkWeather;
    }

    public void setParkWeather(Set<WeatherData> parkWeather) {
        this.parkWeather = parkWeather;
    }

    public Set<WaterSite> getParkWaterSites() {
        return parkWaterSites;
    }

    public void setParkWaterSites(Set<WaterSite> parkWaterSites) {
        this.parkWaterSites = parkWaterSites;
    }

    @Override
    public String toString() {
        return "Park{" +
                "id=" + id +
                ", parkName='" + parkName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", parkWeather=" + parkWeather +
                ", parkWaterSites=" + parkWaterSites +
                '}';
    }
}
