package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.Site;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;

@Node
public class WaterSourceDevice
{
    @Id
    private  String deviceName;

    @Relationship(type = "MONITORED_BY", direction = INCOMING)
    private Set<Site> monitor_site = new HashSet<>();

    public WaterSourceDevice(String deviceName)
    {
        this.deviceName=deviceName;

    }

    public WaterSourceDevice()
    {

    }

    public String getDeviceName()
    {
        return deviceName;
    }

    public Set<Site> getMonitored_by() {
        return monitor_site;
    }

//    public void setMonitored_by(Set<Site> monitored_by) {
//        this.monitor_site = monitored_by;
//    }
}
