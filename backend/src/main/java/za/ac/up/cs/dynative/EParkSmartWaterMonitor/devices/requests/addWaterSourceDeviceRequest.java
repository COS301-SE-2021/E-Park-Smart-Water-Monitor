package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class addWaterSourceDeviceRequest {


    private UUID deviceId;
    private UUID siteId;
    private String  deviceModel;
    private String  deviceName;
    private double  longitude;
    private double  latitude;
    private String parkName;



    public addWaterSourceDeviceRequest(@JsonProperty("parkName") String parkName, @JsonProperty("siteId") UUID siteId, @JsonProperty("deviceId") UUID deviceId, @JsonProperty("deviceModel") String deviceModel, @JsonProperty("deviceName") String deviceName, @JsonProperty("longitude") double longitude, @JsonProperty("latitude") double latitude) {
        this.deviceId       =   deviceId;
        this.deviceModel    =   deviceModel;
        this.deviceName     =   deviceName;
        this.longitude      =   longitude;
        this.latitude       =   latitude;
        this.parkName       =   parkName;
        this.siteId         =   siteId;
    }

    public UUID getSiteId() {
        return siteId;
    }

    public void setSiteId(UUID siteId) {
        this.siteId = siteId;
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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }
}
