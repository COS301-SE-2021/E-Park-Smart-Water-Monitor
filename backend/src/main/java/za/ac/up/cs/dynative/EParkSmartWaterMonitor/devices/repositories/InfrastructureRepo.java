package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository("InfrastructureRepo")
public interface InfrastructureRepo extends Neo4jRepository<InfrastructureRepo, UUID> {
}
