package ch.internettechnik.anouman.backend.entity.report.jasper;

import ch.internettechnik.anouman.backend.entity.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;

/**
 * Created by michzuerch on 10.06.15.
 */
@Entity
public class ReportJasper extends AbstractEntity {
    @Column
    @NotNull
    @Size(min = 1, max = 50)
    private String bezeichnung;

    @Column
    @Lob
    @Basic
    private byte[] templateSource;

    @Column
    @Lob
    @Basic
    private byte[] templateCompiled;

    @Column
    private String filename;

    @Transient
    private int sizeSource;

    @Transient
    private int sizeCompiled;

    public int getSizeSource() {
        if (templateSource == null) {
            return 0;
        } else {
            return templateSource.length;
        }
    }

    public void setSizeSource(int sizeSource) {
        this.sizeSource = sizeSource;
    }

    public int getSizeCompiled() {
        if (templateCompiled == null) {
            return 0;
        } else {
            return templateCompiled.length;
        }
    }

    public void setSizeCompiled(int sizeCompiled) {
        this.sizeCompiled = sizeCompiled;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public byte[] getTemplateSource() {
        return templateSource;
    }

    public void setTemplateSource(byte[] template) {
        this.templateSource = template;
    }

    public byte[] getTemplateCompiled() {
        return templateCompiled;
    }

    public void setTemplateCompiled(byte[] templateCompiled) {
        this.templateCompiled = templateCompiled;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        return "ReportJasper{" +
                "bezeichnung='" + bezeichnung + '\'' +
                ", templateSource=" + Arrays.toString(templateSource) +
                ", filename='" + filename + '\'' +
                ", id=" + id +
                '}';
    }
}
