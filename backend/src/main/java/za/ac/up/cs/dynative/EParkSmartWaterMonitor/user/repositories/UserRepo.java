package za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository("UserRepo")
public interface UserRepo extends Neo4jRepository<User, UUID> {
    List<User> findUserByUsername(String username);

    List<User> findUserByEmail(String email);

    User findUserById(UUID uuid);

    List<User> findUserByIdNumber(String idNumber);

    @Query("match (u:User) return u")
    List<User> getAllUsers();

    @Query("match (u:User{id: $id}) return u")
    User findSpecificUser(@Param("id") UUID id);


    @Query("    MATCH (users:User)-[:WORKS_FOR]->(x:Park) where (x)-[:HAS_WATER_SITE]->(:WaterSite)-[]->(:Device{deviceName: $dName}) return users")
    List<User> findUsersWorkingAtDevicePark(@Param("dName") String dName);

    //void deleteUserById
}
