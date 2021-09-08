package za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata.models;

import java.util.ArrayList;

public class GeoJSON {
    private String type;
    private ArrayList<GeoFeatures> features;

    public GeoJSON(ArrayList<GeoFeatures> features)
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

    public ArrayList<GeoFeatures> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<GeoFeatures> features) {
        this.features = features;
    }
}
