package ch.internettechnik.anouman.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "kontoklasse")
    private Set<Kontogruppe> kontogruppes = new HashSet<>();

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

    public Set<Kontogruppe> getKontogruppes() {
        return kontogruppes;
    }

    public void setKontogruppes(Set<Kontogruppe> kontogruppes) {
        this.kontogruppes = kontogruppes;
    }


    public Buchhaltung getBuchhaltung() {
        return buchhaltung;
    }

    public void setBuchhaltung(Buchhaltung buchhaltung) {
        this.buchhaltung = buchhaltung;
    }
}
