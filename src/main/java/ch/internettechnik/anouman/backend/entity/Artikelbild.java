package ch.internettechnik.anouman.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Artikelbild extends AbstractEntity {
    @Column
    @NotNull
    private String titel;

    @Column
    @Lob
    private byte[] bild;

    @ManyToOne
    private Artikel artikel;

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
