package com.gmail.michzuerch.anouman.backend.jpa.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TemplateKontohauptgruppe extends AbstractEntity {
    @Column
    @NotNull
    private String bezeichnung;

    @Column
    @NotNull
    private String kontonummer;

    @ManyToOne
    private TemplateKontoklasse templateKontoklasse;

    @OneToMany(mappedBy = "templateKontohauptgruppe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TemplateKontogruppe> templateKontogruppes = new ArrayList<>();

    public TemplateKontohauptgruppe() {
    }

    public TemplateKontohauptgruppe(String bezeichnung, String kontonummer, TemplateKontoklasse templateKontoklasse) {
        this.bezeichnung = bezeichnung;
        this.kontonummer = kontonummer;
        this.templateKontoklasse = templateKontoklasse;
    }

    @Transient
    public String getShowKontonummer() {
        return getTemplateKontoklasse().getKontonummer() + getKontonummer();
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getKontonummer() {
        return kontonummer;
    }

    public void setKontonummer(String kontonummer) {
        this.kontonummer = kontonummer;
    }

    public TemplateKontoklasse getTemplateKontoklasse() {
        return templateKontoklasse;
    }

    public void setTemplateKontoklasse(TemplateKontoklasse templateKontoklasse) {
        this.templateKontoklasse = templateKontoklasse;
    }

    public List<TemplateKontogruppe> getTemplateKontogruppes() {
        return templateKontogruppes;
    }

    public void setTemplateKontogruppes(List<TemplateKontogruppe> templateKontogruppes) {
        this.templateKontogruppes = templateKontogruppes;
    }
}
