package ch.internettechnik.anouman.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TemplateSammelkonto extends AbstractEntity {
    @Column
    @NotNull
    private String bezeichnung;

    @Column
    @NotNull
    private String kontonummer;

    @ManyToOne
    private TemplateKontoart templateKontoart;

    @OneToMany(mappedBy = "templateSammelkonto", cascade = CascadeType.ALL)
    private List<TemplateKonto> templateKontos = new ArrayList<TemplateKonto>();

    public TemplateSammelkonto() {
    }

    public TemplateSammelkonto(@NotNull String bezeichnung, @NotNull String kontonummer, TemplateKontoart templateKontoart) {
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

    public List<TemplateKonto> getTemplateKontos() {
        return templateKontos;
    }

    public void setTemplateKontos(List<TemplateKonto> templateKontos) {
        this.templateKontos = templateKontos;
    }
}
