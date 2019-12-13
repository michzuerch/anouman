package com.gmail.michzuerch.anouman.backend.data.entity.report;

import com.gmail.michzuerch.anouman.backend.data.entity.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "ReportFOP")
public class ReportFOP extends AbstractEntity {
    @NotNull
    private String bezeichnung;

    @Basic(fetch = FetchType.LAZY)
    private byte[] template;

    @OneToMany(mappedBy = "reportFOP", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportFOPImage> reportFOPImages = new ArrayList<>();

    private ReportFOP(Builder builder) {
        setBezeichnung(builder.bezeichnung);
        setTemplate(builder.template);
        setReportFOPImages(builder.reportFOPImages);
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

    public void setTemplate(byte[] template) {
        this.template = template;
    }

    public List<ReportFOPImage> getReportFOPImages() {
        return reportFOPImages;
    }

    public void setReportFOPImages(List<ReportFOPImage> reportFOPImages) {
        this.reportFOPImages = reportFOPImages;
    }


    public static final class Builder {
        private @NotNull String bezeichnung;
        private byte[] template;
        private List<ReportFOPImage> reportFOPImages;

        public Builder() {
        }

        public Builder bezeichnung(@NotNull String val) {
            bezeichnung = val;
            return this;
        }

        public Builder template(byte[] val) {
            template = val;
            return this;
        }

        public Builder reportFOPImages(List<ReportFOPImage> val) {
            reportFOPImages = val;
            return this;
        }

        public ReportFOP build() {
            return new ReportFOP(this);
        }
    }
}
