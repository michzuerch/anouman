package ch.internettechnik.anouman.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

@Entity
public class ReportFOPXsl extends AbstractEntity {
    @Column
    @NotNull
    private String bezeichnung;

    @Lob
    private byte[] xslfile;

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public byte[] getXslfile() {
        return xslfile;
    }

    public void setXslfile(byte[] xslfile) {
        this.xslfile = xslfile;
    }
}
