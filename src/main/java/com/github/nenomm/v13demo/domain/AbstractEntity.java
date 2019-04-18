package com.github.nenomm.v13demo.domain;

import org.springframework.data.domain.Persistable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.Transient;

@MappedSuperclass
public class AbstractEntity implements Persistable<Long> {

    @Id
    @GeneratedValue
    private Long id;

    @Transient
    private boolean isNew = true;

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || !(obj.getClass().equals(this.getClass()))) {
            return false;
        }

        AbstractEntity that = (AbstractEntity) obj;


        return id != null &&
                id.equals(that.getId());
    }

    // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PostPersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }
}
