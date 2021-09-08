package za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata.models;

public class GeoFeatures {
    private String type;
    private FeatureProperties properties;
    private GeometryData geometry;

    public GeoFeatures(FeatureProperties properties, GeometryData geometry)
    {
        this.type = "Feature";
        this.properties = properties;
        this.geometry = geometry;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FeatureProperties getProperties() {
        return properties;
    }

    public void setProperties(FeatureProperties properties) {
        this.properties = properties;
    }

    public GeometryData getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryData geometry) {
        this.geometry = geometry;
    }
}
