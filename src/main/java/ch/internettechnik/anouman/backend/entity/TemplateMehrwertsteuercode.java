package ch.internettechnik.anouman.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

/**
 * Created by michzuerch on 07.08.15.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "TemplateMehrwertsteuercode.findAll", query = "SELECT m FROM TemplateMehrwertsteuercode m"),
        @NamedQuery(name = "TemplateMehrwertsteuercode.findById", query = "SELECT m FROM TemplateMehrwertsteuercode m where m.id = :id")
})
public class TemplateMehrwertsteuercode extends AbstractEntity {
    @Column
    @NotNull
    private String code;

    @Column
    @NotNull
    private String bezeichnung;

    @Column
    @NotNull
    @Digits(integer = 2, fraction = 2, message = "Ungültiges Zahlenformat")
    @DecimalMin(value = "0.1", message = "Minimale Mehrwertsteuer ist 0.1%")
    @DecimalMax(value = "50", message = "Maximale Mehrwertsteuer ist 50%")
    private Float prozent;

    @ManyToOne
    private TemplateKonto templateMehrwertsteuerKonto;

    @Column
    private boolean verkauf;

    @ManyToOne
    private TemplateBuchhaltung templateBuchhaltung;


    public TemplateMehrwertsteuercode() {
    }

    public TemplateMehrwertsteuercode(String code, String bezeichnung, Float prozent, TemplateKonto mehrwertsteuerKonto, boolean verkauf) {
        this.code = code;
        this.bezeichnung = bezeichnung;
        this.prozent = prozent;
        this.templateMehrwertsteuerKonto = mehrwertsteuerKonto;
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

    public TemplateKonto getTemplateMehrwertsteuerKonto() {
        return templateMehrwertsteuerKonto;
    }

    public void setTemplateMehrwertsteuerKonto(TemplateKonto templateMehrwertsteuerKonto) {
        this.templateMehrwertsteuerKonto = templateMehrwertsteuerKonto;
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

    public String getProzentString() {
        return getProzent().toString();
    }

    public void setProzentString(String prozent) {
        setProzent(Float.valueOf(prozent));
    }

    @Override
    public String toString() {
        return "TemplateMehrwertsteuercode{" +
                "code='" + code + '\'' +
                ", bezeichnung='" + bezeichnung + '\'' +
                ", prozent=" + prozent +
                ", templateMehrwertsteuerKonto id =" + templateMehrwertsteuerKonto.getId() +
                ", verkauf=" + verkauf +
                ", templateBuchhaltung id =" + templateBuchhaltung.getId() +
                '}';
    }
}
