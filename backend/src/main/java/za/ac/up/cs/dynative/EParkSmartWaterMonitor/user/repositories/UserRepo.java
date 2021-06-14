package za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.models.User;

import java.util.List;
import java.util.UUID;

@Repository("UserRepo")
public interface UserRepo extends Neo4jRepository<User, UUID> {
    List<User> findUserByUsername(String username);
    List<User> findUserByEmail(String email);
    User findUserById(UUID uuid);
    List<User> findUserByIdNumber(String idNumber);
}
