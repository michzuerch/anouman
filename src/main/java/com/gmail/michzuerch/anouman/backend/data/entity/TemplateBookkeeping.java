package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "TemplateBookkeeping")
public class TemplateBookkeeping extends AbstractEntity {
    @NotNull
    private String description;

    @NotNull
    @Digits(integer = 4, fraction = 0, message = "Ungültiges Zahlenformat")
    @DecimalMin(value = "1950", message = "Nicht vor 1950")
    @DecimalMax(value = "2150", message = "Nicht nach 2150")
    private int year;

    @OneToMany(mappedBy = "templateBookkeeping", cascade = CascadeType.ALL)
    private List<TemplateKontoklasse> templateKontoklasses = new ArrayList<>();

    @OneToMany(mappedBy = "templateBookkeeping", cascade = CascadeType.ALL)
    private List<TemplateMehrwertsteuercode> templateMehrwertsteuercodes = new ArrayList<>();

    private TemplateBookkeeping(Builder builder) {
        setDescription(builder.description);
        setYear(builder.year);
        setTemplateKontoklasses(builder.templateKontoklasses);
        setTemplateMehrwertsteuercodes(builder.templateMehrwertsteuercodes);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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

    public static final class Builder {
        private @NotNull String description;
        private @NotNull @Digits(integer = 4, fraction = 0, message = "Ungültiges Zahlenformat") @DecimalMin(value = "1950", message = "Nicht vor 1950") @DecimalMax(value = "2150", message = "Nicht nach 2150") int year;
        private List<TemplateKontoklasse> templateKontoklasses;
        private List<TemplateMehrwertsteuercode> templateMehrwertsteuercodes;

        public Builder() {
        }

        public Builder description(@NotNull String val) {
            description = val;
            return this;
        }

        public Builder year(@NotNull @Digits(integer = 4, fraction = 0, message = "Ungültiges Zahlenformat") @DecimalMin(value = "1950", message = "Nicht vor 1950") @DecimalMax(value = "2150", message = "Nicht nach 2150") int val) {
            year = val;
            return this;
        }

        public Builder templateKontoklasses(List<TemplateKontoklasse> val) {
            templateKontoklasses = val;
            return this;
        }

        public Builder templateMehrwertsteuercodes(List<TemplateMehrwertsteuercode> val) {
            templateMehrwertsteuercodes = val;
            return this;
        }

        public TemplateBookkeeping build() {
            return new TemplateBookkeeping(this);
        }
    }
}
