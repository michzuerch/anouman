package com.gmail.michzuerch.anouman.presentation.ui.backup.dto.artikel;

import java.util.Arrays;

public class BackupArtikelbild {
    private String titel;
    private String mimetype;
    private byte[] bild;

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

    @Override
    public String toString() {
        return "BackupArtikelbild{" +
                "titel='" + titel + '\'' +
                ", mimetype='" + mimetype + '\'' +
                ", bild=" + Arrays.toString(bild) +
                '}';
    }
}
