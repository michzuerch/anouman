package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "TemplateKontoHauptgruppe")
public class TemplateKontoHauptgruppe extends AbstractEntity {
    @NotNull
    private String kontonummer;

    @NotNull
    private String description;

    @ManyToOne
    private TemplateKontoklasse templateKontoklasse;

    @OneToMany(mappedBy = "templateKontoHauptgruppe", cascade = CascadeType.ALL)
    private List<TemplateKontogruppe> kontogruppes;

    private TemplateKontoHauptgruppe(Builder builder) {
        setKontonummer(builder.kontonummer);
        setDescription(builder.description);
        setTemplateKontoklasse(builder.templateKontoklasse);
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

    public TemplateKontoklasse getTemplateKontoklasse() {
        return templateKontoklasse;
    }

    public void setTemplateKontoklasse(TemplateKontoklasse templateKontoklasse) {
        this.templateKontoklasse = templateKontoklasse;
    }

    public List<TemplateKontogruppe> getKontogruppes() {
        return kontogruppes;
    }

    public void setKontogruppes(List<TemplateKontogruppe> kontogruppes) {
        this.kontogruppes = kontogruppes;
    }

    public static final class Builder {
        private @NotNull String kontonummer;
        private @NotNull String description;
        private TemplateKontoklasse templateKontoklasse;
        private List<TemplateKontogruppe> kontogruppes;

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

        public Builder templateKontoklasse(TemplateKontoklasse val) {
            templateKontoklasse = val;
            return this;
        }

        public Builder kontogruppes(List<TemplateKontogruppe> val) {
            kontogruppes = val;
            return this;
        }

        public TemplateKontoHauptgruppe build() {
            return new TemplateKontoHauptgruppe(this);
        }
    }
}
