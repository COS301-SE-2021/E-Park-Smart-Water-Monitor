package za.ac.up.cs.dynative.EParkSmartWaterMonitor.security.former;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.security.UserDetails;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.models.User;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.repositories.UserRepo;

import java.util.UUID;
@Service("PrincipalUserFactory")
public class PrincipalUserFactory {

    @Autowired
    private UserRepo userRepo;

    public UserDetails createUser(String uuid) throws UsernameNotFoundException {

        User theUser = userRepo.findUserById(UUID.fromString(uuid));

        if (theUser != null) {

            UserDetails user = new UserDetails(
                    theUser.getName(),
                    theUser.getSurname(),
                    theUser.getUsername(),
                    theUser.getEmail(),
                    "NONE");

            String role;
            switch (theUser.getRole()) {
                case "ADMIN":
                    role = "ADMIN";
                    user.setAuthorities(new SimpleGrantedAuthority(role));
                    break;
                case "FIELD_ENGINEER":
                    role = "FIELD_ENGINEER";
                    user.setAuthorities(new SimpleGrantedAuthority(role));
                    break;
                case "RANGER":
                    role = "RANGER";
                    user.setAuthorities(new SimpleGrantedAuthority(role));
                    break;
                default:
                    role = "USER";
                    user.setAuthorities(new SimpleGrantedAuthority(role));
                    break;
            }
            return user;
        }

        throw new UsernameNotFoundException("User with the id: " + uuid + " was not found");
    }
}