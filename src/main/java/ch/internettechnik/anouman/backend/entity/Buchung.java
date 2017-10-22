package ch.internettechnik.anouman.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by michzuerch on 07.08.15.
 */
@Entity
public class Buchung extends AbstractEntity {
    @Column
    @NotNull
    private String buchungstext;

    @OneToMany(mappedBy = "buchung", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Unterbuchung> unterbuchungs = new ArrayList<>();

    @ManyToOne
    private Mehrwertsteuercode mehrwertsteuercode;

    @ManyToOne
    private Buchhaltung buchhaltung;

    @Column
    private int belegnummer;

    @Lob
    @Basic
    private byte[] belegkopie;

    public Mehrwertsteuercode getMehrwertsteuercode() {
        return mehrwertsteuercode;
    }

    public void setMehrwertsteuercode(Mehrwertsteuercode mehrwertsteuercode) {
        this.mehrwertsteuercode = mehrwertsteuercode;
    }

    public byte[] getBelegkopie() {
        return belegkopie;
    }

    public void setBelegkopie(byte[] belegkopie) {
        this.belegkopie = belegkopie;
    }

    public Buchhaltung getBuchhaltung() {
        return buchhaltung;
    }

    public void setBuchhaltung(Buchhaltung buchhaltung) {
        this.buchhaltung = buchhaltung;
    }

    public List<Unterbuchung> getUnterbuchungs() {
        return unterbuchungs;
    }

    public void setUnterbuchungs(List<Unterbuchung> unterbuchungs) {
        this.unterbuchungs = unterbuchungs;
    }

    public int getBelegnummer() {
        return belegnummer;
    }

    public void setBelegnummer(int belegnummer) {
        this.belegnummer = belegnummer;
    }

    public String getBuchungstext() {
        return buchungstext;
    }

    public void setBuchungstext(String buchungstext) {
        this.buchungstext = buchungstext;
    }

    @Override
    public String toString() {
        return "Buchung{" +
                "unterbuchungs=" + unterbuchungs +
                ", mehrwertsteuercode=" + mehrwertsteuercode +
                ", belegkopie=" + Arrays.toString(belegkopie) +
                '}';
    }
}
