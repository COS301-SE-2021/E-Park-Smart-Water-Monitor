package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses;

public class GetNumDevicesResponse {

    private int numDevices;
    boolean success;

    public GetNumDevicesResponse(int numDevices, boolean success) {
        this.numDevices = numDevices;
        this.success = success;
    }

    public GetNumDevicesResponse() {
    }

    public int getNumDevices() {
        return numDevices;
    }

    public void setNumDevices(int numDevices) {
        this.numDevices = numDevices;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
