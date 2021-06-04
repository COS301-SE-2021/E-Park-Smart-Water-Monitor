package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceDevice;

import java.util.Collection;
import java.util.UUID;

@Repository("DeviceRepo")
public interface DeviceRepo extends Neo4jRepository< WaterSourceDevice, UUID> {

    Collection<WaterSourceDevice> findWaterSourceDeviceByDeviceName(String deviceName);
    @Query("MATCH (:Park{parkName: $parkname})-->(watersite)-->(w) RETURN w")
    Collection<WaterSourceDevice> getAllParkDevices(@Param("parkname") String parkName);

    @Query("MATCH (:Park{id: $id})-->(watersite)-->(w)-->(d)RETURN w,d")
    Collection<WaterSourceDevice> getAllParkDevicesById(@Param("id") UUID id);

}