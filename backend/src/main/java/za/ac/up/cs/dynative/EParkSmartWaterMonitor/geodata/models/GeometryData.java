package za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata.models;

import java.util.ArrayList;

public class GeometryData {
    private String type;
    private ArrayList<ArrayList<ArrayList<Double>>> coordinates = new ArrayList<>();

    public GeometryData(ArrayList<ArrayList<Double>> coordinates) {
        this.type = "Polygon";
        this.coordinates.add(coordinates);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<ArrayList<ArrayList<Double>>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<ArrayList<ArrayList<Double>>> coordinates) {
        this.coordinates = coordinates;
    }
}
