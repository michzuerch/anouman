package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "KontoHauptgruppe")
public class KontoHauptgruppe extends AbstractEntity {
    @ManyToOne
    private Address address;

    private KontoHauptgruppe(Builder builder) {
        setAddress(builder.address);
    }

    public KontoHauptgruppe() {
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public static final class Builder {
        private Address address;

        public Builder() {
        }

        public Builder address(Address val) {
            address = val;
            return this;
        }

        public KontoHauptgruppe build() {
            return new KontoHauptgruppe(this);
        }
    }
}
