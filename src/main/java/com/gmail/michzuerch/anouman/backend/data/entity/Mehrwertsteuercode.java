package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity(name = "Mehrwertsteuercode")
public class Mehrwertsteuercode extends AbstractEntity {
    private String description;

    private BigDecimal percentage;

    private boolean selling;

    @ManyToOne
    private Bookkeeping templateBookkeeping;

    private Mehrwertsteuercode(Builder builder) {
        setDescription(builder.description);
        setPercentage(builder.percentage);
        setSelling(builder.selling);
        setTemplateBookkeeping(builder.templateBookkeeping);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public boolean isSelling() {
        return selling;
    }

    public void setSelling(boolean selling) {
        this.selling = selling;
    }

    public Bookkeeping getTemplateBookkeeping() {
        return templateBookkeeping;
    }

    public void setTemplateBookkeeping(Bookkeeping templateBookkeeping) {
        this.templateBookkeeping = templateBookkeeping;
    }

    public static final class Builder {
        private String description;
        private BigDecimal percentage;
        private boolean selling;
        private Bookkeeping templateBookkeeping;

        public Builder() {
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder percentage(BigDecimal val) {
            percentage = val;
            return this;
        }

        public Builder selling(boolean val) {
            selling = val;
            return this;
        }

        public Builder templateBookkeeping(Bookkeeping val) {
            templateBookkeeping = val;
            return this;
        }

        public Mehrwertsteuercode build() {
            return new Mehrwertsteuercode(this);
        }
    }
}
