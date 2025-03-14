package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component("auth")
public class AuthUtils {

    public boolean canRead(long id) {
        log.info("canRead id {}", id);
        log.info("authorities {}", getAuthorities());
//        logAuthorities();
        return false;
        // example of some complex logic with checking roles
        // return hasRole("ADMIN") && id % 2 == 0 ||
        //        hasRole("USER") && id % 2 == 1;
    }

    public void logAuthorities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities().forEach(authority -> log.info("authority = {}", authority.toString()));
    }

    private String getAuthorities() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(";"));
    }

    private boolean hasRole(String role) {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_" + role));
    }

}
