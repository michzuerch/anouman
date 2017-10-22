package ch.internettechnik.anouman.backend.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ReportCSS extends AbstractEntity {
    @Column
    @NotNull
    private String bezeichnung;

    @OneToMany(mappedBy = "reportCSS", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportCSSElement> elements = new ArrayList<>();

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public List<ReportCSSElement> getElements() {
        return elements;
    }

    public void setElements(List<ReportCSSElement> elements) {
        this.elements = elements;
    }
}
