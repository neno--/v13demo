package com.github.nenomm.v13demo.domain.user;

import com.github.nenomm.v13demo.domain.AbstractEntity;
import org.springframework.util.Assert;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;

@Entity
public class User extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String username;

    @Embedded
    private Password password;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = UserPrivilege.class)
    @Enumerated(EnumType.STRING)
    private Set<UserPrivilege> privileges = new HashSet<>();

    @Column(nullable = false, unique = false)
    private OffsetDateTime registeredAt;

    private User() {
        // for hibernate
    }

    public User(final String p_username, final Password p_password) {
        Assert.notNull(p_username, "email must not be null");
        Assert.notNull(p_password, "password must not be null");

        username = p_username;
        password = p_password;
        registeredAt = OffsetDateTime.now();
    }

    public void addPrivilege(final UserPrivilege p_privilege) {
        Assert.notNull(p_privilege, "privilege must not be null");
        privileges.add(p_privilege);
    }

    public Password getPassword() {
        return password;
    }

    public Set<UserPrivilege> getPrivileges() {
        return Collections.unmodifiableSet(privileges);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", registeredAt=" + registeredAt +
                "} " + super.toString();
    }
}
