package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "TemplateKontogruppe")
public class TemplateKontogruppe extends AbstractEntity {
    @ManyToOne
    private Address address;

    private TemplateKontogruppe(Builder builder) {
        setAddress(builder.address);
    }

    public TemplateKontogruppe() {
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

        public TemplateKontogruppe build() {
            return new TemplateKontogruppe(this);
        }
    }
}
