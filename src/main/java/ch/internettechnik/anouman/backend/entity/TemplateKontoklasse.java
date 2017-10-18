package ch.internettechnik.anouman.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michzuerch on 25.08.15.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "TemplateKontoklasse.findAll", query = "SELECT k FROM TemplateKontoklasse k"),
        @NamedQuery(name = "TemplateKontoklasse.findById", query = "SELECT k FROM TemplateKontoklasse k where k.id = :id")
})
public class TemplateKontoklasse extends AbstractEntity {
    @Column
    @NotNull
    private String bezeichnung;

    @Column
    @NotNull
    private String kontonummer;

    @OneToMany(mappedBy = "templateKontoklasse", cascade = CascadeType.ALL)
    private List<TemplateKontohauptgruppe> templateKontohauptgruppes = new ArrayList<TemplateKontohauptgruppe>();

    @ManyToOne
    private TemplateBuchhaltung templateBuchhaltung;


    public TemplateKontoklasse() {
    }

    public TemplateKontoklasse(String bezeichnung, String kontonummer, TemplateBuchhaltung buchhaltung) {
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

    public TemplateBuchhaltung getTemplateBuchhaltung() {
        return templateBuchhaltung;
    }

    public void setTemplateBuchhaltung(TemplateBuchhaltung templateBuchhaltung) {
        this.templateBuchhaltung = templateBuchhaltung;
    }

    public List<TemplateKontohauptgruppe> getTemplateKontohauptgruppes() {
        return templateKontohauptgruppes;
    }

    public void setTemplateKontohauptgruppes(List<TemplateKontohauptgruppe> templateKontohauptgruppes) {
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

