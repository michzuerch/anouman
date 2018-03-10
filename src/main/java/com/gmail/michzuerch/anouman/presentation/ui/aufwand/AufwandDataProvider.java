package com.gmail.michzuerch.anouman.presentation.ui.aufwand;

import org.vaadin.addon.calendar.item.CalendarEditableItemProvider;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

public class AufwandDataProvider implements CalendarEditableItemProvider<AufwandItem> {
    public AufwandDataProvider() {
        super();
    }

    @Override
    public void addItem(AufwandItem item) {

    }

    @Override
    public void removeItem(AufwandItem item) {

    }

    @Override
    public void setItems(Collection<AufwandItem> items) {

    }

    @Override
    public List<AufwandItem> getItems(ZonedDateTime startDate, ZonedDateTime endDate) {
        return null;
    }
}
