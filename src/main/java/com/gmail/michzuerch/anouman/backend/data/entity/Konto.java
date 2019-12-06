package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Konto")
public class Konto extends AbstractEntity {
    @NotBlank
    private String description;

    @NotNull
    private String kontonummer;

    @ManyToOne
    private Address address;

    private String comment;

    private Double anfangsbestand;

    @ManyToOne
    private Kontogruppe kontogruppe;

    @OneToMany(mappedBy = "kontoSoll", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Unterbuchung> soll = new ArrayList<>();

    @OneToMany(mappedBy = "kontoHaben", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Unterbuchung> haben = new ArrayList<>();

    private Konto(Builder builder) {
        setDescription(builder.description);
        setKontonummer(builder.kontonummer);
        setAddress(builder.address);
        setComment(builder.comment);
        setAnfangsbestand(builder.anfangsbestand);
        setKontogruppe(builder.kontogruppe);
        setSoll(builder.soll);
        setHaben(builder.haben);
    }

    public Konto() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKontonummer() {
        return kontonummer;
    }

    public void setKontonummer(String kontonummer) {
        this.kontonummer = kontonummer;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getAnfangsbestand() {
        return anfangsbestand;
    }

    public void setAnfangsbestand(Double anfangsbestand) {
        this.anfangsbestand = anfangsbestand;
    }

    public Kontogruppe getKontogruppe() {
        return kontogruppe;
    }

    public void setKontogruppe(Kontogruppe kontogruppe) {
        this.kontogruppe = kontogruppe;
    }

    public List<Unterbuchung> getSoll() {
        return soll;
    }

    public void setSoll(List<Unterbuchung> soll) {
        this.soll = soll;
    }

    public List<Unterbuchung> getHaben() {
        return haben;
    }

    public void setHaben(List<Unterbuchung> haben) {
        this.haben = haben;
    }

    public static final class Builder {
        private @NotBlank String description;
        private @NotNull String kontonummer;
        private Address address;
        private String comment;
        private Double anfangsbestand;
        private Kontogruppe kontogruppe;
        private List<Unterbuchung> soll;
        private List<Unterbuchung> haben;

        public Builder() {
        }

        public Builder description(@NotBlank String val) {
            description = val;
            return this;
        }

        public Builder kontonummer(@NotNull String val) {
            kontonummer = val;
            return this;
        }

        public Builder address(Address val) {
            address = val;
            return this;
        }

        public Builder comment(String val) {
            comment = val;
            return this;
        }

        public Builder anfangsbestand(Double val) {
            anfangsbestand = val;
            return this;
        }

        public Builder kontogruppe(Kontogruppe val) {
            kontogruppe = val;
            return this;
        }

        public Builder soll(List<Unterbuchung> val) {
            soll = val;
            return this;
        }

        public Builder haben(List<Unterbuchung> val) {
            haben = val;
            return this;
        }

        public Konto build() {
            return new Konto(this);
        }
    }
}
