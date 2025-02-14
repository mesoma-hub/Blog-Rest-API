package com.blog.config;

import com.blog.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                (authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers(HttpMethod.GET, "/api/**")
                                .permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN") // Allow POST for users with the ADMIN role
                                .requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN") // Allow PUT for users with the ADMIN role
                                .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN") // Allow Delete for users with  ADMIN role
                                .anyRequest()
                                .authenticated()
                                ))
                .csrf(AbstractHttpConfigurer::disable) // disable for non browser clients to allow for API testing
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    /*@Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("mesoma")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        UserDetails admin =
                User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
*/
    /*This will be used to encode the password*/
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*Database authentication*/
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
