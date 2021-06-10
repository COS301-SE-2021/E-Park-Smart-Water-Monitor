package za.ac.up.cs.dynative.EParkSmartWaterMonitor.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class JwtAuthFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper mapper = new ObjectMapper();

    private String BEARER = "Bearer ";

    private String SECRET = "secret";

    JwtAuthFilter(final RequestMatcher requiresAuth) {
        super(requiresAuth);
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        String param = ofNullable(request.getHeader(AUTHORIZATION)).orElse(request.getParameter("t"));

        if (param == null || !param.contains(BEARER)) {

            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("message", "Missing or invalid authorization header!");
            errorDetails.put("code", "Missing header");

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            mapper.writeValue(response.getWriter(), errorDetails);
            return null;
        }

        String token = param.substring(BEARER.length()).trim();

        try {

            String userID = String.valueOf(parseJwt(token).getBody().get("UUID"));
            final Authentication auth = new UsernamePasswordAuthenticationToken(userID, userID);
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(getAuthenticationManager().authenticate(auth));
            return getAuthenticationManager().authenticate(auth);

        }
        catch (SignatureException | ExpiredJwtException | AuthenticationException sigExp) {
            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("message", sigExp.getMessage());
            errorDetails.put("code", "Jwt token error.");

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            mapper.writeValue(response.getWriter(), errorDetails);
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain, final Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }

    private Jws<Claims> parseJwt(String jwtString) throws SignatureException, ExpiredJwtException {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwtString);
    }
}
