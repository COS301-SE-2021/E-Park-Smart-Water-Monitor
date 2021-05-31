package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.WaterSourceDevice;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;
import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Node
public class Site
{
    @Id
    @GeneratedValue
    private  Long siteId;
    private  String siteName;


    @Relationship(type = "WATER_MONITORED_BY", direction = OUTGOING)
    private Set<WaterSourceDevice> waterSourceDevices = new HashSet<>();


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

    public void addWaterSourceDevice(WaterSourceDevice newWaterSourceDevice) {
        if (waterSourceDevices == null) {
            waterSourceDevices = new HashSet<>();
        }
        waterSourceDevices.add(newWaterSourceDevice);
    }


}
