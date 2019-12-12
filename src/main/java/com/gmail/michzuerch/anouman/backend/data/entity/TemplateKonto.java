package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity(name = "TemplateKonto")
public class TemplateKonto extends AbstractEntity {
    @NotNull
    private String kontonummer;

    @NotNull
    private String description;

    @ManyToOne
    private TemplateKontogruppe templateKontogruppe;

    private TemplateKonto(Builder builder) {
        setKontonummer(builder.kontonummer);
        setDescription(builder.description);
        setTemplateKontogruppe(builder.templateKontogruppe);
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

    public TemplateKontogruppe getTemplateKontogruppe() {
        return templateKontogruppe;
    }

    public void setTemplateKontogruppe(TemplateKontogruppe templateKontogruppe) {
        this.templateKontogruppe = templateKontogruppe;
    }

    public static final class Builder {
        private @NotNull String kontonummer;
        private @NotNull String description;
        private TemplateKontogruppe templateKontogruppe;

        public Builder() {
        }

        public Builder kontonummer(@NotNull String val) {
            kontonummer = val;
            return this;
        }

        public Builder description(@NotNull String val) {
            description = val;
            return this;
        }

        public Builder templateKontogruppe(TemplateKontogruppe val) {
            templateKontogruppe = val;
            return this;
        }

        public TemplateKonto build() {
            return new TemplateKonto(this);
        }
    }
}
