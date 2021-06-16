package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetDeviceDataRequest {

    private final String deviceName;
    private final int numResults;

    public GetDeviceDataRequest(@JsonProperty("deviceName") String deviceName,
                                @JsonProperty("numResults") int numResults) {
        this.deviceName = deviceName;
        this.numResults = numResults;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public int getNumResults() { return numResults; }

    @Override
    public String toString() {
        return "GetDeviceDataRequest{" +
                "deviceName='" + deviceName + '\'' +
                '}';
    }
}
