package com.gmail.michzuerch.anouman.ui.views.storefront.events;

import com.gmail.michzuerch.anouman.ui.views.orderedit.OrderDetails;
import com.vaadin.flow.component.ComponentEvent;

public class CommentEvent extends ComponentEvent<OrderDetails> {

    private static final long serialVersionUID = 1L;
    private Long orderId;
    private String message;

    public CommentEvent(OrderDetails component, Long orderId, String message) {
        super(component, false);
        this.orderId = orderId;
        this.message = message;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getMessage() {
        return message;
    }
}