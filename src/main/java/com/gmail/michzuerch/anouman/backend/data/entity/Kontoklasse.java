package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "Kontoklasse")
public class Kontoklasse extends AbstractEntity {
    private String kontonummer;

    private String description;

    @ManyToOne
    private Bookkeeping bookkeeping;

    @OneToMany(mappedBy = "kontoklasse", cascade = CascadeType.ALL)
    private List<KontoHauptgruppe> kontoHauptgruppes;

    private Kontoklasse(Builder builder) {
        setKontonummer(builder.kontonummer);
        setDescription(builder.description);
        setBookkeeping(builder.bookkeeping);
        setKontoHauptgruppes(builder.kontoHauptgruppes);
    }

    public String getKontonummer() {
        return kontonummer;
    }

    public void setKontonummer(String kontonummer) {
        this.kontonummer = kontonummer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bookkeeping getBookkeeping() {
        return bookkeeping;
    }

    public void setBookkeeping(Bookkeeping bookkeeping) {
        this.bookkeeping = bookkeeping;
    }

    public List<KontoHauptgruppe> getKontoHauptgruppes() {
        return kontoHauptgruppes;
    }

    public void setKontoHauptgruppes(List<KontoHauptgruppe> kontoHauptgruppes) {
        this.kontoHauptgruppes = kontoHauptgruppes;
    }

    public static final class Builder {
        private String kontonummer;
        private String description;
        private Bookkeeping bookkeeping;
        private List<KontoHauptgruppe> kontoHauptgruppes;

        public Builder() {
        }

        public Builder kontonummer(String val) {
            kontonummer = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder bookkeeping(Bookkeeping val) {
            bookkeeping = val;
            return this;
        }

        public Builder kontoHauptgruppes(List<KontoHauptgruppe> val) {
            kontoHauptgruppes = val;
            return this;
        }

        public Kontoklasse build() {
            return new Kontoklasse(this);
        }
    }
}
