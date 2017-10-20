package ch.internettechnik.anouman.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by michzuerch on 07.08.15.
 */
@Entity
public class Unterbuchung extends AbstractEntity {
    @Column
    @NotNull
    private String buchungstext;

    @Column
    private Integer laufnummer;

    @Column
    @Temporal(TemporalType.DATE)
    private Date buchungsdatum;

    @Column
    @NotNull
    private Float betrag;

    @ManyToOne
    @JoinColumn(name = "KONTOSOLL_ID", nullable = false)
    private Konto kontoSoll;

    @ManyToOne
    @JoinColumn(name = "KONTOHABEN_ID", nullable = false)
    private Konto kontoHaben;

    @ManyToOne
    @JoinColumn(name = "BUCHUNG_ID", nullable = false)
    private Buchung buchung;

    public String getBuchungstext() {
        return buchungstext;
    }

    public void setBuchungstext(String buchungstext) {
        this.buchungstext = buchungstext;
    }

    public Integer getLaufnummer() {
        return laufnummer;
    }

    public void setLaufnummer(Integer laufnummer) {
        this.laufnummer = laufnummer;
    }

    public Date getBuchungsdatum() {
        return buchungsdatum;
    }

    public void setBuchungsdatum(Date buchungsdatum) {
        this.buchungsdatum = buchungsdatum;
    }

    public Float getBetrag() {
        return betrag;
    }

    public void setBetrag(Float betrag) {
        this.betrag = betrag;
    }

    public Konto getKontoSoll() {
        return kontoSoll;
    }

    public void setKontoSoll(Konto kontoSoll) {
        this.kontoSoll = kontoSoll;
    }

    public Konto getKontoHaben() {
        return kontoHaben;
    }

    public void setKontoHaben(Konto kontoHaben) {
        this.kontoHaben = kontoHaben;
    }

    public Buchung getBuchung() {
        return buchung;
    }

    public void setBuchung(Buchung buchung) {
        this.buchung = buchung;
    }

    @Override
    public String toString() {
        return "Unterbuchung{" +
                "buchungstext='" + buchungstext + '\'' +
                ", laufnummer=" + laufnummer +
                ", buchungsdatum=" + buchungsdatum +
                ", betrag=" + betrag +
                ", kontoSoll=" + kontoSoll +
                ", kontoHaben=" + kontoHaben +
                ", buchung=" + buchung +
                '}';
    }
}
