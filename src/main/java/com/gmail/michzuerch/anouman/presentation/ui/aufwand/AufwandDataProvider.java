package com.gmail.michzuerch.anouman.presentation.ui.aufwand;

import org.vaadin.addon.calendar.item.BasicItemProvider;

import java.time.ZonedDateTime;
import java.util.List;

public class AufwandDataProvider extends BasicItemProvider<AufwandItem> {
    public AufwandDataProvider() {
        super();
    }

    @Override
    //@todo Query für Datum???
    public List<AufwandItem> getItems(ZonedDateTime startDate, ZonedDateTime endDate) {
        return super.getItems(startDate, endDate);
    }

    void removeAllEvents() {
        this.itemList.clear();
        fireItemSetChanged();
    }
}
