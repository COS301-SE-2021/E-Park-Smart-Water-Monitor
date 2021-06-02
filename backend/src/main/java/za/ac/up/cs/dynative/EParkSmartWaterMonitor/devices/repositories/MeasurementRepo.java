package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.measurement;

import java.util.UUID;

@Repository("SourceDataRepo")
public interface MeasurementRepo extends Neo4jRepository<measurement, UUID> {
}
