package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "TemplateKontohauptgruppe")
public class TemplateKontohauptgruppe extends AbstractEntity {
    @ManyToOne
    private Address address;

    private TemplateKontohauptgruppe(Builder builder) {
        setAddress(builder.address);
    }

    public TemplateKontohauptgruppe() {
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

        public TemplateKontohauptgruppe build() {
            return new TemplateKontohauptgruppe(this);
        }
    }
}
