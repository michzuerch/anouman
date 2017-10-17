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
    private TemplateSammelkonto templateSammelkonto;

    @OneToMany(mappedBy = "templateMehrwertsteuerKonto", cascade = CascadeType.ALL)
    private List<TemplateMehrwertsteuercode> templateMehrwertsteuercode = new ArrayList<>();

    public TemplateKonto() {
    }

    public TemplateKonto(@NotNull String bezeichnung, String bemerkung, @NotNull String kontonummer, TemplateSammelkonto templateSammelkonto) {
        this.bezeichnung = bezeichnung;
        this.bemerkung = bemerkung;
        this.kontonummer = kontonummer;
        this.templateSammelkonto = templateSammelkonto;
    }

    @Transient
    public String getShowKontonummer() {
        return getTemplateSammelkonto().getShowKontonummer() + getKontonummer();
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

    public TemplateSammelkonto getTemplateSammelkonto() {
        return templateSammelkonto;
    }

    public void setTemplateSammelkonto(TemplateSammelkonto templateSammelkonto) {
        this.templateSammelkonto = templateSammelkonto;
    }

    public List<TemplateMehrwertsteuercode> getTemplateMehrwertsteuercode() {
        return templateMehrwertsteuercode;
    }

    public void setTemplateMehrwertsteuercode(List<TemplateMehrwertsteuercode> templateMehrwertsteuercode) {
        this.templateMehrwertsteuercode = templateMehrwertsteuercode;
    }


}
