package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetDeviceDataRequest {

    private final String deviceName;
    private final int numResults;
    private final boolean sorted;

    public GetDeviceDataRequest(@JsonProperty("deviceName") String deviceName,
                                @JsonProperty("numResults") int numResults,
                                @JsonProperty("sorted") boolean sorted) {
        this.deviceName = deviceName;
        this.numResults = numResults;
        this.sorted = sorted;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public int getNumResults() { return numResults; }

    public boolean isSorted() {
        return sorted;
    }

    @Override
    public String toString() {
        return "GetDeviceDataRequest{" +
                "deviceName='" + deviceName + '\'' +
                '}';
    }
}
