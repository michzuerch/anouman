package com.gmail.michzuerch.anouman.presentation.ui.aufwand;

import com.gmail.michzuerch.anouman.backend.entity.Aufwand;
import com.gmail.michzuerch.anouman.backend.entity.Rechnung;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.AufwandDeltaspikeFacade;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.RechnungDeltaspikeFacade;
import com.vaadin.cdi.CDIView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

@CDIView("AufwandCalendarView")
public class AufwandCalendarView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(AufwandCalendarView.class.getName());

    TextField filterTextTitel = new TextField();
    ComboBox<Rechnung> filterRechnung = new ComboBox<Rechnung>();
    AufwandCalendar aufwandCalendar = new AufwandCalendar();

    @Inject
    private AufwandDeltaspikeFacade aufwandDeltaspikeFacade;

    @Inject
    private RechnungDeltaspikeFacade rechnungDeltaspikeFacade;

    @Inject
    private AufwandForm form;

    private void createContent() {
        filterTextTitel.setPlaceholder("Filter für Titel");
        filterTextTitel.addValueChangeListener(e -> updateList());
        filterTextTitel.setValueChangeMode(ValueChangeMode.LAZY);
        filterRechnung.setPlaceholder("Filter für Rechnung");
        filterRechnung.setItems(rechnungDeltaspikeFacade.findAll());
        filterRechnung.setItemCaptionGenerator(item -> item.getBezeichnung() + " " + item.getAdresse().getFirma() + " " + item.getAdresse().getOrt() + " id:" + item.getId());
        filterRechnung.addValueChangeListener(valueChangeEvent -> updateList());
        Button clearFilterTextBtn = new Button(VaadinIcons.RECYCLE);
        clearFilterTextBtn.setDescription("Entferne Filter");
        clearFilterTextBtn.addClickListener(e -> {
            filterTextTitel.clear();
            filterRechnung.clear();
        });

        Button addBtn = new Button(VaadinIcons.PLUS);
        addBtn.addClickListener(event -> {
            //grid.asSingleSelect().clear();
            Aufwand aufwand = new Aufwand();
            aufwand.setRechnung(rechnungDeltaspikeFacade.findAll().get(0));
            if (!filterRechnung.isEmpty()) aufwand.setRechnung(filterRechnung.getValue());
            form.setEntity(aufwand);
            form.openInModalPopup();
            form.setSavedHandler(aufwand1 -> {
                aufwandDeltaspikeFacade.save(aufwand1);
                updateList();
                //grid.select(aufwand1);
                form.closePopup();
            });
        });

        CssLayout tools = new CssLayout();
        tools.addComponents(filterRechnung, filterTextTitel, clearFilterTextBtn, addBtn);
        tools.setWidth(50, Unit.PERCENTAGE);
        tools.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        aufwandCalendar.setSizeFull();
        setMargin(false);
        setSpacing(false);
        addComponents(tools, aufwandCalendar);
        setExpandRatio(aufwandCalendar, 1);
        setSizeFull();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        createContent();
        updateList();
    }

    public void updateList() {
//
//        if ((!filterRechnung.isEmpty()) && (!filterTextTitel.isEmpty())) {
//            // Such mit Rechnung und Titel
//            logger.debug("Suche mit Rechnung und Bezeichnung:" + filterRechnung.getValue().getId() + "," + filterTextBezeichnung.getValue());
//            grid.setItems(rechnungspositionDeltaspikeFacade.findByRechnungAndBezeichnungLikeIgnoreCase(filterRechnung.getValue(), filterTextBezeichnung.getValue() + "%"));
//            return;
//        } else if ((!filterRechnung.isEmpty()) && (filterTextTitel.isEmpty())) {
//            // Suche mit Rechnung
//            logger.debug("Suche mit Rechnung:" + filterRechnung.getValue().getId());
//            grid.setItems(rechnungspositionDeltaspikeFacade.findByRechnung(filterRechnung.getValue()));
//            return;
//        } else if ((filterRechnung.isEmpty()) && (!filterTextTitel.isEmpty())) {
//            // Suche mit Bezeichnung
//            logger.debug("Suche mit Bezeichnung:" + filterTextTitel.getValue());
//            grid.setItems(rechnungspositionDeltaspikeFacade.findByBezeichnungLikeIgnoreCase(filterTextTitel.getValue() + "%"));
//            return;
//        }
//        grid.setItems(rechnungspositionDeltaspikeFacade.findAll());
//
    }

}