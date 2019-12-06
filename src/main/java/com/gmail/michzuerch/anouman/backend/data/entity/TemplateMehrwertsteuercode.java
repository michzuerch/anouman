package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "TemplateMehrwertsteuercode")
public class TemplateMehrwertsteuercode extends AbstractEntity {
    @ManyToOne
    private Address address;

    private TemplateMehrwertsteuercode(Builder builder) {
        setAddress(builder.address);
    }

    public TemplateMehrwertsteuercode() {
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

        public TemplateMehrwertsteuercode build() {
            return new TemplateMehrwertsteuercode(this);
        }
    }
}
