package za.ac.up.cs.dynative.EParkSmartWaterMonitor.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.models.User;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.repositories.UserRepo;

import java.util.UUID;
@Service("PrincipalUserFactory")
public class PrincipalUserFactory {

    @Autowired
    private UserRepo userRepo;

    public UserDetails createUser(String uuid) throws UsernameNotFoundException {
        User userResponse = userRepo.findUserById(UUID.fromString(uuid));
        UserDetails user = new UserDetails(
                userResponse.getName(),
                userResponse.getSurname(),
                userResponse.getUsername(),
                userResponse.getEmail(),
                "NONE");
//        Roles role = Roles.END_USER;
//        if (userResponse.getAdmin()) {
//            role = Roles.ADMIN;
//        }
        user.setAuthorities(new SimpleGrantedAuthority("ADMIN"));
        return user;
    }
}