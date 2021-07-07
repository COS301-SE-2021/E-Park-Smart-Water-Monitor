package za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;

import java.util.*;

@Repository("ParkRepo")
public interface ParkRepo extends Neo4jRepository<Park, UUID> {

    List<Park> findParkByParkName(String parkName);
    Park findParkById(UUID id);

    @Query("match (n:Park) return n")
    List<Park> getAllParks();

}
