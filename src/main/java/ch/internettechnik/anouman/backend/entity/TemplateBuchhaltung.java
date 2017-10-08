package ch.internettechnik.anouman.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(name = "TemplateBuchhaltung.findAll", query = "SELECT b FROM TemplateBuchhaltung b"),
        @NamedQuery(name = "TemplateBuchhaltung.findById", query = "SELECT b FROM TemplateBuchhaltung b where b.id = :id")

})
public class TemplateBuchhaltung extends AbstractEntity {
    @Column
    @NotNull
    private String bezeichnung;

    @OneToMany(mappedBy = "templateBuchhaltung")
    private Set<TemplateKontoklasse> templateKontoklasses = new HashSet<>();

    @OneToMany(mappedBy = "templateBuchhaltung")
    private Set<TemplateMehrwertsteuercode> templateMehrwertsteuercodes = new HashSet<>();

    public TemplateBuchhaltung() {
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public Set<TemplateKontoklasse> getTemplateKontoklasses() {
        return templateKontoklasses;
    }

    public void setTemplateKontoklasses(Set<TemplateKontoklasse> templateKontoklasses) {
        this.templateKontoklasses = templateKontoklasses;
    }

    public Set<TemplateMehrwertsteuercode> getTemplateMehrwertsteuercodes() {
        return templateMehrwertsteuercodes;
    }

    public void setTemplateMehrwertsteuercodes(Set<TemplateMehrwertsteuercode> templateMehrwertsteuercodes) {
        this.templateMehrwertsteuercodes = templateMehrwertsteuercodes;
    }

    @Override
    public String toString() {
        return "TemplateBuchhaltung{" +
                "bezeichnung='" + bezeichnung + '\'' +
                ", templateKontoklasses=" + templateKontoklasses +
                ", templateMehrwertsteuercodes=" + templateMehrwertsteuercodes +
                '}';
    }
}
