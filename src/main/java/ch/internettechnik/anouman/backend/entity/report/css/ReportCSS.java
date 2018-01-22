package ch.internettechnik.anouman.backend.entity.report.css;

import ch.internettechnik.anouman.backend.entity.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ReportCSS extends AbstractEntity {
    @Column
    @NotNull
    private String bezeichnung;

    @Column
    @Basic(fetch = FetchType.LAZY)
    @NotEmpty
    private byte[] css;

    @Column
    @Basic(fetch = FetchType.LAZY)
    @NotEmpty
    private byte[] html;

    @Column
    private String filename;

    @OneToMany(mappedBy = "reportCSS", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportCSSImage> reportCSSImages = new ArrayList<>();


    @Transient
    private int sizeCSS;

    @Transient
    private int sizeHTML;

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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public List<ReportCSSImage> getReportCSSImages() {
        return reportCSSImages;
    }

    public void setReportCSSImages(List<ReportCSSImage> reportCSSImages) {
        this.reportCSSImages = reportCSSImages;
    }
}
