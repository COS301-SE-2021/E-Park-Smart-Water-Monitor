package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.UUID;

@Node("Site")
public class Site
{
    @Id
    private final UUID SiteId;
    private final String SiteName;

    public Site(String sName)
    {
        this.SiteId = UUID.randomUUID();
        this.SiteName=sName;

    }

}
