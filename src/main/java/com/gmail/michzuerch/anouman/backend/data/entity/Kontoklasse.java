package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "Kontoklasse")
public class Kontoklasse extends AbstractEntity {
    @ManyToOne
    private Bookkeeping bookkeeping;

    private Kontoklasse(Builder builder) {
        setBookkeeping(builder.bookkeeping);
    }

    public Kontoklasse() {
    }

    public Bookkeeping getBookkeeping() {
        return bookkeeping;
    }

    public void setBookkeeping(Bookkeeping bookkeeping) {
        this.bookkeeping = bookkeeping;
    }

    public static final class Builder {
        private Bookkeeping bookkeeping;

        public Builder() {
        }

        public Builder bookkeeping(Bookkeeping val) {
            bookkeeping = val;
            return this;
        }

        public Kontoklasse build() {
            return new Kontoklasse(this);
        }
    }
}
