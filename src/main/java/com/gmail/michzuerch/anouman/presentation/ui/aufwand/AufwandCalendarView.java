package com.gmail.michzuerch.anouman.presentation.ui.aufwand;

import com.gmail.michzuerch.anouman.backend.entity.Rechnung;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.AufwandDeltaspikeFacade;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.RechnungDeltaspikeFacade;
import com.gmail.michzuerch.anouman.presentation.ui.Menu;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

@CDIView("AufwandCalendarView")
public class AufwandCalendarView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(AufwandCalendarView.class.getName());

    TextField filterTextTitel = new TextField();
    ComboBox<Rechnung> filterRechnung = new ComboBox<Rechnung>();
    AufwandCalendar aufwandCalendar = new AufwandCalendar();

    @Inject
    private Menu menu;

    @Inject
    private AufwandDeltaspikeFacade facade;

    @Inject
    private RechnungDeltaspikeFacade rechnungDeltaspikeFacade;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        setStyleName("anouman-background");
        addComponents(menu, filterRechnung);

        addComponentsAndExpand(aufwandCalendar);

    }
}