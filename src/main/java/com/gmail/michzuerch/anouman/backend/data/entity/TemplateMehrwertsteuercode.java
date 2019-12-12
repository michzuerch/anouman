package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "TemplateMehrwertsteuercode")
public class TemplateMehrwertsteuercode extends AbstractEntity {
    @ManyToOne
    private TemplateBookkeeping templateBookkeeping;

    private TemplateMehrwertsteuercode(Builder builder) {
        setTemplateBookkeeping(builder.templateBookkeeping);
    }

    public TemplateBookkeeping getTemplateBookkeeping() {
        return templateBookkeeping;
    }

    public void setTemplateBookkeeping(TemplateBookkeeping templateBookkeeping) {
        this.templateBookkeeping = templateBookkeeping;
    }


    public static final class Builder {
        private TemplateBookkeeping templateBookkeeping;

        public Builder() {
        }

        public Builder templateBookkeeping(TemplateBookkeeping val) {
            templateBookkeeping = val;
            return this;
        }

        public TemplateMehrwertsteuercode build() {
            return new TemplateMehrwertsteuercode(this);
        }
    }
}
