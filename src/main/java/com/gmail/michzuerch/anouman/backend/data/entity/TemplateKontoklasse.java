package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "TemplateKontoklasse")
public class TemplateKontoklasse extends AbstractEntity {
    @ManyToOne
    private Address address;

    private TemplateKontoklasse(Builder builder) {
        setAddress(builder.address);
    }

    public TemplateKontoklasse() {
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

        public TemplateKontoklasse build() {
            return new TemplateKontoklasse(this);
        }
    }
}
