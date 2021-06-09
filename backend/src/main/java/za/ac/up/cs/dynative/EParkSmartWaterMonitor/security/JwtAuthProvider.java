package za.ac.up.cs.dynative.EParkSmartWaterMonitor.security;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtAuthProvider extends AbstractUserDetailsAuthenticationProvider {

    private final PrincipalUserFactory userFactory;

    public JwtAuthProvider(@Qualifier("PrincipalUserFactory") PrincipalUserFactory userFactory) {
        this.userFactory = userFactory;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(final String uuid, final UsernamePasswordAuthenticationToken authentication) {
        try {
            return userFactory.createUser(uuid);
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}