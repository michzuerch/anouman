package com.gmail.michzuerch.anouman.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Artikelbild extends com.gmail.michzuerch.anouman.backend.entity.AbstractEntity {
    @Column
    @NotNull
    @Size(min = 3)
    private String titel;

    @Column
    @NotNull
    @Basic(fetch = FetchType.LAZY)
    private byte[] image;

    @ManyToOne
    @NotNull
    private com.gmail.michzuerch.anouman.backend.entity.Artikel artikel;

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] val) {
        this.image = val;
    }

    public com.gmail.michzuerch.anouman.backend.entity.Artikel getArtikel() {
        return artikel;
    }

    public void setArtikel(com.gmail.michzuerch.anouman.backend.entity.Artikel artikel) {
        this.artikel = artikel;
    }

    public int getSize() {
        if (getImage() != null) {
            return getImage().length;
        } else return 0;
    }
}
