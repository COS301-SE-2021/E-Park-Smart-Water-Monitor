package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Device;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

/**
 * The class represents a site within the database, hence the @Node annotation.
 */
@Node
public class Site {

    /**
     * Attributes:
     */
    @Id
    @GeneratedValue
    private  Long siteId;
    private  String siteName;

    /**
     * The relationship that needs to be maintained between nodes.
     */
    @Relationship(type = "WATER_MONITORED_BY", direction = OUTGOING)
    private Set<Device> devices = new HashSet<>();

    /**
     * Custom constructor of the Site.
     * @param sName The name of the site.
     */
    public Site(String sName) {
        this.siteName=sName;
    }

    /**
     * Default constructor.
     */
    public Site() {}

    /**
     * Get the id of a water site.
     * @return A long (integer) value will be returned with the id, that in turn will be converted into the UUID.
     */
    public Long getSiteId() {
        return siteId;
    }

    /**
     * Retrieve the name of the Site by giving the id.
     * @return Return the name of the specified water site.
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * Method to add a new water source device to the database.
     * @param newDevice The Device object and all its details.
     */
    public void addWaterSourceDevice(Device newDevice) {
        if (devices == null) {
            devices = new HashSet<>();
        }
        devices.add(newDevice);
    }


}
