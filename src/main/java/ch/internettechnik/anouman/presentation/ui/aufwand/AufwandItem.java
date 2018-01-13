package ch.internettechnik.anouman.presentation.ui.aufwand;

import ch.internettechnik.anouman.backend.entity.Aufwand;
import com.vaadin.icons.VaadinIcons;
import org.vaadin.addon.calendar.item.BasicItem;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class AufwandItem extends BasicItem {


    private final Aufwand aufwand;

    /**
     * constructor
     *
     * @param aufwand A aufwand
     */

    public AufwandItem(Aufwand aufwand) {
        super(aufwand.getTitel(), aufwand.getBezeichnung(),
                aufwand.getStart().atZone(ZoneId.systemDefault()),
                aufwand.getEnd().atZone(ZoneId.systemDefault()));
        this.aufwand = aufwand;
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
    public String getStyleName() {
        return "state-" + aufwand.getBezeichnung().toLowerCase();
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
    public boolean isMoveable() {
        return false;
        //return aufwand.isEditable();
    }

    @Override
    public boolean isResizeable() {
        return false;
        //return aufwand.isEditable();
    }

//    @Override
//    public boolean isClickable() {
//        return aufwand.isEditable();
//    }

    @Override
    public void setEnd(ZonedDateTime end) {
        aufwand.setEnd(end.toLocalDateTime());
        super.setEnd(end);
    }

    @Override
    public void setStart(ZonedDateTime start) {
        aufwand.setStart(start.toLocalDateTime());
        super.setStart(start);
    }

    @Override
    public String getDateCaptionFormat() {
        //return CalendarItem.RANGE_TIME;
        return VaadinIcons.CLOCK.getHtml() + " %s<br>" +
                VaadinIcons.ARROW_CIRCLE_RIGHT_O.getHtml() + " %s";
    }

}

