package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "BookEntry")
public class BookEntry extends AbstractEntity {
    @NotEmpty
    private String belegnummer;

    private LocalDate buchungsdatum;

    @ManyToOne
    private Bookkeeping bookkeeping;

    @OneToMany(mappedBy = "bookEnry", cascade = CascadeType.ALL)
    private List<Unterbuchung> unterbuchungs;

    private BookEntry(Builder builder) {
        setBelegnummer(builder.belegnummer);
        setBuchungsdatum(builder.buchungsdatum);
        setBookkeeping(builder.bookkeeping);
        setUnterbuchungs(builder.unterbuchungs);
    }

    public String getBelegnummer() {
        return belegnummer;
    }

    public void setBelegnummer(String belegnummer) {
        this.belegnummer = belegnummer;
    }

    public LocalDate getBuchungsdatum() {
        return buchungsdatum;
    }

    public void setBuchungsdatum(LocalDate buchungsdatum) {
        this.buchungsdatum = buchungsdatum;
    }

    public Bookkeeping getBookkeeping() {
        return bookkeeping;
    }

    public void setBookkeeping(Bookkeeping bookkeeping) {
        this.bookkeeping = bookkeeping;
    }

    public List<Unterbuchung> getUnterbuchungs() {
        return unterbuchungs;
    }

    public void setUnterbuchungs(List<Unterbuchung> unterbuchungs) {
        this.unterbuchungs = unterbuchungs;
    }

    public static final class Builder {
        private @NotEmpty String belegnummer;
        private LocalDate buchungsdatum;
        private Bookkeeping bookkeeping;
        private List<Unterbuchung> unterbuchungs;

        public Builder() {
        }

        public Builder belegnummer(@NotEmpty String val) {
            belegnummer = val;
            return this;
        }

        public Builder buchungsdatum(LocalDate val) {
            buchungsdatum = val;
            return this;
        }

        public Builder bookkeeping(Bookkeeping val) {
            bookkeeping = val;
            return this;
        }

        public Builder unterbuchungs(List<Unterbuchung> val) {
            unterbuchungs = val;
            return this;
        }

        public BookEntry build() {
            return new BookEntry(this);
        }
    }
}
