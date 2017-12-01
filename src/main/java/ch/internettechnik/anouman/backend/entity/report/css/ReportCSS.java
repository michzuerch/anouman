package ch.internettechnik.anouman.backend.entity.report.css;

import ch.internettechnik.anouman.backend.entity.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class ReportCSS extends AbstractEntity {
    @Column
    @NotNull
    private String bezeichnung;

    @Column
    @Basic(fetch = FetchType.LAZY)
    private byte[] css;

    @Column
    @Basic(fetch = FetchType.LAZY)
    private byte[] html;

    @Column
    private String filename;

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
}
