package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.WaterSourceDevice;

@Repository("DeviceRepo")
public interface DeviceRepo extends Neo4jRepository< WaterSourceDevice, String>
{
//    Mono<WaterSourceDevice> findByName(String title);
}