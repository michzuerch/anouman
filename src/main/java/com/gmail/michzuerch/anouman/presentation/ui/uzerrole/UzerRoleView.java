package com.gmail.michzuerch.anouman.presentation.ui.uzerrole;

import com.gmail.michzuerch.anouman.backend.entity.Uzer;
import com.gmail.michzuerch.anouman.backend.entity.UzerRole;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.UzerDeltaspikeFacade;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.UzerRoleDeltaspikeFacade;
import com.vaadin.cdi.CDIView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@CDIView("UzerRoleView")
public class UzerRoleView extends VerticalLayout implements View {
    private static final Logger LOGGER = Logger.getLogger(UzerRoleView.class.getName());

    TextField filterText = new TextField();
    Grid<UzerRole> grid = new Grid<>(UzerRole.class);

    @Inject
    private UzerRoleDeltaspikeFacade uzerRoleDeltaspikeFacade;

    @Inject
    private UzerDeltaspikeFacade uzerDeltaspikeFacade;

    @Inject
    private UzerRoleForm form;

    private ComboBox<Uzer> uzerComboBox;


    private Component createContent() {
        HorizontalLayout layout = new HorizontalLayout();

        filterText.setPlaceholder("Filter für Erster...");
        filterText.addValueChangeListener(e -> updateList());
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        Collection<Uzer> uzers = uzerDeltaspikeFacade.findAll();
        ComboBox<Uzer> uzerComboBox = new ComboBox<>("", uzers);
        uzerComboBox.setItemCaptionGenerator(Uzer::getPrincipal);
        uzerComboBox.setEmptySelectionAllowed(true);
        uzerComboBox.addValueChangeListener(valueChangeEvent -> updateList());

        Button clearFilterTextBtn = new Button(VaadinIcons.RECYCLE);
        clearFilterTextBtn.setDescription("Entferne Filter");
        clearFilterTextBtn.addClickListener(e -> uzerComboBox.clear());

        Button addBtn = new Button(VaadinIcons.PLUS);
        addBtn.addClickListener(event -> {
            grid.asSingleSelect().clear();
            form.setEntity(new UzerRole());
            form.openInModalPopup();
            form.setSavedHandler(val -> {
                uzerRoleDeltaspikeFacade.save(val);
                updateList();
                grid.select(val);
                form.closePopup();
            });
        });

        CssLayout tools = new CssLayout();
        tools.addComponents(uzerComboBox, addBtn);
        //tools.addComponents(filterText, clearFilterTextBtn, addBtn);
        tools.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        grid.setColumns("id", "role", "roleGroup", "anzahlUzers");
        grid.setSizeFull();

        // Render a button that deletes the data row (item)
        grid.addColumn(val -> "löschen",
                new ButtonRenderer(event -> {
                    Notification.show("Lösche User Role id:" + event.getItem(), Notification.Type.HUMANIZED_MESSAGE);
                    uzerRoleDeltaspikeFacade.delete((UzerRole) event.getItem());
                    updateList();
                })
        );

        grid.addColumn(rechnung -> "ändern",
                new ButtonRenderer(event -> {
                    form.setEntity((UzerRole) event.getItem());
                    form.openInModalPopup();
                    form.setSavedHandler(val -> {
                        uzerRoleDeltaspikeFacade.save(val);
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
        List<UzerRole> items;

        if (uzerComboBox != null) {
            if (!uzerComboBox.isEmpty()) {
                items = (List<UzerRole>) uzerComboBox.getValue().getRoles();
            }
        }
        /*
        if (!filterText.isEmpty()) {
            items = uzerRoleDeltaspikeFacade.findByErsterLike(filterText.getValue().toLowerCase());
        } else {
            items = uzerRoleDeltaspikeFacade.findAll();
        }
        */
        items = uzerRoleDeltaspikeFacade.findAll();
        grid.setItems(items);
    }

}
