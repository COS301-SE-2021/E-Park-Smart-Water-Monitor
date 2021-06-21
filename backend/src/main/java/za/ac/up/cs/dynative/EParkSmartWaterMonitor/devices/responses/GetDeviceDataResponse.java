package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses;

import java.util.ArrayList;

public class GetDeviceDataResponse {

    private String status;
    private Boolean success;
    private String deviceName;
    private ArrayList<GetDeviceInnerResponse> innerResponses;

    public GetDeviceDataResponse(String status, Boolean success) {
        this.status = status;
        this.success = success;
    }

    public GetDeviceDataResponse() {
    }

    public void addInnerResponse(GetDeviceInnerResponse innerResponse) {
        if (innerResponses == null)
            innerResponses = new ArrayList<>();

        innerResponses.add(innerResponse);
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public ArrayList<GetDeviceInnerResponse> getInnerResponses() {
        return innerResponses;
    }

    public void setInnerResponses(ArrayList<GetDeviceInnerResponse> innerResponses) {
        this.innerResponses = innerResponses;
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
