package ch.internettechnik.anouman.backend.entity.report.fop;

import ch.internettechnik.anouman.backend.entity.AbstractEntity;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class ReportFOP extends AbstractEntity {
    @Column
    @NotNull
    private String bezeichnung;

    @Column
    @Basic(fetch = FetchType.LAZY)
    @NotEmpty
    private byte[] template;

    @Transient
    private int size;

    public int getSize() {
        return template.length;
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

    public byte[] getTemplate() {
        return template;
    }

    public void setTemplate(byte[] xslfile) {
        this.template = xslfile;
    }
}
