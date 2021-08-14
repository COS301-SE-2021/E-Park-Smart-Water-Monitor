package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataNotification {
    String deviceName;
    WaterSourceData waterSourceData;
    String timestamp;


    public DataNotification(@JsonProperty("deviceName") String deviceName, @JsonProperty("WaterSourceData") WaterSourceData waterSourceData, @JsonProperty("timestamp") String timestamp)
    {
        this.deviceName = deviceName;
        this.waterSourceData = waterSourceData;
        this.timestamp=timestamp;

    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public WaterSourceData getWaterSourceData() {
        return waterSourceData;
    }

    public void setWaterSourceData(WaterSourceData waterSourceData) {
        this.waterSourceData = waterSourceData;
    }
}
