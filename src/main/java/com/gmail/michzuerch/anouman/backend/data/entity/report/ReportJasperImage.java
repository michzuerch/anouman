package com.gmail.michzuerch.anouman.backend.data.entity.report;


import com.gmail.michzuerch.anouman.backend.data.entity.AbstractEntity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity(name = "ReportJasperImage")
public class ReportJasperImage extends AbstractEntity {
    private String bezeichnung;

    @ManyToOne
    private ReportJasper reportJasper;

    private String mimeType;

    @Basic(fetch = FetchType.LAZY)
    private byte[] image;

    private ReportJasperImage(Builder builder) {
        setBezeichnung(builder.bezeichnung);
        setReportJasper(builder.reportJasper);
        setMimeType(builder.mimeType);
        setImage(builder.image);
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public ReportJasper getReportJasper() {
        return reportJasper;
    }

    public void setReportJasper(ReportJasper reportJasper) {
        this.reportJasper = reportJasper;
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

    public static final class Builder {
        private String bezeichnung;
        private ReportJasper reportJasper;
        private String mimeType;
        private byte[] image;

        public Builder() {
        }

        public Builder bezeichnung(String val) {
            bezeichnung = val;
            return this;
        }

        public Builder reportJasper(ReportJasper val) {
            reportJasper = val;
            return this;
        }

        public Builder mimeType(String val) {
            mimeType = val;
            return this;
        }

        public Builder image(byte[] val) {
            image = val;
            return this;
        }

        public ReportJasperImage build() {
            return new ReportJasperImage(this);
        }
    }
}