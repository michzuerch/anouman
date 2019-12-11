package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "Kontogruppe")
public class Kontogruppe extends AbstractEntity {
    @NotNull
    private String kontonummer;

    @NotNull
    private String description;

    @ManyToOne
    private KontoHauptgruppe kontoHauptgruppe;

    @OneToMany(mappedBy = "kontogruppe", cascade = CascadeType.ALL)
    private List<Konto> kontos;

    private Kontogruppe(Builder builder) {
        setKontonummer(builder.kontonummer);
        setDescription(builder.description);
        setKontoHauptgruppe(builder.kontoHauptgruppe);
        setKontos(builder.kontos);
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

    public KontoHauptgruppe getKontoHauptgruppe() {
        return kontoHauptgruppe;
    }

    public void setKontoHauptgruppe(KontoHauptgruppe kontoHauptgruppe) {
        this.kontoHauptgruppe = kontoHauptgruppe;
    }

    public List<Konto> getKontos() {
        return kontos;
    }

    public void setKontos(List<Konto> kontos) {
        this.kontos = kontos;
    }

    public static final class Builder {
        private String kontonummer;
        private String description;
        private KontoHauptgruppe kontoHauptgruppe;
        private List<Konto> kontos;

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

        public Builder kontoHauptgruppe(KontoHauptgruppe val) {
            kontoHauptgruppe = val;
            return this;
        }

        public Builder kontos(List<Konto> val) {
            kontos = val;
            return this;
        }

        public Kontogruppe build() {
            return new Kontogruppe(this);
        }
    }
}
