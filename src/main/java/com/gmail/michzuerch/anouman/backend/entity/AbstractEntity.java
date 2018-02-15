package com.gmail.michzuerch.anouman.backend.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Matti Tahvonen
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable, Cloneable {

    @Version
    public int version;

    @Id
    @GeneratedValue
    protected Long id;

    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (this.id == null) {
            return false;
        }

        if (obj instanceof AbstractEntity && obj.getClass().equals(getClass())) {
            return this.id.equals(((AbstractEntity) obj).id);
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.id);
        return hash;
    }

}
