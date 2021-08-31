package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Measurement;

import java.util.List;

public class WaterSourceData
{

    String deviceName;
    List<Measurement> measurements;

    public WaterSourceData(
            @JsonProperty("deviceName") String deviceName,
            @JsonProperty("measurements")  List<Measurement> measurements) {
        this.deviceName = deviceName;
        this.measurements = measurements;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    @Override
    public String toString() {
        return "DataNotificationRequest{" +
                "deviceName='" + deviceName + '\'' +
                ", measurements=" + measurements +
                '}';
    }
}
