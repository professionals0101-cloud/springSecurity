package com.vipul.springSecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final String SECRET = "jhashaj34jklda9adk9pa92jjb3kbhjakhsjii99adsk9wjb9bjbas9999asdjk99999asjbkjb232jbjkbads8baksjdbkbaksj";


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/test").authenticated();
                    auth.requestMatchers("/api/groups/**").authenticated();
                    auth.requestMatchers("/s3/**").authenticated();
                    auth.requestMatchers("/api/members/**").authenticated();
                    auth.requestMatchers("/auth/**").permitAll();
                    auth.requestMatchers("/actuator/env").permitAll();

                })
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())) //  enable JWT decoding
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        // Use the same secret key you used to generate JWT
        return NimbusJwtDecoder.withSecretKey(
                new javax.crypto.spec.SecretKeySpec(SECRET.getBytes(), "HmacSHA256")
        ).build();
    }
}

