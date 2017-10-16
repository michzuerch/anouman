package ch.internettechnik.anouman.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;

/**
 * Created by michzuerch on 10.06.15.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "ReportTemplate.findAll", query = "SELECT rt FROM ReportTemplate rt"),
        @NamedQuery(name = "ReportTemplate.findById", query = "SELECT rt FROM ReportTemplate rt where rt.id = :id")
})
public class ReportTemplate extends AbstractEntity {
    @Column
    @NotNull
    @Size(min = 1, max = 50)
    private String bezeichnung;

    @Column
    @Lob
    @Basic
    private byte[] template;

    @Column
    private String filename;

    @Transient
    private int size;

    public int getSize() {
        return template.length;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public byte[] getTemplate() {
        return template;
    }

    public void setTemplate(byte[] template) {
        this.template = template;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        return "ReportTemplate{" +
                "bezeichnung='" + bezeichnung + '\'' +
                ", template=" + Arrays.toString(template) +
                ", filename='" + filename + '\'' +
                ", id=" + id +
                '}';
    }
}
