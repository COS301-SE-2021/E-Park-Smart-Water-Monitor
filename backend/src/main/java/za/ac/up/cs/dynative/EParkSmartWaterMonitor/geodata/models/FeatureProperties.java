package za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata.models;

public class FeatureProperties {
    private double dataValue;

    public FeatureProperties(double dataValue) {
        this.dataValue = dataValue;
    }

    public double getDataValue() {
        return dataValue;
    }

    public void setDataValue(double dataValue) {
        this.dataValue = dataValue;
    }
}
