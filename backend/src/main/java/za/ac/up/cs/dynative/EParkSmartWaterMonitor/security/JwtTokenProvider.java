package za.ac.up.cs.dynative.EParkSmartWaterMonitor.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider
{
    private String jwtSecret ="tempSecret" ;
    private String jwtTokenPrefix= "tempTokenPrefix";
    private String jwtHeaderString= "tempHeaderString";
    private Long jwtExpirationInMs= Long.valueOf(86400000);

    public String generateToken(Authentication authentication)
    {
        String authorities= authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining());
        return Jwts.builder().setSubject(authentication.getName())
                .claim("roles",authorities)
                .setExpiration(new Date(System.currentTimeMillis()+jwtExpirationInMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }


    private String resolveToken(HttpServletRequest request)
    {
        String bearerToken = request.getHeader(jwtHeaderString);
        if (bearerToken!=null&&bearerToken.startsWith(jwtTokenPrefix))
        {
            return bearerToken.substring(7,bearerToken.length());

        }
        else return null;
    }

}
