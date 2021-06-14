package za.ac.up.cs.dynative.EParkSmartWaterMonitor.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.models.User;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.repositories.UserRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private final UserRepo userRepo;

    public UserDetailService(@Qualifier("UserRepo") UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails userDetails;
        List<User> users = userRepo.findUserByUsername(username);
        User user = users.get(0);
        if (user != null) {
            if (user.getUsername().equals(username)) {
                switch (user.getRole()) {
                    case "ADMIN":
                        userDetails = org.springframework.security.core.userdetails
                                .User.builder().username(user.getUsername()).password(user.getPassword())
                                .authorities(new ArrayList<>())
                                .roles("ADMIN").build();
                        return userDetails;
                    case "FIELD_ENGINEER":
                        userDetails = org.springframework.security.core.userdetails
                                .User.builder().username(user.getUsername()).password(user.getPassword())
                                .authorities(new ArrayList<>())
                                .roles("FIELD_ENGINEER").build();
                        return userDetails;
                    case "RANGER":
                        userDetails = org.springframework.security.core.userdetails
                                .User.builder().username(user.getUsername()).password(user.getPassword())
                                .authorities(new ArrayList<>())
                                .roles("RANGER").build();
                        return userDetails;
                }
            }
        }
        throw new UsernameNotFoundException("User with the username: " + username + " was not found");
    }
}
