package com.gmail.michzuerch.anouman.backend.jpa.domain;

import com.gmail.michzuerch.anouman.presentation.ui.util.field.ImageAndMimetype;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Artikelbild extends AbstractEntity {
    @Column
    @NotNull
    @Size(min = 3)
    private String titel;

    @Column
    private String mimetype;

    @Column
    @NotNull
    @Basic(fetch = FetchType.LAZY)
    private byte[] image;

    @ManyToOne
    @NotNull
    private Artikel artikel;

    @Transient
    private ImageAndMimetype imageAndMimetype;

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

    public Artikel getArtikel() {
        return artikel;
    }

    public void setArtikel(Artikel artikel) {
        this.artikel = artikel;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public ImageAndMimetype getImageAndMimetype() {
        return imageAndMimetype;
    }

    public void setImageAndMimetype(ImageAndMimetype imageAndMimetype) {
        this.imageAndMimetype = imageAndMimetype;
        setImage(imageAndMimetype.getImage());
        setMimetype(imageAndMimetype.getMimetype());
    }

    public int getSize() {
        if (getImage() != null) {
            return getImage().length;
        } else return 0;
    }
}