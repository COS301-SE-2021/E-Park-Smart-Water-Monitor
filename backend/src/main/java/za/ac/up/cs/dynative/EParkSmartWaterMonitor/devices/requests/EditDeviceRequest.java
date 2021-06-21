package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests;

import java.util.UUID;

public class EditDeviceRequest
{
    private UUID deviceId;
    private String  deviceModel;
    private String  deviceName;
    private String deviceType;

    public EditDeviceRequest(UUID deviceId, String deviceType, String deviceModel, String deviceName)
    {
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.deviceModel = deviceModel;
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
