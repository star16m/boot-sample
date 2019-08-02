package star16m.bootsample.web.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import star16m.bootsample.web.config.security.jwt.JWTAuthenticationFilter;
import star16m.bootsample.web.config.security.jwt.JWTAuthenticationManager;
import star16m.bootsample.web.config.security.jwt.JWTAuthorizationFilter;
import star16m.bootsample.web.config.security.resource.UserDetailServiceImpl;
import star16m.bootsample.web.config.security.resource.UserRepository;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_URL = "/user/login";
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTAuthenticationManager jwtAuthenticationManager;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                "/swagger/**",
                "/resources/**",
                "/images/**",
                "/swagger-resources/**"
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(LOGIN_URL).permitAll()
                .antMatchers("/api/rest/v1/action").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().logout()
                .and().csrf().disable()
                .exceptionHandling().accessDeniedHandler(this.jwtAuthenticationManager)
                .and()
                .addFilterAt(jwtAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtAuthorizationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Autowired
    public void configureAuth(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(this.userDetailService)
                .passwordEncoder(passwordEncoder());
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token", this.jwtAuthenticationManager.getHeaderKey()));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private JWTAuthenticationFilter jwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(this.jwtAuthenticationManager, this.userRepository);
        jwtAuthenticationFilter.setUsernameParameter("userId");
        jwtAuthenticationFilter.setPasswordParameter("password");
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        jwtAuthenticationFilter.setRequiresAuthenticationRequestMatcher(
                new AntPathRequestMatcher(LOGIN_URL, HttpMethod.POST.name())
        );
        jwtAuthenticationFilter.setAuthenticationFailureHandler(this.jwtAuthenticationManager);
        jwtAuthenticationFilter.setAuthenticationSuccessHandler(this.jwtAuthenticationManager);
        return jwtAuthenticationFilter;
    }
    private JWTAuthorizationFilter jwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        JWTAuthorizationFilter jwtAuthorizationFilter = new JWTAuthorizationFilter(authenticationManager, this.jwtAuthenticationManager);
        return jwtAuthorizationFilter;
    }
}
