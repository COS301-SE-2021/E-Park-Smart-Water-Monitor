package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Device;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Node
public class Site
{
    @Id
    @GeneratedValue
    private  Long siteId;
    private  String siteName;


    @Relationship(type = "WATER_MONITORED_BY", direction = OUTGOING)
    private Set<Device> devices = new HashSet<>();


    public Site(String sName)
    {
        this.siteName=sName;
    }

    public Site() {}

    public Long getSiteId() {
        return siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void addWaterSourceDevice(Device newDevice) {
        if (devices == null) {
            devices = new HashSet<>();
        }
        devices.add(newDevice);
    }


}
