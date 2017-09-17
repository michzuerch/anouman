package ch.internettechnik.anouman.backend.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by michzuerch on 07.08.15.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "TemplateMehrwertsteuercode.findAll", query = "SELECT m FROM TemplateMehrwertsteuercode m"),
        @NamedQuery(name = "TemplateMehrwertsteuercode.findById", query = "SELECT m FROM TemplateMehrwertsteuercode m where m.id = :id")
})
@XmlAccessorType(XmlAccessType.NONE)
public class TemplateMehrwertsteuercode extends AbstractEntity {
    @Column
    @NotNull
    @NotBlank
    @XmlElement
    private String code;

    @Column
    @NotNull
    @NotBlank
    @XmlElement
    private String bezeichnung;

    @Column
    @NotNull
    @Range(min = 1, max = 45)
    @XmlElement
    private Float prozent;

    @OneToOne
    private TemplateKonto mehrwertsteuerKonto;

    @Column
    @XmlElement
    private boolean verkauf;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private TemplateBuchhaltung templateBuchhaltung;


    public TemplateMehrwertsteuercode() {
    }

    public TemplateMehrwertsteuercode(String code, String bezeichnung, Float prozent, TemplateKonto mehrwertsteuerKonto, boolean verkauf) {
        this.code = code;
        this.bezeichnung = bezeichnung;
        this.prozent = prozent;
        this.mehrwertsteuerKonto = mehrwertsteuerKonto;
        this.verkauf = verkauf;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public Float getProzent() {
        return prozent;
    }

    public void setProzent(Float prozent) {
        this.prozent = prozent;
    }

    public TemplateKonto getMehrwertsteuerKonto() {
        return mehrwertsteuerKonto;
    }

    public void setMehrwertsteuerKonto(TemplateKonto mehrwertsteuerKonto) {
        this.mehrwertsteuerKonto = mehrwertsteuerKonto;
    }

    public boolean isVerkauf() {
        return verkauf;
    }

    public void setVerkauf(boolean verkauf) {
        this.verkauf = verkauf;
    }

    public TemplateBuchhaltung getTemplateBuchhaltung() {
        return templateBuchhaltung;
    }

    public void setTemplateBuchhaltung(TemplateBuchhaltung templateBuchhaltung) {
        this.templateBuchhaltung = templateBuchhaltung;
    }
}
