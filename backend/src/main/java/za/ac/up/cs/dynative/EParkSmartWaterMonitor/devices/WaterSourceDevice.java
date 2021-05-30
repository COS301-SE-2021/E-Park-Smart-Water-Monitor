package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;

@Node("WaterSourceDevice")
public class WaterSourceDevice
{
    @Id
    private final String deviceName;
    @Relationship(type = "MONITORED_BY", direction = INCOMING)
    private Set<Site> monitored_by = new HashSet<>();

    public WaterSourceDevice(String deviceID, String deviceName)
    {
        this.deviceName=deviceName;

    }
}
