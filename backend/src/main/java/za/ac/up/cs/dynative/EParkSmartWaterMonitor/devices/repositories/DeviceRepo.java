package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceDevice;

import java.util.UUID;

@Repository("DeviceRepo")
public interface DeviceRepo extends Neo4jRepository< WaterSourceDevice, UUID>
{
//    Mono<WaterSourceDevice> findByName(String title);
}