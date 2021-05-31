package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.UUID;

@Node
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

//    public Site() {
//    }

    public UUID getSiteId() {
        return SiteId;
    }

    public String getSiteName() {
        return SiteName;
    }
}
