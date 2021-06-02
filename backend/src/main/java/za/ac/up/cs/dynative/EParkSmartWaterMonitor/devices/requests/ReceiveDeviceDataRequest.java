package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class ReceiveDeviceDataRequest {

    private String deviceName;
    private double value;
    private String unitOfMeasurement;
    private String deviceDateTime;
    private String type;

    public ReceiveDeviceDataRequest(
                                            @JsonProperty("deviceName") String deviceName,
                                            @JsonProperty("type") String type,
                                            @JsonProperty("value") double value,
                                            @JsonProperty("unitOfMeasurement") String unitOfMeasurement,
                                            @JsonProperty("deviceDateTime") String deviceDateTime
                                    )
    {
        this.deviceName = deviceName;
        this.value = value;
        this.unitOfMeasurement = unitOfMeasurement;
        this.type = type;
        this.deviceDateTime = deviceDateTime;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public String getDeviceDateTime() {
        return deviceDateTime;
    }

    public void setDeviceDateTime(String deviceDateTime) {
        this.deviceDateTime = deviceDateTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
