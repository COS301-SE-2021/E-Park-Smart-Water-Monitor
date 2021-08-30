package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class EditDeviceRequest
{
    private UUID deviceId;
    private String  deviceModel;
    private String  deviceName;
    private String deviceType;
    private double latitude;
    private double longitude;

    public EditDeviceRequest(UUID deviceId, String deviceType, String deviceModel, String deviceName)
    {
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.deviceModel = deviceModel;
        this.deviceName = deviceName;
    }

    public EditDeviceRequest(@JsonProperty("deviceId") UUID deviceId,
                             @JsonProperty("deviceType") String deviceType,
                             @JsonProperty("deviceModel") String deviceModel,
                             @JsonProperty("deviceName") String deviceName,
                             @JsonProperty("latitude") double latitude,
                             @JsonProperty("longitude")double longitude)
    {
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.deviceModel = deviceModel;
        this.deviceName = deviceName;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
