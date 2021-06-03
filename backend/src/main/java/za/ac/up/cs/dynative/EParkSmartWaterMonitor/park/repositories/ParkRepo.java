package za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository("ParkRepo")
public interface ParkRepo extends Neo4jRepository<Park, UUID> {

    Collection<Park> findParkByParkName(String parkName);
    Park findParkById(UUID id);


}
