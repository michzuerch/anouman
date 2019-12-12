package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "TemplateKontogruppe")
public class TemplateKontogruppe extends AbstractEntity {
    @NotNull
    private String kontonummer;

    @NotNull
    private String description;

    @ManyToOne
    private TemplateKontoHauptgruppe templateKontoHauptgruppe;

    @OneToMany(mappedBy = "templateKontogruppe", cascade = CascadeType.ALL)
    private List<TemplateKonto> templateKontos;

    private TemplateKontogruppe(Builder builder) {
        setKontonummer(builder.kontonummer);
        setDescription(builder.description);
        setTemplateKontoHauptgruppe(builder.templateKontoHauptgruppe);
        setTemplateKontos(builder.templateKontos);
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

    public TemplateKontoHauptgruppe getTemplateKontoHauptgruppe() {
        return templateKontoHauptgruppe;
    }

    public void setTemplateKontoHauptgruppe(TemplateKontoHauptgruppe templateKontoHauptgruppe) {
        this.templateKontoHauptgruppe = templateKontoHauptgruppe;
    }

    public List<TemplateKonto> getTemplateKontos() {
        return templateKontos;
    }

    public void setTemplateKontos(List<TemplateKonto> templateKontos) {
        this.templateKontos = templateKontos;
    }

    public static final class Builder {
        private @NotNull String kontonummer;
        private @NotNull String description;
        private TemplateKontoHauptgruppe templateKontoHauptgruppe;
        private List<TemplateKonto> templateKontos;

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

        public Builder templateKontoHauptgruppe(TemplateKontoHauptgruppe val) {
            templateKontoHauptgruppe = val;
            return this;
        }

        public Builder templateKontos(List<TemplateKonto> val) {
            templateKontos = val;
            return this;
        }

        public TemplateKontogruppe build() {
            return new TemplateKontogruppe(this);
        }
    }
}
