package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity(name = "Unterbuchung")
public class Unterbuchung extends AbstractEntity {
    @ManyToOne
    private BookEntry bookEntry;

    @NotNull
    private String buchungstext;

    private BigDecimal betrag;

    @ManyToOne
    @JoinColumn(name = "KONTOSOLL_ID", nullable = false)
    private Konto kontoSoll;

    @ManyToOne
    @JoinColumn(name = "KONTOHABEN_ID", nullable = false)
    private Konto kontoHaben;

    private Unterbuchung(Builder builder) {
        setBookEntry(builder.bookEntry);
        setBuchungstext(builder.buchungstext);
        setBetrag(builder.betrag);
        setKontoSoll(builder.kontoSoll);
        setKontoHaben(builder.kontoHaben);
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

    public BigDecimal getBetrag() {
        return betrag;
    }

    public void setBetrag(BigDecimal betrag) {
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

    public static final class Builder {
        private BookEntry bookEntry;
        private @NotNull String buchungstext;
        private BigDecimal betrag;
        private Konto kontoSoll;
        private Konto kontoHaben;

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

        public Builder betrag(BigDecimal val) {
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

        public Unterbuchung build() {
            return new Unterbuchung(this);
        }
    }
}
