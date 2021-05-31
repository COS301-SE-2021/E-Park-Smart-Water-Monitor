package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.Site;

@Repository("SiteRepo")
public interface SiteRepo extends Neo4jRepository<Site, Long>
{
//    Site findBySiteId(Long SiteId);
    Site findBySiteName(String SiteName);

}