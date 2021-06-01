package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models;
import org.springframework.data.neo4j.core.schema.Id;
        import org.springframework.data.neo4j.core.schema.Node;
        import org.springframework.data.neo4j.core.schema.Relationship;
        import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.Site;

        import java.util.HashSet;
        import java.util.Set;
        import java.util.UUID;

        import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;
import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Node
public class WaterSourceDevice
{
    @Id
    private UUID    deviceId;
    private String  deviceModel;
    private String  deviceName;
    private double  longitude;
    private double  latitude;

    @Relationship(type = "PRODUCES", direction = OUTGOING)
    private Set<SourceData> deviceDataProduced;

    public WaterSourceDevice(String deviceName, String deviceModel,double  longitude,double  latitude, Set<SourceData> deviceDataProduced)
    {
        this.deviceId    = UUID.randomUUID();
        this.deviceName  = deviceName;
        this.deviceModel = deviceModel;
        this.longitude   = longitude;
        this.latitude    = latitude;
        this.deviceDataProduced = deviceDataProduced;
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

    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

}
