package za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata.models;

import java.util.ArrayList;

public class GeoJSON {
    private String type;
    private ArrayList<geoFeatures> features;

    public GeoJSON(ArrayList<geoFeatures> features)
    {
        this.type="FeatureCollection";
        this.features = features;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<geoFeatures> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<geoFeatures> features) {
        this.features = features;
    }
}
