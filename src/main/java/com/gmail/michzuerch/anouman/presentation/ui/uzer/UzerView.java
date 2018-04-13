package com.gmail.michzuerch.anouman.presentation.ui.uzer;

import com.gmail.michzuerch.anouman.backend.entity.Uzer;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.UzerDeltaspikeFacade;
import com.vaadin.cdi.CDIView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;

import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;

@CDIView("UzerView")
public class UzerView extends VerticalLayout implements View {
    private static final Logger LOGGER = Logger.getLogger(UzerView.class.getName());

    TextField filterText = new TextField();
    Grid<Uzer> grid = new Grid<>(Uzer.class);

    @Inject
    private UzerDeltaspikeFacade service;

    @Inject
    private UzerForm form;

    private Component createContent() {
        HorizontalLayout layout = new HorizontalLayout();

        filterText.setPlaceholder("Filter für Erster...");
        filterText.addValueChangeListener(e -> updateList());
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        Button clearFilterTextBtn = new Button(VaadinIcons.RECYCLE);
        clearFilterTextBtn.setDescription("Entferne Filter");
        clearFilterTextBtn.addClickListener(e -> filterText.clear());

        Button addBtn = new Button(VaadinIcons.PLUS);
        addBtn.addClickListener(event -> {
            grid.asSingleSelect().clear();
            form.setEntity(new Uzer());
            form.openInModalPopup();
            form.setSavedHandler(val -> {
                service.save(val);
                updateList();
                grid.select(val);
                form.closePopup();
            });
        });

        CssLayout tools = new CssLayout();
        tools.addComponents(filterText, clearFilterTextBtn, addBtn);
        tools.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        grid.setColumns("id", "principal", "pazzword", "description", "anzahlUzerRoles");
        grid.setSizeFull();

        // Render a button that deletes the data row (item)
        grid.addColumn(rechnung -> "löschen",
                new ButtonRenderer(event -> {
                    Notification.show("Lösche User id:" + event.getItem(), Notification.Type.HUMANIZED_MESSAGE);
                    service.delete((Uzer) event.getItem());
                    updateList();
                })
        );

        grid.addColumn(rechnung -> "ändern",
                new ButtonRenderer(event -> {
                    form.setEntity((Uzer) event.getItem());
                    form.openInModalPopup();
                    form.setSavedHandler(val -> {
                        service.save(val);
                        updateList();
                        grid.select(val);
                        form.closePopup();
                    });
                    form.setResetHandler(val -> {
                        updateList();
                        grid.select(val);
                        form.closePopup();
                    });
                }));
        layout.addComponents(tools, grid);
        layout.setSizeFull();
        return layout;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        addComponent(createContent());
        setSizeFull();
        updateList();
    }

    public void updateList() {
        List<Uzer> items;
        /*
        if (!filterText.isEmpty()) {
            items = service.findByErsterLike(filterText.getValue().toLowerCase());
        } else {
            items = service.findAll();
        }
        */
        items = service.findAll();
        grid.setItems(items);
    }

}
