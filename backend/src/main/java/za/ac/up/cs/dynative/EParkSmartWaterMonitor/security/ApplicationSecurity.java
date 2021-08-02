package za.ac.up.cs.dynative.EParkSmartWaterMonitor.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.repositories.UserRepo;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {
/*
    private final RequestMatcher USER_URLS = new OrRequestMatcher(
            new AntPathRequestMatcher("/api/user/login"),
            new AntPathRequestMatcher("/api/**"),
            new AntPathRequestMatcher("/api/user/createUser")
);*/

   // private final RequestMatcher PROTECTED_URLS = new NegatedRequestMatcher(USER_URLS);

    private final JwtAuthProvider provider;

    public ApplicationSecurity(final JwtAuthProvider provider) {
        super();
        this.provider = provider;
    }

    @Autowired
    private UserRepo repo;

    @Autowired
    private UserDetailService userDetailsService;

   /* @Override
    public void configure(WebSecurity web) {
        web.ignoring().requestMatchers(USER_URLS);
    }*/


/*    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.FORBIDDEN), PROTECTED_URLS)
                .and()
                .authenticationProvider(provider)
                .addFilterBefore(restAuthenticationFilter(), AnonymousAuthenticationFilter.class);
    }*/

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailService(repo);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public WebMvcConfigurer corsConfigurer()
    {
        return new WebMvcConfigurer() {
        @Override
            public void addCorsMappings(CorsRegistry registry){
            registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
        }
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").hasAnyRole("ADMIN", "FIELD_ENGINEER", "RANGER")
                .antMatchers("/api/devices/addDevice").hasAnyRole("ADMIN", "FIELD_ENGINEER")
                .antMatchers("/api/devices/editDevice").hasAnyRole("ADMIN", "FIELD_ENGINEER")
                .antMatchers("addInspection/addInspection").hasAnyRole("ADMIN", "FIELD_ENGINEER")
                .antMatchers("/api/park/addPark").hasAnyRole("ADMIN")
                .antMatchers("/api/park/editPark").hasAnyRole("ADMIN")
                .antMatchers("/api/user/createUser").hasAnyRole("ADMIN")
                .antMatchers("/api/user/deleteUser").hasAnyRole("ADMIN")
                .antMatchers("/api/user/editUser").hasAnyRole("ADMIN")
                .antMatchers("/api/sites/addSite").hasAnyRole("ADMIN")
                .antMatchers("/api/user/login").permitAll()
                .anyRequest().fullyAuthenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }

    /*@Bean
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
    }*/
}