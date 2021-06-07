package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models;

import org.springframework.data.neo4j.core.schema.Id;

import java.util.UUID;

public class DeviceData {

    @Id
    private UUID deviceDataId;
    private double longitude;
    private double latitude;
    private double battery;
    private String deviceStatus;
    private double upTime;
    private double lifeTime;

    public DeviceData(double longitude,
                      double latitude,
                      double battery,
                      String deviceStatus,
                      double upTime,
                      double lifeTime) {
        this.deviceDataId = UUID.randomUUID();
        this.longitude = longitude;
        this.latitude = latitude;
        this.battery = battery;
        this.deviceStatus = deviceStatus;
        this.upTime = upTime;
        this.lifeTime = lifeTime;
    }

    public DeviceData() {
        this.deviceDataId = UUID.randomUUID();
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

    public double getBattery() {
        return battery;
    }

    public void setBattery(double battery) {
        this.battery = battery;
    }
    
    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public double getUpTime() {
        return upTime;
    }

    public void setUpTime(double upTime) {
        this.upTime = upTime;
    }

    public double getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(double lifeTime) {
        this.lifeTime = lifeTime;
    }

    public enum DeviceStatus {
        FINE, DIRE, NEEDS_ATTENTION
    }
}
