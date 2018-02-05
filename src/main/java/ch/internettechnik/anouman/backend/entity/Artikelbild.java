package ch.internettechnik.anouman.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Artikelbild extends AbstractEntity {
    @Column
    @NotNull
    private String titel;

    @Column
    private String mimetype;


    @Column
    @Basic(fetch = FetchType.LAZY)
    private Byte[] bild;

    @ManyToOne
    private Artikel artikel;

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

    public Byte[] getBild() {
        return bild;
    }

    public void setBild(Byte[] bild) {
        this.bild = bild;
    }

    public Artikel getArtikel() {
        return artikel;
    }

    public void setArtikel(Artikel artikel) {
        this.artikel = artikel;
    }

    public int getSize() {
        if (getBild() != null) {
            return getBild().length;
        } else return 0;
    }
}
