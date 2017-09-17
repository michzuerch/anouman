package ch.internettechnik.anouman.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
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
@XmlAccessorType(XmlAccessType.NONE)
public class TemplateKontoklasse extends AbstractEntity {
    @Column
    @NotNull
    @XmlElement
    private String bezeichnung;

    @Column
    @NotNull
    @XmlElement
    private String kontonummer;

    @XmlElement(name = "Kontogruppe")
    @OneToMany(mappedBy = "templateKontoklasse")
    private List<TemplateKontogruppe> templateKontogruppes = new ArrayList<TemplateKontogruppe>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    private TemplateBuchhaltung templateBuchhaltung;


    public TemplateKontoklasse() {
    }

    public TemplateKontoklasse(String bezeichnung, String kontonummer, TemplateBuchhaltung buchhaltung) {
        this.bezeichnung = bezeichnung;
        this.kontonummer = kontonummer;
        this.templateBuchhaltung = buchhaltung;
    }

    @Transient
    @XmlAttribute
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

    public List<TemplateKontogruppe> getTemplateKontogruppes() {
        return templateKontogruppes;
    }

    public void setTemplateKontogruppes(List<TemplateKontogruppe> templateKontogruppes) {
        this.templateKontogruppes = templateKontogruppes;
    }

    public void addKontogruppe(TemplateKontogruppe val) {
        getTemplateKontogruppes().add(val);
    }
}

