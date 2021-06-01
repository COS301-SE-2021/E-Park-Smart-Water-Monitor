package za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;

import java.util.UUID;

@Repository("ParkRepo")
public interface ParkRepo extends Neo4jRepository<Park, UUID> {

    Park findParkByParkName(String parkName);

}
