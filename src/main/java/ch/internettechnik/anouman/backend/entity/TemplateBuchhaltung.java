package ch.internettechnik.anouman.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "TemplateBuchhaltung.findAll", query = "SELECT b FROM TemplateBuchhaltung b"),
        @NamedQuery(name = "TemplateBuchhaltung.findById", query = "SELECT b FROM TemplateBuchhaltung b where b.id = :id")

})
public class TemplateBuchhaltung extends AbstractEntity {
    @Column
    @NotNull
    private String bezeichnung;

    @OneToMany(mappedBy = "templateBuchhaltung", cascade = CascadeType.ALL)
    private List<TemplateKontoklasse> templateKontoklasses = new ArrayList<>();

    @OneToMany(mappedBy = "templateBuchhaltung", cascade = CascadeType.ALL)
    private List<TemplateMehrwertsteuercode> templateMehrwertsteuercodes = new ArrayList<>();

    public TemplateBuchhaltung() {
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public List<TemplateKontoklasse> getTemplateKontoklasses() {
        return templateKontoklasses;
    }

    public void setTemplateKontoklasses(List<TemplateKontoklasse> templateKontoklasses) {
        this.templateKontoklasses = templateKontoklasses;
    }

    public List<TemplateMehrwertsteuercode> getTemplateMehrwertsteuercodes() {
        return templateMehrwertsteuercodes;
    }

    public void setTemplateMehrwertsteuercodes(List<TemplateMehrwertsteuercode> templateMehrwertsteuercodes) {
        this.templateMehrwertsteuercodes = templateMehrwertsteuercodes;
    }

    @Override
    public String toString() {
        return "TemplateBuchhaltung{" +
                ", id=" + id +
                "bezeichnung='" + bezeichnung + '\'' +
                '}';
    }
}
