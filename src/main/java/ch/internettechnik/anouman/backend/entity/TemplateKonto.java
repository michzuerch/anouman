package ch.internettechnik.anouman.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by michzuerch on 07.08.15.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "TemplateKonto.findAll", query = "SELECT k FROM TemplateKonto k"),
        @NamedQuery(name = "TemplateKonto.findById", query = "SELECT k FROM TemplateKonto k where k.id = :id")
})
@XmlAccessorType(XmlAccessType.NONE)
public class TemplateKonto extends AbstractEntity {
    @Column
    @NotNull
    @XmlElement
    private String bezeichnung;

    @Column
    @NotNull
    @XmlElement
    private String kontonummer;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private TemplateKontoart templateKontoart;

    public TemplateKonto() {
    }

    public TemplateKonto(String bezeichnung, String kontonummer, TemplateKontoart templateKontoart) {
        this.bezeichnung = bezeichnung;
        this.kontonummer = kontonummer;
        this.templateKontoart = templateKontoart;
    }

    @Transient
    @XmlAttribute
    public String getShowKontonummer() {
        return getTemplateKontoart().getShowKontonummer() + getKontonummer();
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

    public TemplateKontoart getTemplateKontoart() {
        return templateKontoart;
    }

    public void setTemplateKontoart(TemplateKontoart templateKontoart) {
        this.templateKontoart = templateKontoart;
    }
}
