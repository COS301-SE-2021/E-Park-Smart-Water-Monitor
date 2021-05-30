package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repos;
import reactor.core.publisher.Mono;

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.WaterSourceDevice;

public interface DeviceRepo extends ReactiveNeo4jRepository< WaterSourceDevice, String> {
//    Mono<WaterSourceDevice> findByName(String title);
}