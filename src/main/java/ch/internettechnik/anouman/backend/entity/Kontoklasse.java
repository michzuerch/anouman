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
        @NamedQuery(name = "Kontoklasse.findAll", query = "SELECT k FROM Kontoklasse k"),
        @NamedQuery(name = "Kontoklasse.findById", query = "SELECT k FROM Kontoklasse k where k.id = :id")
})
public class Kontoklasse extends AbstractEntity {
    @Column
    @NotNull
    private String bezeichnung;

    @Column
    @NotNull
    private String kontonummer;

    @ManyToOne
    private Buchhaltung buchhaltung;

    @OneToMany(mappedBy = "kontoklasse", cascade = CascadeType.ALL)
    private List<Kontohauptgruppe> kontohauptgruppes = new ArrayList<>();

    @Transient
    public String getShowKontonummer() {
        return getKontonummer();
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

    public List<Kontohauptgruppe> getKontohauptgruppes() {
        return kontohauptgruppes;
    }

    public void setKontohauptgruppes(List<Kontohauptgruppe> kontohauptgruppes) {
        this.kontohauptgruppes = kontohauptgruppes;
    }

    public Buchhaltung getBuchhaltung() {
        return buchhaltung;
    }

    public void setBuchhaltung(Buchhaltung buchhaltung) {
        this.buchhaltung = buchhaltung;
    }
}
