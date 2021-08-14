package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models;

import org.springframework.data.neo4j.core.schema.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DeviceData {

    @Id
    private UUID deviceDataId;
    private Date lastSeen;
    private double longitude;
    private double latitude;
    private double battery;
    private String deviceStatus;
    private double upTime;
    private double lifeTime;
    private ArrayList<sensorConfiguration> deviceConfiguration;



    public DeviceData(double longitude,
                      double latitude,
                      double battery,
                      String deviceStatus,
                      double upTime,
                      double lifeTime,
                      ArrayList<sensorConfiguration> deviceConfiguration
                      ) {

        this.deviceDataId = UUID.randomUUID();
        this.longitude = longitude;
        this.latitude = latitude;
        this.battery = battery;
        this.deviceStatus = deviceStatus;
        this.upTime = upTime;
        this.lifeTime = lifeTime;
        this.deviceConfiguration=deviceConfiguration;
    }

    public Date getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Date lastSeen) {
        this.lastSeen = lastSeen;
    }

    public void setDeviceDataId(UUID deviceDataId) {
        this.deviceDataId = deviceDataId;
    }

    public UUID getDeviceDataId() {
        return deviceDataId;
    }

    public ArrayList<sensorConfiguration> getDeviceConfiguration() {
        return deviceConfiguration;
    }

    public void setDeviceConfiguration(ArrayList<sensorConfiguration> deviceConfiguration) {
        this.deviceConfiguration = deviceConfiguration;
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

    @Override
    public String toString() {
        return "\"DeviceData\":{" +
                "\"longitude\":" + "\"" +longitude + "\"" +
                ", \"latitude\":" + "\"" +latitude + "\"" +
                ", \"battery\":" + "\"" +battery + "\"" +
                ", \"deviceStatus\":" + "\"" +deviceStatus + "\"" +
                ", \"upTime\":" + "\"" +upTime + "\"" +
                ", \"lifeTime\":" + "\"" +lifeTime + "\"" +
                ", \"deviceConfiguration\":[" + deviceConfiguration.toString().replaceAll("\\[","").replaceAll("]","") + "]}" +
                '}';
    }

    public enum DeviceStatus {
        FINE, DIRE, NEEDS_ATTENTION
    }
}
