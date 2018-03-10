package com.gmail.michzuerch.anouman.backend.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;

@Entity
public class ImageTest extends AbstractEntity {
    @Column
    @NotNull
    private String titel;

    @Column
    @NotNull
    @Basic(fetch = FetchType.LAZY)
    private byte[] bild;

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public byte[] getBild() {
        return bild;
    }

    public void setBild(byte[] bild) {
        this.bild = bild;
    }
}
