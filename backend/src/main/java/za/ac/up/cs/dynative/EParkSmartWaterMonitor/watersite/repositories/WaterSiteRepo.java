package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceDevice;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.Site;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;

import java.util.UUID;

@Repository("WaterSiteRepo")
public interface WaterSiteRepo extends Neo4jRepository<WaterSite, UUID>
{
    WaterSite findByWaterSiteName(String SiteName);
}