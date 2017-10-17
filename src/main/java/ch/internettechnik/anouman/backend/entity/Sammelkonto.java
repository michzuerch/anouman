package ch.internettechnik.anouman.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Sammelkonto extends AbstractEntity {
    @Column
    @NotNull
    private String bezeichnung;

    @Column
    @NotNull
    private String kontonummer;

    @ManyToOne
    private Kontoart kontoart;

    @OneToMany(mappedBy = "sammelkonto", cascade = CascadeType.ALL)
    private List<Konto> kontos = new ArrayList<>();

    @Transient
    public String getShowKontonummer() {
        return getKontoart().getShowKontonummer() + getKontonummer();
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

    public Kontoart getKontoart() {
        return kontoart;
    }

    public void setKontoart(Kontoart kontoart) {
        this.kontoart = kontoart;
    }

    public List<Konto> getKontos() {
        return kontos;
    }

    public void setKontos(List<Konto> kontos) {
        this.kontos = kontos;
    }
}

