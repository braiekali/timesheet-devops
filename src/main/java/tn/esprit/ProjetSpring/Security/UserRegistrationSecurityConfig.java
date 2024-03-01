package tn.esprit.ProjetSpring.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tn.esprit.ProjetSpring.jwt.JWTAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class UserRegistrationSecurityConfig {

    private static final String[] SECURED_URLs = {



    };

    private static final String[] UN_SECURED_URLs = {
            "/register/**",
            "/authenticate/**",
            "/upload-directory/",
            "/upload-directory",
            "/upload-directory/**",
            "/login/**",
            "/admin/**",
            "/user/**",
            "/reservation/**",
            "/chambre/**",
            "/users/**",
            "/register/session/**",
            "/register/session",
            "/users",
            "/bloc/**",
            "/blocs/**",
            "/bloc/blocs/**",
            "/chambres/**",
            "/register",
            "/foyer/**",
            "/foyers/**",
            "/actualite/**",
            "/restaurant/**",
            "/plat/**",
            "/universite/**",
            "/specialites/**",
            "/matieres/**",



            "/**",
            "**",
    };

    @Autowired
    private JWTAuthenticationFilter authenticationFilter;

    @Autowired
    private UserRegistrationDetailsService userDetailsService;



    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        var authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(UN_SECURED_URLs).permitAll().and()
                .authorizeHttpRequests().requestMatchers(SECURED_URLs)
                .hasAuthority("").anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

}