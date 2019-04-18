package com.github.nenomm.v13demo.domain.user;

import org.springframework.security.core.GrantedAuthority;

public enum UserPrivilege implements GrantedAuthority {
    ADMIN,
    USER;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
