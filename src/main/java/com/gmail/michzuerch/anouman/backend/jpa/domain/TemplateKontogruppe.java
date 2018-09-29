package com.gmail.michzuerch.anouman.backend.jpa.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michzuerch on 25.08.15.
 */
@Entity
public class TemplateKontogruppe extends AbstractEntity {
    @Column
    @NotNull
    private String bezeichnung;

    @Column
    @NotNull
    private String kontonummer;

    @ManyToOne
    private TemplateKontohauptgruppe templateKontohauptgruppe;

    @OneToMany(mappedBy = "templateKontogruppe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TemplateKonto> templateKontos = new ArrayList<TemplateKonto>();

    public TemplateKontogruppe() {
    }

    public TemplateKontogruppe(@NotNull String bezeichnung, @NotNull String kontonummer, TemplateKontohauptgruppe templateKontohauptgruppe) {
        this.bezeichnung = bezeichnung;
        this.kontonummer = kontonummer;
        this.templateKontohauptgruppe = templateKontohauptgruppe;
    }

    @Transient
    public String getShowKontonummer() {
        return getTemplateKontohauptgruppe().getShowKontonummer() + getKontonummer();
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

    public TemplateKontohauptgruppe getTemplateKontohauptgruppe() {
        return templateKontohauptgruppe;
    }

    public void setTemplateKontohauptgruppe(TemplateKontohauptgruppe templateKontohauptgruppe) {
        this.templateKontohauptgruppe = templateKontohauptgruppe;
    }

    public List<TemplateKonto> getTemplateKontos() {
        return templateKontos;
    }

    public void setTemplateKontos(List<TemplateKonto> templateKontos) {
        this.templateKontos = templateKontos;
    }
}
