package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.Date;
import java.util.UUID;

@Node
public class Measurement {

    @Id
    private UUID dataId;
    private String type;
    private String unitOfMeasurement;
    private double value;
    private String deviceDateTime;
    private Date dateTime;

    public Measurement(
            @JsonProperty("type") String type,
            @JsonProperty("value") double value,
            @JsonProperty("unitOfMeasurement") String unitOfMeasurement,
            @JsonProperty("deviceDateTime") String deviceDateTime
)
    {
        this.dataId = UUID.randomUUID();

        this.type = type;
        this.unitOfMeasurement = unitOfMeasurement;
        this.value = value;

        this.deviceDateTime = deviceDateTime;
        this.dateTime = new Date();
    }

    public UUID getId() {
        return dataId;
    }

    public void setId(UUID dataId) {
        this.dataId = dataId;
    }


    @Override
    public String toString() {
        return "measurement{" +
                "dataId=" + dataId +
                ", type='" + type + '\'' +
                ", unitOfMeasurement='" + unitOfMeasurement + '\'' +
                ", value=" + value +
                ", deviceDateTime='" + deviceDateTime + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }

    public UUID getDataId() {
        return dataId;
    }

    public void setDataId(UUID dataId) {
        this.dataId = dataId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
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


}
