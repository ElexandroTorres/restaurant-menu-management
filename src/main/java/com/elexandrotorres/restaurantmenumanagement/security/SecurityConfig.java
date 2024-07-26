package com.elexandrotorres.restaurantmenumanagement.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final SecurityFilter securityFilter;

    @Autowired
    public SecurityConfig(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                                .requestMatchers(HttpMethod.POST, "/auth").permitAll()
                                .requestMatchers(HttpMethod.GET, "/restaurants/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/restaurants").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/restaurants/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/restaurants").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/tablets/**").hasRole("MANAGER")
                                .requestMatchers(HttpMethod.POST, "/tablets").hasRole("MANAGER")
                                .requestMatchers(HttpMethod.PUT, "/tablets/**").hasRole("MANAGER")
                                .requestMatchers(HttpMethod.DELETE, "/tablets").hasRole("MANAGER")
                                .requestMatchers(HttpMethod.GET, "/menus/**").hasRole("TABLET")
                                .requestMatchers(HttpMethod.POST, "/menus").hasRole("MANAGER")
                                .requestMatchers(HttpMethod.PUT, "/menus/**").hasRole("MANAGER")
                                .requestMatchers(HttpMethod.DELETE, "/menus").hasRole("MANAGER")
                                .requestMatchers("/swagger-ui/**",
                                        "/swagger-resources/*",
                                        "/v3/api-docs/**")
                                .permitAll()
                                .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandling -> {
                    exceptionHandling
                            .authenticationEntryPoint((request, response, authException) ->
                                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))
                            .accessDeniedHandler((request, response, accessDeniedException) ->
                                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied"));
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


}
