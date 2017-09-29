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

    @ManyToOne(cascade = CascadeType.PERSIST)
    private TemplateKontogruppe templateKontogruppe;

    @OneToMany(mappedBy = "templateKontoart")
    private List<TemplateKonto> templateKontos = new ArrayList<TemplateKonto>();

    public TemplateKontoart() {
    }

    public TemplateKontoart(String bezeichnung, String kontonummer, TemplateKontogruppe templateKontogruppe) {
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

    public List<TemplateKonto> getTemplateKontos() {
        return templateKontos;
    }

    public void setTemplateKontos(List<TemplateKonto> templateKontos) {
        this.templateKontos = templateKontos;
    }

    public void addKonto(TemplateKonto val) {
        getTemplateKontos().add(val);
    }
}
