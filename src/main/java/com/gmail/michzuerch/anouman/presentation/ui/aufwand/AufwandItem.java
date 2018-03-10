package com.gmail.michzuerch.anouman.presentation.ui.aufwand;

import com.gmail.michzuerch.anouman.backend.entity.Aufwand;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.AufwandDeltaspikeFacade;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.icons.VaadinIcons;
import org.vaadin.addon.calendar.item.EditableCalendarItem;

import javax.inject.Inject;
import java.time.ZonedDateTime;

@ViewScoped
public class AufwandItem implements EditableCalendarItem {

    @Inject
    AufwandDeltaspikeFacade aufwandDeltaspikeFacade;

    private Aufwand aufwand;

    public AufwandItem() {
        aufwand = aufwandDeltaspikeFacade.findAll().get(0);
    }

    public AufwandItem(Aufwand val) {
        aufwand = val;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AufwandItem)) {
            return false;
        }
        AufwandItem that = (AufwandItem) o;
        return getAufwand().equals(that.getAufwand());
    }


    public Aufwand getAufwand() {
        return aufwand;
    }

    @Override
    public ZonedDateTime getStart() {
        return null;
    }

    @Override
    public void setStart(ZonedDateTime start) {
        getAufwand().setStart(start.toLocalDateTime());
    }

    @Override
    public ZonedDateTime getEnd() {
        return null;
    }

    @Override
    public void setEnd(ZonedDateTime end) {
        getAufwand().setEnd(end.toLocalDateTime());
    }

    @Override
    public String getCaption() {
        return null;
    }

    @Override
    public void setCaption(String caption) {
        getAufwand().getTitel();
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void setDescription(String description) {

    }

    @Override
    public String getStyleName() {
        return "state-" + getAufwand().getBezeichnung().toLowerCase();
    }


//    @Override
//    public boolean isClickable() {
//        return aufwand.isEditable();
//    }

    @Override
    public void setStyleName(String styleName) {

    }

    @Override
    public int hashCode() {
        return getAufwand().hashCode();
    }

    @Override
    public boolean isAllDay() {
        return false;
        //return aufwand.isLongTimeEvent();
    }

    @Override
    public void setAllDay(boolean isAllDay) {

    }

    @Override
    public boolean isMoveable() {
        return getAufwand().isMoveable();
        //return aufwand.isEditable();
    }

    @Override
    public boolean isResizeable() {
        return getAufwand().isResizable();
        //return aufwand.isEditable();
    }

    @Override
    public ItemChangeNotifier getNotifier() {
        return null;
    }

    @Override
    public String getDateCaptionFormat() {
        //return CalendarItem.RANGE_TIME;
        return VaadinIcons.CLOCK.getHtml() + " %s<br>" +
                VaadinIcons.ARROW_CIRCLE_RIGHT_O.getHtml() + " %s";
    }

}

