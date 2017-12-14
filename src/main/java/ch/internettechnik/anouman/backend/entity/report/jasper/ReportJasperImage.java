package ch.internettechnik.anouman.backend.entity.report.jasper;

import ch.internettechnik.anouman.backend.entity.AbstractEntity;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
public class ReportJasperImage extends AbstractEntity {

    @Column
    private String bezeichnung;

    @ManyToOne
    private ReportJasper reportJasper;

    @Column
    @Basic(fetch = FetchType.LAZY)
    @NotEmpty
    private byte[] image;

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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getSize() {
        return image.length;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
