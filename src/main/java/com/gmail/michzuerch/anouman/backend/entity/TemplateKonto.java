package com.gmail.michzuerch.anouman.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michzuerch on 07.08.15.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "TemplateKonto.findAll", query = "SELECT k FROM TemplateKonto k"),
        @NamedQuery(name = "TemplateKonto.findById", query = "SELECT k FROM TemplateKonto k where k.id = :id")
})
public class TemplateKonto extends AbstractEntity {
    @Column
    @NotNull
    private String bezeichnung;

    @Column
    private String bemerkung;

    @Column
    @NotNull
    private String kontonummer;

    @ManyToOne
    private TemplateKontogruppe templateKontogruppe;

    @OneToMany(mappedBy = "templateMehrwertsteuerKonto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TemplateMehrwertsteuercode> templateMehrwertsteuercodes = new ArrayList<>();

    public TemplateKonto() {
    }

    public TemplateKonto(@NotNull String bezeichnung, String bemerkung, @NotNull String kontonummer, TemplateKontogruppe templateKontogruppe) {
        this.bezeichnung = bezeichnung;
        this.bemerkung = bemerkung;
        this.kontonummer = kontonummer;
        this.templateKontogruppe = templateKontogruppe;
    }

    @Transient
    public String getShowKontonummer() {
        return getTemplateKontogruppe().getShowKontonummer() + getKontonummer();
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getBemerkung() {
        return bemerkung;
    }

    public void setBemerkung(String bemerkung) {
        this.bemerkung = bemerkung;
    }

    public String getKontonummer() {
        return kontonummer;
    }

    public void setKontonummer(String kontonummer) {
        this.kontonummer = kontonummer;
    }

    public TemplateKontogruppe getTemplateKontogruppe() {
        return templateKontogruppe;
    }

    public void setTemplateKontogruppe(TemplateKontogruppe templateKontogruppe) {
        this.templateKontogruppe = templateKontogruppe;
    }

    public List<TemplateMehrwertsteuercode> getTemplateMehrwertsteuercodes() {
        return templateMehrwertsteuercodes;
    }

    public void setTemplateMehrwertsteuercodes(List<TemplateMehrwertsteuercode> templateMehrwertsteuercode) {
        this.templateMehrwertsteuercodes = templateMehrwertsteuercode;
    }


}
