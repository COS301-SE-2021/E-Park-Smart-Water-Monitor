package za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.models.User;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;

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

    @Query("MATCH (p:Park{id: $parkId})"+
            "    CREATE (users:User{ role: $role, parkName: $parkName , idNumber: $idNumber, cellNumber: $cellNumber,resetPasswordExpiration:\"\", password:\"\" , surname: $surname, name: $name, activationCode: \"\", id: $id , email: $email , username: $username   })-[:WORKS_FOR]->(p)\n")
    void addUser(UUID id,long idNumber, String email, String name, String surname, String password, String username, String role, UUID parkId , String parkName, String cellNumber);

    //void deleteUserById
}
