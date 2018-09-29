package com.gmail.michzuerch.anouman.backend.jpa.domain.report.css;

import com.gmail.michzuerch.anouman.backend.jpa.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ReportCSS extends AbstractEntity {
    @Column
    @NotNull
    private String bezeichnung;

    @Column
    @NotNull
    @Basic(fetch = FetchType.LAZY)
    private byte[] css;

    @Column
    @NotNull
    @Basic(fetch = FetchType.LAZY)
    private byte[] html;

    @Column
    @NotNull
    @Basic(fetch = FetchType.LAZY)
    private byte[] javascript;

    @OneToMany(mappedBy = "reportCSS", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportCSSImage> reportCSSImages = new ArrayList<>();


    @Transient
    private int sizeCSS;

    @Transient
    private int sizeHTML;

    @Transient
    private int sizeJavascript;

    public int getSizeCSS() {
        return css.length;
    }

    public void setSizeCSS(int sizeCSS) {
        this.sizeCSS = sizeCSS;
    }

    public int getSizeHTML() {
        return html.length;
    }

    public void setSizeHTML(int sizeHTML) {
        this.sizeHTML = sizeHTML;
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

    public int getSizeJavascript() {
        return sizeJavascript;
    }

    public void setSizeJavascript(int sizeJavascript) {
        this.sizeJavascript = sizeJavascript;
    }

    public List<ReportCSSImage> getReportCSSImages() {
        return reportCSSImages;
    }

    public void setReportCSSImages(List<ReportCSSImage> reportCSSImages) {
        this.reportCSSImages = reportCSSImages;
    }
}
