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
        @NamedQuery(name = "TemplateKontogruppe.findAll", query = "SELECT k FROM TemplateKontogruppe k"),
        @NamedQuery(name = "TemplateKontogruppe.findById", query = "SELECT k FROM TemplateKontogruppe k where k.id = :id"),
        @NamedQuery(name = "TemplateKontogruppe.findByKontoklasse", query = "SELECT k FROM TemplateKontogruppe k where k.templateKontoklasse.id = :kontoklasseId")
})
@XmlAccessorType(XmlAccessType.NONE)
public class TemplateKontogruppe extends AbstractEntity {
    @Column
    @NotNull
    @XmlElement
    private String bezeichnung;

    @Column
    @NotNull
    @XmlElement
    private String kontonummer;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private TemplateKontoklasse templateKontoklasse;

    @OneToMany(mappedBy = "templateKontogruppe")
    @XmlElement(name = "Kontoart")
    private List<TemplateKontoart> templateKontoarts = new ArrayList<>();

    public TemplateKontogruppe() {
    }

    public TemplateKontogruppe(String bezeichnung, String kontonummer, TemplateKontoklasse templateKontoklasse) {
        this.bezeichnung = bezeichnung;
        this.kontonummer = kontonummer;
        this.templateKontoklasse = templateKontoklasse;
    }

    @Transient
    @XmlAttribute
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

    public List<TemplateKontoart> getTemplateKontoarts() {
        return templateKontoarts;
    }

    public void setTemplateKontoarts(List<TemplateKontoart> templateKontoarts) {
        this.templateKontoarts = templateKontoarts;
    }

    public void addKontoart(TemplateKontoart val) {
        getTemplateKontoarts().add(val);
    }
}
