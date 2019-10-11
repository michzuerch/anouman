package com.gmail.michael.ui.views.storefront.events;

import com.vaadin.flow.component.ComponentEvent;
import com.gmail.michael.ui.views.orderedit.OrderItemEditor;

public class DeleteEvent extends ComponentEvent<OrderItemEditor> {
	public DeleteEvent(OrderItemEditor component) {
		super(component, false);
	}
}