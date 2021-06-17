package za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceDevice;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.models.Inspection;

import java.util.List;
import java.util.UUID;

@Repository("InspectionRepo")
public interface InspectionRepo extends Neo4jRepository<Inspection, UUID> {

    Inspection findInspectionById(UUID id);
    List<Inspection> findInspectionsByWaterSiteId(UUID id);

    @Query("MATCH (i:Inspection)-[:PERFORMED_ON]->(d:WaterSourceDevice {deviceId: $id}) return i")
    List<Inspection> getInspectionByDeviceId(@Param("id") UUID id);
}
