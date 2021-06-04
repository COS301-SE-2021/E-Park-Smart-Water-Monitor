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
    private DeviceData deviceData;


    public InfrastructureDevice(String deviceModel,
                                String deviceName,
                                DeviceData deviceData) {
        this.deviceId = UUID.randomUUID();
        this.deviceModel = deviceModel;
        this.deviceName = deviceName;
        this.deviceData = deviceData;
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

    public DeviceData getDeviceData() {
        return deviceData;
    }
}
