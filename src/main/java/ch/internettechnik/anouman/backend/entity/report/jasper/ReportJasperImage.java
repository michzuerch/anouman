package ch.internettechnik.anouman.backend.entity.report.jasper;

import ch.internettechnik.anouman.backend.entity.AbstractEntity;

import javax.persistence.*;

@Entity
public class ReportJasperImage extends AbstractEntity {

    @Column
    private String bezeichnung;

    @ManyToOne
    private ReportJasper reportJasper;

    @Column
    private String mimeType;

    @Column
    @Basic(fetch = FetchType.LAZY)
    private Byte[] image;

    @Transient
    private int size;


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

    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }

    public int getSize() {
        if (getImage() == null) return 0;
        return image.length;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "ReportJasperImage{" +
                "bezeichnung='" + bezeichnung + '\'' +
                ", reportJasper=" + reportJasper +
                ", mimeType='" + mimeType + '\'' +
                ", size=" + size +
                ", id=" + id +
                '}';
    }
}