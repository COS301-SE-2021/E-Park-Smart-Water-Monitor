package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceDevice;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository("WaterSourceDeviceRepo")
public interface WaterSourceDeviceRepo extends Neo4jRepository< WaterSourceDevice, UUID> {

    List<WaterSourceDevice> findWaterSourceDeviceByDeviceName(String deviceName);

    @Query("MATCH (:Park{id: $id})-->(watersite)-->(w) RETURN w")
    List<WaterSourceDevice> getAllParkDevices(@Param("id") UUID id);

    @Query("MATCH (:Park{id: $id})-->(watersite)-->(w)-->(d)RETURN w,d")
    List<WaterSourceDevice> getAllParkDevicesById(@Param("id") UUID id);

}