package com.gmail.michzuerch.anouman.backend.data.entity.report;

import com.gmail.michzuerch.anouman.backend.data.entity.AbstractEntity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity(name = "ReportFOPImage")
public class ReportFOPImage extends AbstractEntity {
    private String bezeichnung;

    @ManyToOne
    private ReportFOP reportFOP;

    private String mimeType;

    @Basic(fetch = FetchType.LAZY)
    private byte[] image;

    private ReportFOPImage(Builder builder) {
        setBezeichnung(builder.bezeichnung);
        setReportFOP(builder.reportFOP);
        setMimeType(builder.mimeType);
        setImage(builder.image);
    }

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
        private ReportFOP reportFOP;
        private String mimeType;
        private byte[] image;

        public Builder() {
        }

        public Builder bezeichnung(String val) {
            bezeichnung = val;
            return this;
        }

        public Builder reportFOP(ReportFOP val) {
            reportFOP = val;
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

        public ReportFOPImage build() {
            return new ReportFOPImage(this);
        }
    }
}
