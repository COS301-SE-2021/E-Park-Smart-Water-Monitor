package za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata.models;

import java.util.ArrayList;

public class GeoFeatures {
    private String type;
    private FeatureProperties properties;
    private ArrayList<ArrayList<ArrayList<Double>>> coordinates;

    public GeoFeatures(FeatureProperties properties, ArrayList<ArrayList<ArrayList<Double>>> coordinates)
    {
        this.type = "geometry";
        this.properties = properties;
        this.coordinates = coordinates;
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

    public ArrayList<ArrayList<ArrayList<Double>>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<ArrayList<ArrayList<Double>>> coordinates) {
        this.coordinates = coordinates;
    }
}
