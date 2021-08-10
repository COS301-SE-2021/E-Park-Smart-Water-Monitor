package za.ac.up.cs.dynative.EParkSmartWaterMonitor.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter
{
    private JwtTokenProvider jwtTokenProvider;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider)
    {
        super(authenticationManager);
        jwtTokenProvider=tokenProvider;

    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        Authentication authentication = jwtTokenProvider.getAuthentication(request);
       if(authentication!=null && jwtTokenProvider.validateToken(request))
       {
           SecurityContextHolder.getContext().setAuthentication(authentication);
           System.out.println("USER HAS THIS AUTH: "+ SecurityContextHolder.getContext().getAuthentication());
       }
       chain.doFilter(request,response);
    }
    }
