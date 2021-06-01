package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReceiveDeviceDataRequest {

    private String deviceName;
    private double waterLevel;
    private double waterQuality;
    private double waterTemperature;
    private String deviceDateTime;

    public ReceiveDeviceDataRequest(@JsonProperty("deviceName") String deviceName, @JsonProperty("waterLevel") double waterLevel, @JsonProperty("waterQuality") double waterQuality, @JsonProperty("waterTemperature") double waterTemperature, @JsonProperty("deviceDateTime") String deviceDateTime) {
        this.deviceName = deviceName;
        this.waterLevel = waterLevel;
        this.waterQuality = waterQuality;
        this.waterTemperature = waterTemperature;
        this.deviceDateTime = deviceDateTime;
    }

    public ReceiveDeviceDataRequest() {
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
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
}
