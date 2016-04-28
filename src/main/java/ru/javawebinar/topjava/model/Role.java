package ru.javawebinar.topjava.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public enum Role implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
