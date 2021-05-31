package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;

@Node
public class WaterSourceDevice
{
    @Id
    private final String deviceName;

    @Relationship(type = "MONITORED_BY", direction = INCOMING)
    private Set<Site> monitored_by = new HashSet<>();

    public WaterSourceDevice(String deviceName)
    {
        this.deviceName=deviceName;

    }

//    public WaterSourceDevice() {}

    public String getDeviceName() {
        return deviceName;
    }

    public Set<Site> getMonitored_by() {
        return monitored_by;
    }

    public void setMonitored_by(Set<Site> monitored_by) {
        this.monitored_by = monitored_by;
    }
}
