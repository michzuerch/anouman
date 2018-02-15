package com.gmail.michzuerch.anouman.backend.entity.report.jasper;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michzuerch on 10.06.15.
 */
@Entity
public class ReportJasper extends com.gmail.michzuerch.anouman.backend.entity.AbstractEntity {
    @Column
    @NotNull
    @Size(min = 1, max = 50)
    private String bezeichnung;

    @Column
    @Basic(fetch = FetchType.LAZY)
    private byte[] templateSource;

    @Column
    @Basic(fetch = FetchType.LAZY)
    private byte[] templateCompiled;

    @OneToMany(mappedBy = "reportJasper", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<com.gmail.michzuerch.anouman.backend.entity.report.jasper.ReportJasperImage> reportJasperImages = new ArrayList<>();

    @Column
    private String filename;

    @Transient
    private int sizeSource;

    @Transient
    private int sizeCompiled;

    @Transient
    private int anzahlReportJasperImages;

    public int getSizeSource() {
        if (templateSource == null) {
            return 0;
        } else {
            return templateSource.length;
        }
    }

    public void setSizeSource(int sizeSource) {
        this.sizeSource = sizeSource;
    }

    public int getSizeCompiled() {
        if (templateCompiled == null) {
            return 0;
        } else {
            return templateCompiled.length;
        }
    }

    public void setSizeCompiled(int sizeCompiled) {
        this.sizeCompiled = sizeCompiled;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public byte[] getTemplateSource() {
        return templateSource;
    }

    public void setTemplateSource(byte[] template) {
        this.templateSource = template;
    }

    public byte[] getTemplateCompiled() {
        return templateCompiled;
    }

    public void setTemplateCompiled(byte[] templateCompiled) {
        this.templateCompiled = templateCompiled;
    }

    public List<com.gmail.michzuerch.anouman.backend.entity.report.jasper.ReportJasperImage> getReportJasperImages() {
        return reportJasperImages;
    }

    public void setReportJasperImages(List<com.gmail.michzuerch.anouman.backend.entity.report.jasper.ReportJasperImage> reportJasperImages) {
        this.reportJasperImages = reportJasperImages;
    }

    public int getAnzahlReportJasperImages() {
        return getReportJasperImages().size();
    }

    public void setAnzahlReportJasperImages(int anzahlReportJasperImages) {
        this.anzahlReportJasperImages = anzahlReportJasperImages;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        return "ReportJasper{" +
                "bezeichnung='" + bezeichnung + '\'' +
                ", reportJasperImages=" + reportJasperImages +
                ", filename='" + filename + '\'' +
                ", sizeSource=" + sizeSource +
                ", sizeCompiled=" + sizeCompiled +
                ", anzahlReportJasperImages=" + anzahlReportJasperImages +
                '}';
    }
}
