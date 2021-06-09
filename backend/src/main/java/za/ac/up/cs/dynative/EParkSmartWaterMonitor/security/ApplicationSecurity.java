package za.ac.up.cs.dynative.EParkSmartWaterMonitor.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

    private final RequestMatcher USER_URLS = new OrRequestMatcher(
            new AntPathRequestMatcher("/api/user/login"),
            new AntPathRequestMatcher("/api/user/createUser")
    );

    private final RequestMatcher PROTECTED_URLS = new NegatedRequestMatcher(USER_URLS);

    private final JwtAuthProvider provider;

    public ApplicationSecurity(final JwtAuthProvider provider) {
        super();
        this.provider = provider;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().requestMatchers(USER_URLS);
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().and().
                sessionManagement().
                sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                and().
                exceptionHandling().
                defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.FORBIDDEN), PROTECTED_URLS).
                and().
                authenticationProvider(provider).
                addFilterBefore(restAuthenticationFilter(), AnonymousAuthenticationFilter.class).
                csrf().disable();
    }

    @Bean
    JwtAuthFilter restAuthenticationFilter() throws Exception {
        final JwtAuthFilter filter = new JwtAuthFilter(PROTECTED_URLS);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(successHandler());
        return filter;
    }

    @Bean
    SimpleUrlAuthenticationSuccessHandler successHandler() {
        final SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
        successHandler.setRedirectStrategy(new NoRedirectStrategy());
        return successHandler;
    }
}