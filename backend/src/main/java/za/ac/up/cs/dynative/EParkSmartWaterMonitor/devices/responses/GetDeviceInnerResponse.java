package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Measurement;

import java.util.ArrayList;

public class GetDeviceInnerResponse {

    private String status;
    private Boolean success;
    private String deviceName;
    private ArrayList<Measurement> measurements;

    public GetDeviceInnerResponse(String status, Boolean success) {
        this.status = status;
        this.success = success;
    }

    public GetDeviceInnerResponse() {
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public ArrayList<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(ArrayList<Measurement> measurements) {
        this.measurements = measurements;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

}
