package ch.internettechnik.anouman.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToOne(cascade = CascadeType.PERSIST)
    private TemplateKontoart templateKontoart;

    @OneToMany(mappedBy = "templateMehrwertsteuerKonto")
    private Set<TemplateMehrwertsteuercode> templateMehrwertsteuercode = new HashSet<>();

    public TemplateKonto() {
    }

    public TemplateKonto(String bezeichnung, String kontonummer, TemplateKontoart templateKontoart) {
        this.bezeichnung = bezeichnung;
        this.kontonummer = kontonummer;
        this.templateKontoart = templateKontoart;
    }

    @Transient
    public String getShowKontonummer() {
        return getTemplateKontoart().getShowKontonummer() + getKontonummer();
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

    public TemplateKontoart getTemplateKontoart() {
        return templateKontoart;
    }

    public void setTemplateKontoart(TemplateKontoart templateKontoart) {
        this.templateKontoart = templateKontoart;
    }

    public Set<TemplateMehrwertsteuercode> getTemplateMehrwertsteuercode() {
        return templateMehrwertsteuercode;
    }

    public void setTemplateMehrwertsteuercode(Set<TemplateMehrwertsteuercode> templateMehrwertsteuercode) {
        this.templateMehrwertsteuercode = templateMehrwertsteuercode;
    }
}
