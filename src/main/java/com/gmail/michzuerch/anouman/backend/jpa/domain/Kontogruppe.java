package com.gmail.michzuerch.anouman.backend.jpa.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michzuerch on 25.08.15.
 */
@Entity
public class Kontogruppe extends AbstractEntity {
    @Column
    @NotNull
    private String bezeichnung;

    @Column
    @NotNull
    private String kontonummer;

    @ManyToOne
    private Kontohauptgruppe kontohauptgruppe;

    @OneToMany(mappedBy = "kontogruppe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Konto> kontos = new ArrayList<>();

    @Transient
    public String getShowKontonummer() {
        return getKontohauptgruppe().getShowKontonummer() + getKontonummer();
    }

    public Kontohauptgruppe getKontohauptgruppe() {
        return kontohauptgruppe;
    }

    public void setKontohauptgruppe(Kontohauptgruppe kontohauptgruppe) {
        this.kontohauptgruppe = kontohauptgruppe;
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

    public List<Konto> getKontos() {
        return kontos;
    }

    public void setKontos(List<Konto> kontos) {
        this.kontos = kontos;
    }
}
