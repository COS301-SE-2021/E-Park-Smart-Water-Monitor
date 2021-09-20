package za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata.models;

public class FeatureProperties {
    private double dataValue;
    private String fill;
    private String stroke;

    public FeatureProperties(double dataValue, String colour)
    {
        this.dataValue = dataValue;
        this.fill = colour;
        this.stroke = colour;
    }

    public double getDataValue() {
        return dataValue;
    }

    public void setDataValue(double dataValue) {
        this.dataValue = dataValue;
    }

    public String getFill() {
        return fill;
    }

    public void setFill(String fill) {
        this.fill = fill;
    }

    public String getStroke() {
        return stroke;
    }

    public void setStroke(String stroke) {
        this.stroke = stroke;
    }
}
