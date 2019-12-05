package com.gmail.michzuerch.anouman.ui.views.storefront.events;

import com.gmail.michzuerch.anouman.ui.views.orderedit.OrderItemsEditor;
import com.vaadin.flow.component.ComponentEvent;

public class TotalPriceChangeEvent extends ComponentEvent<OrderItemsEditor> {

    private static final long serialVersionUID = 1L;
    private final Integer totalPrice;

    public TotalPriceChangeEvent(OrderItemsEditor component, Integer totalPrice) {
        super(component, false);
        this.totalPrice = totalPrice;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

}