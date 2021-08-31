package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Device;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Measurement;

import java.util.List;
import java.util.UUID;

@Repository("SourceDataRepo")
public interface MeasurementRepo extends Neo4jRepository<Measurement, UUID> {
    @Query("MATCH (:Device{deviceName: $id})-->(m:Measurement) DETACH DELETE m")
    void removeOldMeasurementSet(@Param("id") String id);
}
