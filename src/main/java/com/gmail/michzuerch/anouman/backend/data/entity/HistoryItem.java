package com.gmail.michzuerch.anouman.backend.data.entity;

import com.gmail.michzuerch.anouman.backend.data.OrderState;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
public class HistoryItem extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    private OrderState newState;

    @NotBlank
    @Size(max = 255)
    private String message;

    @NotNull
    private LocalDateTime timestamp;
    @ManyToOne
    @NotNull
    private User createdBy;

    HistoryItem() {
        // Empty constructor is needed by Spring Data / JPA
    }

    public HistoryItem(User createdBy, String message) {
        this.createdBy = createdBy;
        this.message = message;
        timestamp = LocalDateTime.now();
    }

    public OrderState getNewState() {
        return newState;
    }

    public void setNewState(OrderState newState) {
        this.newState = newState;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

}
