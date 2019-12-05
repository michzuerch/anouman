package com.gmail.michzuerch.anouman.ui.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;

public class ValidationFailedEvent extends ComponentEvent<Component> {

    private static final long serialVersionUID = 1L;

    public ValidationFailedEvent(Component source) {
        super(source, false);
    }

}