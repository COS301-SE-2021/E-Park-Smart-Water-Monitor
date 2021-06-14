package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.InfrastructureDevice;

import java.util.List;
import java.util.UUID;

@Repository("InfrastructureDeviceRepo")
public interface InfrastructureDeviceRepo extends Neo4jRepository< InfrastructureDevice, UUID> {
    List<InfrastructureDevice> findInfrastructureDeviceByDeviceName(String deviceName);
}
