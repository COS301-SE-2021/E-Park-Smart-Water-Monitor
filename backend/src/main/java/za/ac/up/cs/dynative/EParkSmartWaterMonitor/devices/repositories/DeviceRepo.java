package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Device;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Measurement;

import java.util.*;

@Repository("WaterSourceDeviceRepo")
public interface DeviceRepo extends Neo4jRepository<Device, UUID> {

    List<Device> findWaterSourceDeviceByDeviceName(String deviceName);
    List<Device> findDeviceByDeviceName(String deviceName);

    @Query("MATCH (:Park{id: $id})-->(watersite)-->(d:Device) RETURN d")
    List<Device> getAllParkDevices(@Param("id") UUID id);

    @Query("MATCH (:Park{id: $id})-->(watersite)-->(w)-->(d)RETURN w,d")
    List<Device> getAllParkDevicesById(@Param("id") UUID id);

    @Query("MATCH (n:Device {deviceId:$id})-[*0..]->(deviceToDelete) detach delete deviceToDelete")
    void deleteDevice(@Param("id") UUID id);


    @Query(" MATCH (d:Device{deviceId:$deviceId})"+ "SET d.deviceName=$deviceName,d.deviceModel=$deviceModel")
    void editDevice(UUID deviceId,String deviceName,String deviceModel);

    @Query("MATCH (waterSite:WaterSite {id:$waterSiteId})\n" +
            "CREATE (device:Device\n" +
            "   {\n" +
            "        deviceId:$deviceId,\n" +
            "        deviceModel:$deviceModel,\n" +
            "        deviceName:$deviceName,\n" +
            "        deviceType:$deviceType,\n" +
            "        measurementSet:$latestMeasurement" +
            "   })<-[:WATER_MONITORED_BY]-(waterSite),\n" +
            "(deviceData:DeviceData\n" +
            "   {\n" +
            "        battery:$battery,\n" +
            "        deviceDataId:$deviceDataId,\n" +
            "        deviceStatus:$deviceStatus,\n" +
            "        lastSeen:$lastSeen,\n" +
            "        latitude:$latitude,\n" +
            "        longitude:$longitude,\n" +
            "        upTime:$upTime,\n" +
            "        lifeTime:$lifeTime" +
            "   })<-[:DEVICE_DATA]-(device),\n" +
            "(config1:sensorConfiguration\n" +
            "   {\n" +
            "        configId:$configId1,\n" +
            "        lowerLimit:$lowerLimit1,\n" +
            "        settingType:$settingType1,\n" +
            "        upperLimit:$upperLimit1, " +
            "        value:$value1\n" +
            "   })<-[:DEVICE_CONFIGURATION]-(deviceData),\n" +
            "(config2:sensorConfiguration\n" +
            "   {\n" +
            "        configId:$configId2,\n" +
            "        lowerLimit:$lowerLimit2,\n" +
            "        settingType:$settingType2,\n" +
            "        upperLimit:$upperLimit2, " +
            "        value:$value2\n" +
            "   })<-[:DEVICE_CONFIGURATION]-(deviceData),\n" +
            "(config3:sensorConfiguration\n" +
            "   {\n" +
            "        configId:$configId3,\n" +
            "        lowerLimit:$lowerLimit3,\n" +
            "        settingType:$settingType3,\n" +
            "        upperLimit:$upperLimit3, " +
            "        value:$value3\n" +
            "   })<-[:DEVICE_CONFIGURATION]-(deviceData),\n" +
            "(config4:sensorConfiguration\n" +
            "   {\n" +
            "        configId:$configId4,\n" +
            "        lowerLimit:$lowerLimit4,\n" +
            "        settingType:$settingType4,\n" +
            "        upperLimit:$upperLimit4, " +
            "        value:$value4\n" +
            "})<-[:DEVICE_CONFIGURATION]-(deviceData);")
    void addDevice(@Param("waterSiteId") UUID waterSiteId,
                   @Param("deviceId") UUID deviceId,
                   @Param("deviceModel") String deviceModel,
                   @Param("deviceName") String deviceName,
                   @Param("deviceType") String deviceType,
                   @Param("latestMeasurement") Set<Measurement> latestMeasurement,
                   @Param("deviceDataId") UUID deviceDataId,
                   @Param("battery") double battery,
                   @Param("deviceStatus") String status,
                   @Param("lastSeen") Date lastSeen,
                   @Param("latitude") double latitude,
                   @Param("longitude") double longitude,
                   @Param("upTime") double upTime,
                   @Param("lifeTime") double lifeTime,
                   @Param("configId1") UUID configId1,
                   @Param("lowerLimit1") double lowerLimit1,
                   @Param("upperLimit1") double upperLimit1,
                   @Param("settingType1") String settingType1,
                   @Param("value1") double value1,
                   @Param("configId2") UUID configId2,
                   @Param("lowerLimit2") double lowerLimit2,
                   @Param("upperLimit2") double upperLimit2,
                   @Param("settingType2") String settingType2,
                   @Param("value2") double value2,
                   @Param("configId3") UUID configId3,
                   @Param("lowerLimit3") double lowerLimit3,
                   @Param("upperLimit3") double upperLimit3,
                   @Param("settingType3") String settingType3,
                   @Param("value3") double value3,
                   @Param("configId4") UUID configId4,
                   @Param("lowerLimit4") double lowerLimit4,
                   @Param("upperLimit4") double upperLimit4,
                   @Param("settingType4") String settingType4,
                   @Param("value4") double value4);
}