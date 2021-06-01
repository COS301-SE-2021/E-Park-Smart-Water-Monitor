package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.Date;
import java.util.UUID;

@Node
public class SourceData {

    @Id
    private UUID dataId;

    private double waterLevel;

    private double waterQuality;

    private double waterTemperature;

    String deviceDateTime;

    private Date dateTime;

    public SourceData(UUID dataId, double waterLevel, double waterQuality, double waterTemperature, String deviceDateTime, Date dateTime) {
        this.dataId = dataId;
        this.waterLevel = waterLevel;
        this.waterQuality = waterQuality;
        this.waterTemperature = waterTemperature;
        this.deviceDateTime = deviceDateTime;
        this.dateTime = dateTime;
    }

    public SourceData(double waterLevel, double waterQuality, double waterTemperature, String deviceDateTime, Date dateTime) {
        this.dataId = UUID.randomUUID();
        this.waterLevel = waterLevel;
        this.waterQuality = waterQuality;
        this.waterTemperature = waterTemperature;
        this.deviceDateTime = deviceDateTime;
        this.dateTime = dateTime;
    }

    public UUID getId() {
        return dataId;
    }

    public void setId(UUID dataId) {
        this.dataId = dataId;
    }

    public double getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(double waterLevel) {
        this.waterLevel = waterLevel;
    }

    public double getWaterQuality() {
        return waterQuality;
    }

    public void setWaterQuality(double waterQuality) {
        this.waterQuality = waterQuality;
    }

    public double getWaterTemperature() {
        return waterTemperature;
    }

    public void setWaterTemperature(double waterTemperature) {
        this.waterTemperature = waterTemperature;
    }

    public String getDeviceDateTime() {
        return deviceDateTime;
    }

    public void setDeviceDateTime(String deviceDateTime) {
        this.deviceDateTime = deviceDateTime;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "SourceData{" +
                "dataId=" + dataId +
                ", waterLevel=" + waterLevel +
                ", waterQuality=" + waterQuality +
                ", waterTemperature=" + waterTemperature +
                ", dateTime=" + dateTime +
                '}';
    }
}
