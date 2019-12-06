package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "Kontogruppe")
public class Kontogruppe extends AbstractEntity {
    @ManyToOne
    private Address address;

    private Kontogruppe(Builder builder) {
        setAddress(builder.address);
    }

    public Kontogruppe() {
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

        public Kontogruppe build() {
            return new Kontogruppe(this);
        }
    }
}
