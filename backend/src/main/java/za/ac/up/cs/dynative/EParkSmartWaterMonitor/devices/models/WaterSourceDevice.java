package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models;
import org.springframework.data.neo4j.core.schema.Id;
        import org.springframework.data.neo4j.core.schema.Node;
        import org.springframework.data.neo4j.core.schema.Relationship;

import javax.xml.crypto.Data;
import java.util.HashSet;
        import java.util.Set;
        import java.util.UUID;


@Node
public class WaterSourceDevice
{
    @Id
    private UUID    deviceId;
    private String  deviceModel;
    private String  deviceName;
    private DeviceData deviceData;

    @Relationship(type = "PRODUCES", direction = Relationship.Direction.OUTGOING)
    private Set<SourceData> deviceDataProduced;

    public WaterSourceDevice(String deviceName,
                             String deviceModel,
                             double longitude,
                             double latitude)
    {
        this.deviceId    = UUID.randomUUID();
        this.deviceName  = deviceName;
        this.deviceModel = deviceModel;
        this.deviceData = new DeviceData();
        this.deviceData.setLatitude(latitude);
        this.deviceData.setLatitude(longitude);
    }



    public WaterSourceDevice()
    {

    }

    public void addDeviceDataProduced(SourceData data) {
        if (deviceDataProduced == null) {
            deviceDataProduced = new HashSet<>();
        }
        deviceDataProduced.add(data);
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

    public Set<SourceData> getDeviceDataProduced() {
        return deviceDataProduced;
    }

    public void setDeviceDataProduced(Set<SourceData> deviceDataProduced) {
        this.deviceDataProduced = deviceDataProduced;
    }
}
