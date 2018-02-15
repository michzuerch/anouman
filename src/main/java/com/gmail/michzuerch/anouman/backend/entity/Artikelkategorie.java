package com.gmail.michzuerch.anouman.backend.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Artikelkategorie extends AbstractEntity {
    @Column
    @NotNull
    private String bezeichnung;

    @OneToMany(mappedBy = "artikelkategorie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Artikel> artikels = new ArrayList<>();

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public List<Artikel> getArtikels() {
        return artikels;
    }

    public void setArtikels(List<Artikel> artikels) {
        this.artikels = artikels;
    }

}
