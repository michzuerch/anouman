package com.gmail.michzuerch.anouman.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Artikelbild extends com.gmail.michzuerch.anouman.backend.entity.AbstractEntity {
    @Column
    @NotNull
    private String titel;

    @Column
    private String mimetype;

    @Column
    @Basic(fetch = FetchType.LAZY)
    private byte[] bild;

    @ManyToOne
    private com.gmail.michzuerch.anouman.backend.entity.Artikel artikel;

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public byte[] getBild() {
        return bild;
    }

    public void setBild(byte[] bild) {
        this.bild = bild;
    }

    public com.gmail.michzuerch.anouman.backend.entity.Artikel getArtikel() {
        return artikel;
    }

    public void setArtikel(com.gmail.michzuerch.anouman.backend.entity.Artikel artikel) {
        this.artikel = artikel;
    }

    public int getSize() {
        if (getBild() != null) {
            return getBild().length;
        } else return 0;
    }
}
