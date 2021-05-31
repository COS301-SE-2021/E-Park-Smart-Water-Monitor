package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.Date;

@Node
public class SourceData {

    @Id
    @GeneratedValue
    private long id;

    private double waterLevel;

    private double waterQuality;

    private double waterTemperature;

    private Date dateTime;

    public SourceData(long id, double waterLevel, double waterQuality, double waterTemperature, Date dateTime) {
        this.id = id;
        this.waterLevel = waterLevel;
        this.waterQuality = waterQuality;
        this.waterTemperature = waterTemperature;
        this.dateTime = dateTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "SourceData{" +
                "id=" + id +
                ", waterLevel=" + waterLevel +
                ", waterQuality=" + waterQuality +
                ", waterTemperature=" + waterTemperature +
                ", dateTime=" + dateTime +
                '}';
    }
}
