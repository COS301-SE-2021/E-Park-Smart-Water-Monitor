package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models;

import org.springframework.data.neo4j.core.schema.Id;

import java.util.UUID;

public class sensorConfiguration
{
    @Id
    private UUID configId ;
    private String settingType ;
    private double value;
    private double upperLimit;
    private double lowerLimit;

    public sensorConfiguration(String settingType, double value)
    {
        configId=UUID.randomUUID();
        this.settingType = settingType;
        this.value = value;
        this.upperLimit = 100;
        this.lowerLimit = 10;
    }

    public void setConfigId(UUID configId) {
        this.configId = configId;
    }

    public double getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(double upperLimit) {
        this.upperLimit = upperLimit;
    }

    public double getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(double lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public UUID getConfigId() {
        return configId;
    }

    public String getSettingType() {
        return settingType;
    }

    public void setSettingType(String settingType) {
        this.settingType = settingType;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "{\"sensorConfiguration\":{" +
                "\"settingType\": \"" + settingType + "\"" +
                ", \"value\":" + value +
                "}}";
    }
}
