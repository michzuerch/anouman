package com.gmail.michzuerch.anouman.ui.views.storefront.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;

public class EditEvent extends ComponentEvent<Component> {

	private static final long serialVersionUID = 1L;

	public EditEvent(Component source) {
		super(source, false);
	}
}
