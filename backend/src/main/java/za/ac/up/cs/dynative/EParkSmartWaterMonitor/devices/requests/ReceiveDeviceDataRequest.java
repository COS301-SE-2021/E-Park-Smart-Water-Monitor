package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Measurement;

import java.util.Date;
import java.util.List;

public class ReceiveDeviceDataRequest {

    private String deviceName;
    private List<Measurement> measurements;

    public ReceiveDeviceDataRequest(
            @JsonProperty("deviceName") String deviceName,
            @JsonProperty("measurements") List<Measurement> measurements
    )
    {
        this.deviceName = deviceName;
        this.measurements=measurements;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

//    public double getValue() {
//        return value;
//    }

//    public void setValue(double value) {
//        this.value = value;
//    }

//    public String getUnitOfMeasurement() {
//        return unitOfMeasurement;
//    }

//    public void setUnitOfMeasurement(String unitOfMeasurement) {
//        this.unitOfMeasurement = unitOfMeasurement;
//    }
//
//    public String getDeviceDateTime() {
//        return deviceDateTime;
//    }

//    public void setDeviceDateTime(String deviceDateTime) {
//        this.deviceDateTime = deviceDateTime;
//    }

    @Override
    public String toString() {
        return "ReceiveDeviceDataRequest{" +
                "deviceName='" + deviceName + '\'' +
                ", measurements=" + measurements +
                '}';
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

//    public String getType() {
//        return type;
//    }

//    public void setType(String type) {
//        this.type = type;
//    }
}
