package za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;

import java.util.*;

@Repository("ParkRepo")
public interface ParkRepo extends Neo4jRepository<Park, UUID> {

    List<Park> findParkByParkName(String parkName);
    Park findParkById(UUID id);

    @Query("match (n:Park) return n")
    List<Park> getAllParks();

    @Query("MATCH (p:Park {id: $parkId})-[*0..]-(x) DETACH DELETE x")
    void deleteEntirePark(@Param("parkId") UUID parkId);

    @Query("match (n:Park)-[*0..1]->(p) return p")
    List<Park> getAllParksAndSites();

}
