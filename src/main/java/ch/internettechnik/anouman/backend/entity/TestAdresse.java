package ch.internettechnik.anouman.backend.entity;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@NamedQueries({
        @NamedQuery(name = "TestAdresse.findAll", query = "SELECT a FROM TestAdresse a"),
        @NamedQuery(name = "TestAdresse.findById", query = "SELECT a FROM TestAdresse a where a.id = :id"),
})
public class TestAdresse extends AbstractEntity {
    @Column
    @NotNull
    @NotBlank(message = "Nachname muss eingegeben werden.")
    @Pattern(regexp = "[a-z-A-Z]*", message = "Nachname enthält ungültige Zeichen")
    private String nachname;
    @Column
    private String vorname;
    @Column
    private String ort;

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String name) {
        this.nachname = name;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }
}
