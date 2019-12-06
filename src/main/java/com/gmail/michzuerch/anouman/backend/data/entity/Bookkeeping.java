package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Bookkeeping")
public class Bookkeeping extends AbstractEntity {
    @NotNull
    private String description;

    @NotNull
    @Digits(integer = 4, fraction = 0, message = "Ungültiges Zahlenformat")
    @DecimalMin(value = "1950", message = "Nicht vor 1950")
    @DecimalMax(value = "2150", message = "Nicht nach 2150")
    private int year;

    @OneToMany(mappedBy = "bookkeeping", cascade = CascadeType.ALL)
    private List<Kontoklasse> kontoklasses = new ArrayList<>();

    @OneToMany(mappedBy = "bookkeeping", cascade = CascadeType.ALL)
    private List<BookEntry> bookEntries = new ArrayList<>();

    private Bookkeeping(Builder builder) {
        setDescription(builder.description);
        setYear(builder.year);
        setKontoklasses(builder.kontoklasses);
        setBookEntries(builder.bookEntries);
    }

    public Bookkeeping() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Kontoklasse> getKontoklasses() {
        return kontoklasses;
    }

    public void setKontoklasses(List<Kontoklasse> kontoklasses) {
        this.kontoklasses = kontoklasses;
    }

    public List<BookEntry> getBookEntries() {
        return bookEntries;
    }

    public void setBookEntries(List<BookEntry> bookEntries) {
        this.bookEntries = bookEntries;
    }

    public static final class Builder {
        private @NotNull String description;
        private @NotNull @Digits(integer = 4, fraction = 0, message = "Ungültiges Zahlenformat") @DecimalMin(value = "1950", message = "Nicht vor 1950") @DecimalMax(value = "2150", message = "Nicht nach 2150") int year;
        private List<Kontoklasse> kontoklasses;
        private List<BookEntry> bookEntries;

        public Builder() {
        }

        public Builder description(@NotNull String val) {
            description = val;
            return this;
        }

        public Builder year(@NotNull @Digits(integer = 4, fraction = 0, message = "Ungültiges Zahlenformat") @DecimalMin(value = "1950", message = "Nicht vor 1950") @DecimalMax(value = "2150", message = "Nicht nach 2150") int val) {
            year = val;
            return this;
        }

        public Builder kontoklasses(List<Kontoklasse> val) {
            kontoklasses = val;
            return this;
        }

        public Builder bookEntries(List<BookEntry> val) {
            bookEntries = val;
            return this;
        }

        public Bookkeeping build() {
            return new Bookkeeping(this);
        }
    }
}
