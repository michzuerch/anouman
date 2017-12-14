package ch.internettechnik.anouman.backend.entity.report.css;

import ch.internettechnik.anouman.backend.entity.AbstractEntity;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
public class ReportCSSImage extends AbstractEntity {

    @Column
    private String bezeichnung;

    @ManyToOne
    private ReportCSS reportCSS;

    @Column
    @Basic(fetch = FetchType.LAZY)
    @NotEmpty
    private byte[] image;


    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public ReportCSS getReportCSS() {
        return reportCSS;
    }

    public void setReportCSS(ReportCSS reportCSS) {
        this.reportCSS = reportCSS;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
