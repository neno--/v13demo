package com.github.nenomm.v13demo.domain.user;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Password {

    @Column(nullable = false, unique = false)
    String password;

    private Password(String password) {
        this.password = password;
    }

    // enforcing immutability
    public static Password getNew(String password) {
        return new Password(password);
    }

    // for hibernate
    private Password() {
    }

    @Override
    public boolean equals(final Object p_object) {
        if (this == p_object) return true;
        if (p_object == null || getClass() != p_object.getClass()) return false;

        Password that = (Password) p_object;

        return Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }
}
