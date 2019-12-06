package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "TemplateBookkeeping")
public class TemplateBookkeeping extends AbstractEntity {
    @ManyToOne
    private Address address;

    private TemplateBookkeeping(Builder builder) {
        setAddress(builder.address);
    }

    public TemplateBookkeeping() {
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

        public TemplateBookkeeping build() {
            return new TemplateBookkeeping(this);
        }
    }
}
