package ch.internettechnik.anouman.backend.entity.report.fop;

import ch.internettechnik.anouman.backend.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
public class ReportFOPXsl extends AbstractEntity {
    @Column
    @NotNull
    private String bezeichnung;

    @Lob
    private byte[] xslfile;

    @Transient
    private int size;

    public int getSize() {
        return xslfile.length;
    }

    public void setSize(int size) {
        this.size = size;
    }


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
