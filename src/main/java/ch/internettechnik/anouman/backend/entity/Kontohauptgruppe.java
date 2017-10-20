package ch.internettechnik.anouman.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michzuerch on 25.08.15.
 */
@Entity
public class Kontohauptgruppe extends AbstractEntity {
    @Column
    @NotNull
    private String bezeichnung;

    @Column
    @NotNull
    private String kontonummer;

    @ManyToOne
    private Kontoklasse kontoklasse;

    @OneToMany(mappedBy = "kontohauptgruppe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Kontogruppe> kontogruppes = new ArrayList<>();

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

    public List<Kontogruppe> getKontogruppes() {
        return kontogruppes;
    }

    public void setKontogruppes(List<Kontogruppe> kontogruppes) {
        this.kontogruppes = kontogruppes;
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
