package ch.internettechnik.anouman.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by michzuerch on 13.01.16.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "EditorTest.findAll", query = "SELECT e FROM EditorTest e"),
        @NamedQuery(name = "EditorTest.findById", query = "SELECT e FROM EditorTest e where e.id = :id"),
        @NamedQuery(name = "EditorTest.findByErsterLike", query = "SELECT e FROM EditorTest e where lower(e.erster) LIKE :erster")
})
public class EditorTest extends AbstractEntity {
    @Column
    @NotNull
    @Size(min = 3, message = "Eingabe muss mindenstens 3 Zeichen lang sein")
    private String erster;

    @Column
    private String zweiter;

    public EditorTest(String erster, String zweiter) {
        this.erster = erster;
        this.zweiter = zweiter;
    }

    public EditorTest() {
    }


    public String getErster() {
        return erster;
    }

    public void setErster(String erster) {
        this.erster = erster;
    }

    public String getZweiter() {
        return zweiter;
    }

    public void setZweiter(String zweiter) {
        this.zweiter = zweiter;
    }
}
