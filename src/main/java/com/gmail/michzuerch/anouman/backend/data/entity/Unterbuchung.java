package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity(name = "Unterbuchung")
public class Unterbuchung extends AbstractEntity {
    @ManyToOne
    private BookEntry bookEntry;

    @NotNull
    private String buchungstext;

    private LocalDate buchungsdatum;

    private Float betrag;

    @ManyToOne
    @JoinColumn(name = "KONTOSOLL_ID", nullable = false)
    private Konto kontoSoll;

    @ManyToOne
    @JoinColumn(name = "KONTOHABEN_ID", nullable = false)
    private Konto kontoHaben;

    @ManyToOne
    @JoinColumn(name = "BUCHUNG_ID", nullable = false)
    private BookEntry buchung;

    private Unterbuchung(Builder builder) {
        setBookEntry(builder.bookEntry);
        setBuchungstext(builder.buchungstext);
        setBuchungsdatum(builder.buchungsdatum);
        setBetrag(builder.betrag);
        setKontoSoll(builder.kontoSoll);
        setKontoHaben(builder.kontoHaben);
        setBuchung(builder.buchung);
    }

    public BookEntry getBookEntry() {
        return bookEntry;
    }

    public void setBookEntry(BookEntry bookEntry) {
        this.bookEntry = bookEntry;
    }

    public String getBuchungstext() {
        return buchungstext;
    }

    public void setBuchungstext(String buchungstext) {
        this.buchungstext = buchungstext;
    }

    public LocalDate getBuchungsdatum() {
        return buchungsdatum;
    }

    public void setBuchungsdatum(LocalDate buchungsdatum) {
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

    public BookEntry getBuchung() {
        return buchung;
    }

    public void setBuchung(BookEntry buchung) {
        this.buchung = buchung;
    }

    public static final class Builder {
        private BookEntry bookEntry;
        private @NotNull String buchungstext;
        private LocalDate buchungsdatum;
        private Float betrag;
        private Konto kontoSoll;
        private Konto kontoHaben;
        private BookEntry buchung;

        public Builder() {
        }

        public Builder bookEntry(BookEntry val) {
            bookEntry = val;
            return this;
        }

        public Builder buchungstext(@NotNull String val) {
            buchungstext = val;
            return this;
        }

        public Builder buchungsdatum(LocalDate val) {
            buchungsdatum = val;
            return this;
        }

        public Builder betrag(Float val) {
            betrag = val;
            return this;
        }

        public Builder kontoSoll(Konto val) {
            kontoSoll = val;
            return this;
        }

        public Builder kontoHaben(Konto val) {
            kontoHaben = val;
            return this;
        }

        public Builder buchung(BookEntry val) {
            buchung = val;
            return this;
        }

        public Unterbuchung build() {
            return new Unterbuchung(this);
        }
    }
}
