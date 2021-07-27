package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Device;

import java.util.List;
import java.util.UUID;

@Repository("WaterSourceDeviceRepo")
public interface DeviceRepo extends Neo4jRepository<Device, UUID> {

    List<Device> findWaterSourceDeviceByDeviceName(String deviceName);
    List<Device> findDeviceByDeviceName(String deviceName);

    @Query("MATCH (:Park{id: $id})-->(watersite)-->(w) RETURN w")
    List<Device> getAllParkDevices(@Param("id") UUID id);

    @Query("MATCH (:Park{id: $id})-->(watersite)-->(w)-->(d)RETURN w,d")
    List<Device> getAllParkDevicesById(@Param("id") UUID id);

    @Query("MATCH (n:Device {deviceId:$id})-[*0..]->(deviceToDelete) detach delete deviceToDelete")
    void deleteDevice(@Param("id") UUID id);

}