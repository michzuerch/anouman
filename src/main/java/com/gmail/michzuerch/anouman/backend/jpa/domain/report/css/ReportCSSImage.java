package com.gmail.michzuerch.anouman.backend.jpa.domain.report.css;

import com.gmail.michzuerch.anouman.backend.jpa.domain.AbstractEntity;

import javax.persistence.*;

@Entity
public class ReportCSSImage extends AbstractEntity {

    @Column
    private String bezeichnung;

    @ManyToOne
    private com.gmail.michzuerch.anouman.backend.jpa.domain.report.css.ReportCSS reportCSS;

    @Column
    private String mimeType;

    @Column
    @Basic(fetch = FetchType.LAZY)
    private byte[] image;


    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public com.gmail.michzuerch.anouman.backend.jpa.domain.report.css.ReportCSS getReportCSS() {
        return reportCSS;
    }

    public void setReportCSS(com.gmail.michzuerch.anouman.backend.jpa.domain.report.css.ReportCSS reportCSS) {
        this.reportCSS = reportCSS;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
