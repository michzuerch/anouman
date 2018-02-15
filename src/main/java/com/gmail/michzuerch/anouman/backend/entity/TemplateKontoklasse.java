package com.gmail.michzuerch.anouman.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michzuerch on 25.08.15.
 */
@Entity
public class TemplateKontoklasse extends com.gmail.michzuerch.anouman.backend.entity.AbstractEntity {
    @Column
    @NotNull
    private String bezeichnung;

    @Column
    @NotNull
    private String kontonummer;

    @OneToMany(mappedBy = "templateKontoklasse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<com.gmail.michzuerch.anouman.backend.entity.TemplateKontohauptgruppe> templateKontohauptgruppes = new ArrayList<com.gmail.michzuerch.anouman.backend.entity.TemplateKontohauptgruppe>();

    @ManyToOne
    private com.gmail.michzuerch.anouman.backend.entity.TemplateBuchhaltung templateBuchhaltung;


    public TemplateKontoklasse() {
    }

    public TemplateKontoklasse(String bezeichnung, String kontonummer, com.gmail.michzuerch.anouman.backend.entity.TemplateBuchhaltung buchhaltung) {
        this.bezeichnung = bezeichnung;
        this.kontonummer = kontonummer;
        this.templateBuchhaltung = buchhaltung;
    }

    @Transient
    public String getShowKontonummer() {
        return getKontonummer();
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

    public com.gmail.michzuerch.anouman.backend.entity.TemplateBuchhaltung getTemplateBuchhaltung() {
        return templateBuchhaltung;
    }

    public void setTemplateBuchhaltung(com.gmail.michzuerch.anouman.backend.entity.TemplateBuchhaltung templateBuchhaltung) {
        this.templateBuchhaltung = templateBuchhaltung;
    }

    public List<com.gmail.michzuerch.anouman.backend.entity.TemplateKontohauptgruppe> getTemplateKontohauptgruppes() {
        return templateKontohauptgruppes;
    }

    public void setTemplateKontohauptgruppes(List<com.gmail.michzuerch.anouman.backend.entity.TemplateKontohauptgruppe> templateKontohauptgruppes) {
        this.templateKontohauptgruppes = templateKontohauptgruppes;
    }

    @Override
    public String toString() {
        return "TemplateKontoklasse{" +
                ", id=" + id +
                "bezeichnung='" + bezeichnung + '\'' +
                ", kontonummer='" + kontonummer + '\'' +
                '}';
    }
}

