package ch.internettechnik.anouman.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michzuerch on 25.08.15.
 */
@Entity
public class Kontoart extends AbstractEntity {
    @Column
    @NotNull
    private String bezeichnung;

    @Column
    @NotNull
    private String kontonummer;

    @ManyToOne
    private Kontogruppe kontogruppe;

    @OneToMany(mappedBy = "kontoart", cascade = CascadeType.ALL)
    private List<Sammelkonto> sammelkontos = new ArrayList<>();

    @Transient
    public String getShowKontonummer() {
        return getKontogruppe().getShowKontonummer() + getKontonummer();
    }

    public Kontogruppe getKontogruppe() {
        return kontogruppe;
    }

    public void setKontogruppe(Kontogruppe kontogruppe) {
        this.kontogruppe = kontogruppe;
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

    public List<Sammelkonto> getSammelkontos() {
        return sammelkontos;
    }

    public void setSammelkontos(List<Sammelkonto> sammelkontos) {
        this.sammelkontos = sammelkontos;
    }
}
