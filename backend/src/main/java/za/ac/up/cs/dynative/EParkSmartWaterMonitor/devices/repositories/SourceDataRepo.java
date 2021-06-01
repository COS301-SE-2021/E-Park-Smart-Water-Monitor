package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.SourceData;

@Repository("SourceDataRepo")
public interface SourceDataRepo extends Neo4jRepository<SourceData, Long> {
}
