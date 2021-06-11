package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models;

import org.springframework.data.neo4j.core.schema.Id;

import java.util.UUID;

public class sensorConfiguration
{
    @Id
    private UUID configId ;
    private String settingType ;
    private double value;

    public sensorConfiguration(String settingType, double value)
    {
        configId=UUID.randomUUID();
        this.settingType = settingType;
        this.value = value;
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
}
