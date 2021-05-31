package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.UUID;

@Node
public class InfrastructureDevice {

    @Id
    private UUID deviceId;
    private String deviceModel;
    private String deviceName;
    private double longitude;
    private double latitude;

    public InfrastructureDevice(String deviceModel, String deviceName, double longitude, double latitude) {
        this.deviceId = UUID.randomUUID();;
        this.deviceModel = deviceModel;
        this.deviceName = deviceName;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public InfrastructureDevice() {
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
}
