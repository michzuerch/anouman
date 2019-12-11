package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "KontoHauptgruppe")
public class KontoHauptgruppe extends AbstractEntity {
    @NotNull
    private String kontonummer;

    @NotNull
    private String description;

    @ManyToOne
    private Kontoklasse kontoklasse;

    @OneToMany(mappedBy = "kontoHauptgruppe", cascade = CascadeType.ALL)
    private List<Kontogruppe> kontogruppes;

    private KontoHauptgruppe(Builder builder) {
        setKontonummer(builder.kontonummer);
        setDescription(builder.description);
        setKontoklasse(builder.kontoklasse);
        setKontogruppes(builder.kontogruppes);
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

    public Kontoklasse getKontoklasse() {
        return kontoklasse;
    }

    public void setKontoklasse(Kontoklasse kontoklasse) {
        this.kontoklasse = kontoklasse;
    }

    public List<Kontogruppe> getKontogruppes() {
        return kontogruppes;
    }

    public void setKontogruppes(List<Kontogruppe> kontogruppes) {
        this.kontogruppes = kontogruppes;
    }

    public static final class Builder {
        private String kontonummer;
        private String description;
        private Kontoklasse kontoklasse;
        private List<Kontogruppe> kontogruppes;

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

        public Builder kontoklasse(Kontoklasse val) {
            kontoklasse = val;
            return this;
        }

        public Builder kontogruppes(List<Kontogruppe> val) {
            kontogruppes = val;
            return this;
        }

        public KontoHauptgruppe build() {
            return new KontoHauptgruppe(this);
        }
    }
}
