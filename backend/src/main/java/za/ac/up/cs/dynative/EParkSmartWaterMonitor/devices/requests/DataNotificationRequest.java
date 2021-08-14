package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceData;

public class DataNotificationRequest {

    //'deviceName':deviceName,
//        'timestamp':date,
//        'WaterSourceData':{
//    'deviceName':deviceName,
//            'measurements':newMeasurements

    String deviceName;
    String timestamp;
    WaterSourceData WaterSourceData;

    public DataNotificationRequest(@JsonProperty("deviceName") String deviceName,
                                   @JsonProperty("timestamp") String timestamp,
                                   @JsonProperty("WaterSourceData") za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceData waterSourceData) {
        this.deviceName = deviceName;
        this.timestamp = timestamp;
        WaterSourceData = waterSourceData;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceData getWaterSourceData() {
        return WaterSourceData;
    }

    public void setWaterSourceData(za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceData waterSourceData) {
        WaterSourceData = waterSourceData;
    }
}
