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
        @NamedQuery(name = "Kontogruppe.findAll", query = "SELECT k FROM Kontogruppe k"),
        @NamedQuery(name = "Kontogruppe.findById", query = "SELECT k FROM Kontogruppe k where k.id = :id"),
        @NamedQuery(name = "Kontogruppe.findByKontoklasse", query = "SELECT k FROM Kontogruppe k where k.kontoklasse.id = :kontoklasseId")

})
public class Kontogruppe extends AbstractEntity {
    @Column
    @NotNull
    private String bezeichnung;

    @Column
    @NotNull
    private String kontonummer;

    @ManyToOne
    private Kontoklasse kontoklasse;

    @OneToMany(mappedBy = "kontogruppe", cascade = CascadeType.ALL)
    private List<Kontoart> kontoarts = new ArrayList<>();

    @Transient
    public String getShowKontonummer() {
        return getKontoklasse().getKontonummer() + getKontonummer();
    }

    public Kontoklasse getKontoklasse() {
        return kontoklasse;
    }

    public void setKontoklasse(Kontoklasse kontoklasse) {
        this.kontoklasse = kontoklasse;
    }

    public List<Kontoart> getKontoarts() {
        return kontoarts;
    }

    public void setKontoarts(List<Kontoart> kontoarts) {
        this.kontoarts = kontoarts;
    }

    public String getKontonummer() {
        return kontonummer;
    }

    public void setKontonummer(String kontonummer) {
        this.kontonummer = kontonummer;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }
}
