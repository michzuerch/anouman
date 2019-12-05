package com.gmail.michzuerch.anouman.ui.views.storefront.events;

import com.gmail.michzuerch.anouman.ui.views.orderedit.OrderItemEditor;
import com.vaadin.flow.component.ComponentEvent;

public class PriceChangeEvent extends ComponentEvent<OrderItemEditor> {

    private static final long serialVersionUID = 1L;

    private final int oldValue;

    private final int newValue;

    public PriceChangeEvent(OrderItemEditor component, int oldValue, int newValue) {
        super(component, false);
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public int getOldValue() {
        return oldValue;
    }

    public int getNewValue() {
        return newValue;
    }

}