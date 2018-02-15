package com.gmail.michzuerch.anouman.backend.entity.report.fop;

import com.gmail.michzuerch.anouman.backend.entity.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class ReportFOPImage extends AbstractEntity {

    @Column
    private String bezeichnung;

    @ManyToOne
    private ReportFOP reportFOP;

    @Column
    @Basic(fetch = FetchType.LAZY)
    @NotEmpty
    private byte[] image;


    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public ReportFOP getReportFOP() {
        return reportFOP;
    }

    public void setReportFOP(ReportFOP reportFOP) {
        this.reportFOP = reportFOP;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
