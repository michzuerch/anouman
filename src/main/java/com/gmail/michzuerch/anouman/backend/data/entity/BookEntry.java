package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
/* @todo Unterbuchung?? */
@Entity(name = "BookEntry")
public class BookEntry extends AbstractEntity {
    @ManyToOne
    private Bookkeeping bookkeeping;

    private BookEntry(Builder builder) {
        setBookkeeping(builder.bookkeeping);
    }

    public BookEntry() {
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

        public BookEntry build() {
            return new BookEntry(this);
        }
    }
}
