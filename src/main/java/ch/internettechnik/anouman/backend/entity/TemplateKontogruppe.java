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
        @NamedQuery(name = "TemplateKontogruppe.findAll", query = "SELECT k FROM TemplateKontogruppe k"),
        @NamedQuery(name = "TemplateKontogruppe.findById", query = "SELECT k FROM TemplateKontogruppe k where k.id = :id"),
        @NamedQuery(name = "TemplateKontogruppe.findByKontoklasse", query = "SELECT k FROM TemplateKontogruppe k where k.templateKontoklasse.id = :kontoklasseId")
})
public class TemplateKontogruppe extends AbstractEntity {
    @Column
    @NotNull
    private String bezeichnung;

    @Column
    @NotNull
    private String kontonummer;

    @ManyToOne
    private TemplateKontoklasse templateKontoklasse;

    @OneToMany(mappedBy = "templateKontogruppe", cascade = CascadeType.ALL)
    private List<TemplateKontoart> templateKontoarts = new ArrayList<>();

    public TemplateKontogruppe() {
    }

    public TemplateKontogruppe(String bezeichnung, String kontonummer, TemplateKontoklasse templateKontoklasse) {
        this.bezeichnung = bezeichnung;
        this.kontonummer = kontonummer;
        this.templateKontoklasse = templateKontoklasse;
    }

    @Transient
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
}
