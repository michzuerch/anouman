package com.gmail.michzuerch.anouman.backend.data.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity(name = "Effort")
public class Effort extends AbstractEntity {
    @NotNull
    @Size(min = 3)
    private String title;

    @NotNull
    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private boolean resizeable;

    private boolean movable;

    @ManyToOne
    private Invoice invoice;

    private Effort(Builder builder) {
        setTitle(builder.title);
        setDescription(builder.description);
        setStartTime(builder.startTime);
        setEndTime(builder.endTime);
        setResizeable(builder.resizeable);
        setMovable(builder.movable);
        setInvoice(builder.invoice);
    }

    public Effort() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public boolean isResizeable() {
        return resizeable;
    }

    public void setResizeable(boolean resizeable) {
        this.resizeable = resizeable;
    }

    public boolean isMovable() {
        return movable;
    }

    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public static final class Builder {
        private @NotNull @Size(min = 3) String title;
        private @NotNull String description;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private boolean resizeable;
        private boolean movable;
        private Invoice invoice;

        public Builder() {
        }

        public Builder title(@NotNull @Size(min = 3) String val) {
            title = val;
            return this;
        }

        public Builder description(@NotNull String val) {
            description = val;
            return this;
        }

        public Builder startTime(LocalDateTime val) {
            startTime = val;
            return this;
        }

        public Builder endTime(LocalDateTime val) {
            endTime = val;
            return this;
        }

        public Builder resizeable(boolean val) {
            resizeable = val;
            return this;
        }

        public Builder movable(boolean val) {
            movable = val;
            return this;
        }

        public Builder invoice(Invoice val) {
            invoice = val;
            return this;
        }

        public Effort build() {
            return new Effort(this);
        }
    }
}
