package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "TemplateKontohauptgruppe")
public class TemplateKontoHauptgruppe extends AbstractEntity {
    @ManyToOne
    private Address address;

    private TemplateKontoHauptgruppe(Builder builder) {
        setAddress(builder.address);
    }

    public TemplateKontoHauptgruppe() {
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

        public TemplateKontoHauptgruppe build() {
            return new TemplateKontoHauptgruppe(this);
        }
    }
}
