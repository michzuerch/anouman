package ch.internettechnik.anouman.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class ReportCSSElement extends AbstractEntity {
    @Column
    @NotNull
    private String bezeichnung;

    @Column
    @NotNull
    private String type;

    @ManyToOne
    private ReportCSS reportCSS;

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ReportCSS getReportCSS() {
        return reportCSS;
    }

    public void setReportCSS(ReportCSS reportCSS) {
        this.reportCSS = reportCSS;
    }
}
