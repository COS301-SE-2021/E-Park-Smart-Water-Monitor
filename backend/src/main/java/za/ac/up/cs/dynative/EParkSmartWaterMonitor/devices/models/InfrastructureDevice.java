package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.ArrayList;
import java.util.UUID;

@Node
public class InfrastructureDevice {

    @Id
    private UUID deviceId;
    private String deviceModel;
    private String deviceName;
    private DeviceData deviceData;
    private String deviceType;


    public InfrastructureDevice(String deviceName,
                                String deviceModel,
                                double longitude,
                                double latitude)
    {
        this.deviceId = UUID.randomUUID();
        this.deviceModel = deviceModel;
        this.deviceName = deviceName;
        this.deviceType= "Infrastructure";


        ArrayList<sensorConfiguration> deviceConfiguration = new ArrayList<>();
        deviceConfiguration.add(new sensorConfiguration("reportingFrequency",4.0));
        deviceConfiguration.add(new sensorConfiguration("flowRateSensitivity",1.0));

        this.deviceData = new DeviceData(longitude,latitude,100,"FINE",100,1,deviceConfiguration);



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
