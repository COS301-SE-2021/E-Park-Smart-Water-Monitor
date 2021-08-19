package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models;
import org.springframework.data.neo4j.core.schema.Id;
        import org.springframework.data.neo4j.core.schema.Node;
        import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.*;


@Node
public class Device
{
    @Id
    private UUID    deviceId;
    private String  deviceModel;
    private String  deviceName;
    private String  deviceType;
    private DeviceData deviceData;

    @Relationship(type = "PRODUCES", direction = Relationship.Direction.OUTGOING)
    private Set<Measurement> measurementSet;

    public Device(UUID deviceId,
                  String deviceName,
                  String deviceType,
                  String deviceModel,
                  double longitude,
                  double latitude)
    {
        this.deviceId    = deviceId;
        this.deviceName  = deviceName;
        this.deviceModel = deviceModel;
        this.deviceType= deviceType;

        ArrayList<sensorConfiguration> deviceConfiguration = new ArrayList<>();
        deviceConfiguration.add(new sensorConfiguration("reportingFrequency",4.0));
        deviceConfiguration.add(new sensorConfiguration("WATER_TEMP",1.0));
        deviceConfiguration.add(new sensorConfiguration("WATER_LEVEL",1.0));
        deviceConfiguration.add(new sensorConfiguration("WATER_QUALITY",1.0));

        this.deviceData = new DeviceData(longitude,latitude,100,"FINE",100,1,deviceConfiguration);

    }

    public Device()
    {

    }

    public void addDeviceDataProduced(Measurement data) {
        if (measurementSet == null) {
            measurementSet = new HashSet<>();
        }
        measurementSet.add(data);
    }

    public void wipeData()
    {
//        measurementSet.clear();
        measurementSet.removeAll(measurementSet);
    }

    public String getDeviceName()
    {
        return deviceName;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public DeviceData getDeviceData() {
        return deviceData;
    }

    public void setDeviceData(DeviceData deviceData) {
        this.deviceData = deviceData;
    }

    public Set<Measurement> getMeasurementSet() {
        return measurementSet;
    }

    public void setMeasurementSet(Set<Measurement> measurementSet) {
        this.measurementSet = measurementSet;
    }

    @Override
    public String toString() {
        return "WaterSourceDevice:{" +
                "deviceId=" + deviceId +
                ", deviceModel='" + deviceModel + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", deviceData=" + deviceData +
                ", measurementSet=" + measurementSet +
                '}';
    }
}
