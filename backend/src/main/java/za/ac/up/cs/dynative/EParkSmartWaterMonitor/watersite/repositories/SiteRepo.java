package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.Site;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;

import java.util.UUID;

@Repository("SiteRepo")
public interface SiteRepo extends Neo4jRepository<WaterSite, UUID>
{
//    Site findBySiteId(Long SiteId);
    WaterSite findByWaterSiteName(String SiteName);
}