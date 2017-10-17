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
        @NamedQuery(name = "TemplateKontoart.findAll", query = "SELECT k FROM TemplateKontoart k"),
        @NamedQuery(name = "TemplateKontoart.findById", query = "SELECT k FROM TemplateKontoart k where k.id = :id")
})
public class TemplateKontoart extends AbstractEntity {
    @Column
    @NotNull
    private String bezeichnung;

    @Column
    @NotNull
    private String kontonummer;

    @ManyToOne
    private TemplateKontogruppe templateKontogruppe;

    @OneToMany(mappedBy = "templateKontoart", cascade = CascadeType.ALL)
    private List<TemplateSammelkonto> templateSammelkontos = new ArrayList<TemplateSammelkonto>();

    public TemplateKontoart() {
    }

    public TemplateKontoart(@NotNull String bezeichnung, @NotNull String kontonummer, TemplateKontogruppe templateKontogruppe) {
        this.bezeichnung = bezeichnung;
        this.kontonummer = kontonummer;
        this.templateKontogruppe = templateKontogruppe;
    }

    @Transient
    public String getShowKontonummer() {
        return getTemplateKontogruppe().getShowKontonummer() + getKontonummer();
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

    public TemplateKontogruppe getTemplateKontogruppe() {
        return templateKontogruppe;
    }

    public void setTemplateKontogruppe(TemplateKontogruppe templateKontogruppe) {
        this.templateKontogruppe = templateKontogruppe;
    }

    public List<TemplateSammelkonto> getTemplateSammelkontos() {
        return templateSammelkontos;
    }

    public void setTemplateSammelkontos(List<TemplateSammelkonto> templateSammelkontos) {
        this.templateSammelkontos = templateSammelkontos;
    }
}
