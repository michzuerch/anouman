package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "TemplateKontoklasse")
public class TemplateKontoklasse extends AbstractEntity {
    @NotNull
    private String kontonummer;

    @NotNull
    private String description;

    @ManyToOne
    private TemplateBookkeeping templateBookkeeping;

    @OneToMany(mappedBy = "templateKontoklasse", cascade = CascadeType.ALL)
    private List<TemplateKontoHauptgruppe> templateKontoHauptgruppes;

    private TemplateKontoklasse(Builder builder) {
        setKontonummer(builder.kontonummer);
        setDescription(builder.description);
        setTemplateBookkeeping(builder.templateBookkeeping);
        setTemplateKontoHauptgruppes(builder.templateKontoHauptgruppes);
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

    public TemplateBookkeeping getTemplateBookkeeping() {
        return templateBookkeeping;
    }

    public void setTemplateBookkeeping(TemplateBookkeeping templateBookkeeping) {
        this.templateBookkeeping = templateBookkeeping;
    }

    public List<TemplateKontoHauptgruppe> getTemplateKontoHauptgruppes() {
        return templateKontoHauptgruppes;
    }

    public void setTemplateKontoHauptgruppes(List<TemplateKontoHauptgruppe> templateKontoHauptgruppes) {
        this.templateKontoHauptgruppes = templateKontoHauptgruppes;
    }

    public static final class Builder {
        private @NotNull String kontonummer;
        private @NotNull String description;
        private TemplateBookkeeping templateBookkeeping;
        private List<TemplateKontoHauptgruppe> templateKontoHauptgruppes;

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

        public Builder templateBookkeeping(TemplateBookkeeping val) {
            templateBookkeeping = val;
            return this;
        }

        public Builder templateKontoHauptgruppes(List<TemplateKontoHauptgruppe> val) {
            templateKontoHauptgruppes = val;
            return this;
        }

        public TemplateKontoklasse build() {
            return new TemplateKontoklasse(this);
        }
    }
}
