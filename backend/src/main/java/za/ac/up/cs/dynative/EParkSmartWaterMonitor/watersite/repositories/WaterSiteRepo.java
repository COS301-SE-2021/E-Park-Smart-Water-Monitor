package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;

import java.util.UUID;

@Repository("WaterSiteRepo")
public interface WaterSiteRepo extends Neo4jRepository<WaterSite, UUID>
{
    WaterSite findByWaterSiteName(String SiteName);

    @Query("MATCH (w:WaterSite {id: $id})-[*0..]->(graphFromWatersiteOutward) detach delete graphFromWatersiteOutward")
    void deletEntireWaterSite(@Param("id") UUID id);
}