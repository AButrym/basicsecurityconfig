package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.security.config.Customizer.withDefaults;

@Slf4j
@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain config(HttpSecurity http) throws Exception {
        http.httpBasic(withDefaults())
                .authorizeHttpRequests(c -> c
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/products/**").authenticated()
                        .anyRequest().authenticated()
                );
        http.exceptionHandling(c -> c
                .authenticationEntryPoint((req, res, ex) -> {
                    log.warn("Authentication EntryPoint ex = {}", ex.getMessage());
                    res.sendError(SC_UNAUTHORIZED, ex.getMessage());
                })
                .accessDeniedHandler((req, res, ex) -> {
                    log.warn("Access Denied ex = {}", ex.getMessage());
                    res.sendError(SC_FORBIDDEN, ex.getMessage());
                })
        );
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }


    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager(
                User.withUsername("admin")
                        .password("{noop}admin")
                        .roles("ADMIN").build(),
                User.withUsername("user")
                        .password("{noop}user")
                        .roles("USER").build()
        );
    }
}
