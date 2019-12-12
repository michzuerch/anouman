package com.gmail.michzuerch.anouman.backend.data.entity.report;

import com.gmail.michzuerch.anouman.backend.data.entity.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "ReportCSS")
public class ReportCSS extends AbstractEntity {
    @NotNull
    private String bezeichnung;

    @NotNull
    @Basic(fetch = FetchType.LAZY)
    private byte[] css;

    @NotNull
    @Basic(fetch = FetchType.LAZY)
    private byte[] html;

    @NotNull
    @Basic(fetch = FetchType.LAZY)
    private byte[] javascript;

    @OneToMany(mappedBy = "reportCSS", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportCSSImage> reportCSSImages = new ArrayList<>();

    private ReportCSS(Builder builder) {
        setBezeichnung(builder.bezeichnung);
        setCss(builder.css);
        setHtml(builder.html);
        setJavascript(builder.javascript);
        setReportCSSImages(builder.reportCSSImages);
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public byte[] getCss() {
        return css;
    }

    public void setCss(byte[] css) {
        this.css = css;
    }

    public byte[] getHtml() {
        return html;
    }

    public void setHtml(byte[] html) {
        this.html = html;
    }

    public byte[] getJavascript() {
        return javascript;
    }

    public void setJavascript(byte[] javascript) {
        this.javascript = javascript;
    }

    public List<ReportCSSImage> getReportCSSImages() {
        return reportCSSImages;
    }

    public void setReportCSSImages(List<ReportCSSImage> reportCSSImages) {
        this.reportCSSImages = reportCSSImages;
    }

    public static final class Builder {
        private @NotNull String bezeichnung;
        private @NotNull byte[] css;
        private @NotNull byte[] html;
        private @NotNull byte[] javascript;
        private List<ReportCSSImage> reportCSSImages;

        public Builder() {
        }

        public Builder bezeichnung(@NotNull String val) {
            bezeichnung = val;
            return this;
        }

        public Builder css(@NotNull byte[] val) {
            css = val;
            return this;
        }

        public Builder html(@NotNull byte[] val) {
            html = val;
            return this;
        }

        public Builder javascript(@NotNull byte[] val) {
            javascript = val;
            return this;
        }

        public Builder reportCSSImages(List<ReportCSSImage> val) {
            reportCSSImages = val;
            return this;
        }

        public ReportCSS build() {
            return new ReportCSS(this);
        }
    }
}