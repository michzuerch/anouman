package ch.internettechnik.anouman.backend.entity;

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
    private TemplateKontoart templateKontoart;

    @OneToMany(mappedBy = "templateMehrwertsteuerKonto", cascade = CascadeType.ALL)
    private List<TemplateMehrwertsteuercode> templateMehrwertsteuercode = new ArrayList<>();

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

    public List<TemplateMehrwertsteuercode> getTemplateMehrwertsteuercode() {
        return templateMehrwertsteuercode;
    }

    public void setTemplateMehrwertsteuercode(List<TemplateMehrwertsteuercode> templateMehrwertsteuercode) {
        this.templateMehrwertsteuercode = templateMehrwertsteuercode;
    }

    @Override
    public String toString() {
        return "TemplateKonto{" +
                "id='" + getId().toString() + '\'' +
                ", bezeichnung='" + bezeichnung + '\'' +
                ", bemerkung='" + bemerkung + '\'' +
                ", kontonummer='" + kontonummer + '\'' +
                ", templateKontoart=" + templateKontoart +
                ", templateMehrwertsteuercode=" + templateMehrwertsteuercode +
                '}';
    }
}
