package com.gmail.michzuerch.anouman.presentation.ui.testviews.editortest;

import com.gmail.michzuerch.anouman.backend.entity.EditorTest;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.EditorTestDeltaspikeFacade;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Binder;
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

@PreserveOnRefresh
@CDIView("EditorTestGridEditView")
public class EditorTestGridEditView extends VerticalLayout implements View {
    private static final Logger LOGGER = Logger.getLogger(EditorTestGridEditView.class.getName());

    @Inject
    EditorTestDeltaspikeFacade facade;

    TextField filterText = new TextField();
    Grid<EditorTest> grid = new Grid<>();

    @Inject
    private EditorTestForm form;

    private TextField ersterFld = new TextField();
    private TextField zweiterFld = new TextField();

    private void createContent() {
        filterText.setPlaceholder("Filter für Erster...");
        filterText.addValueChangeListener(e -> updateList());
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        grid.addColumn(EditorTest::getId).setCaption("id");
        grid.addColumn(EditorTest::getVersion).setCaption("Version");
        grid.addColumn(EditorTest::getErster).setEditorComponent(ersterFld, EditorTest::setErster);
        grid.addColumn(EditorTest::getZweiter).setEditorComponent(zweiterFld, EditorTest::setZweiter);
        Binder<EditorTest> binder = grid.getEditor().getBinder();

        binder.bind(ersterFld, EditorTest::getErster, EditorTest::setErster);
        binder.bind(zweiterFld, EditorTest::getZweiter, EditorTest::setZweiter);

        grid.getEditor().setBinder(binder);
        grid.getEditor().setEnabled(true);
        grid.getEditor().addSaveListener(event -> {
            if (binder.isValid()) {
                facade.save(event.getBean());
            }
            updateList();
            grid.select(event.getBean());
        });

        Button clearFilterTextBtn = new Button(VaadinIcons.RECYCLE);
        clearFilterTextBtn.setDescription("Entferne Filter");
        clearFilterTextBtn.addClickListener(e -> filterText.clear());

        Button addBtn = new Button(VaadinIcons.PLUS);
        addBtn.addClickListener(event -> {
            grid.asSingleSelect().clear();
            form.setEntity(new EditorTest());
            form.openInModalPopup();
            form.setSavedHandler(val -> {
                facade.save(val);
                updateList();
                grid.select(val);
                form.closePopup();
            });
        });

        CssLayout tools = new CssLayout();
        tools.addComponents(filterText, clearFilterTextBtn, addBtn);
        tools.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        grid.setSizeFull();

        // Render a button that deletes the data row (item)
        grid.addColumn(editorTest -> "löschen",
                new ButtonRenderer(event -> {
                    Notification.show("Lösche EditorTest id:" + event.getItem(), Notification.Type.HUMANIZED_MESSAGE);
                    facade.delete((EditorTest) event.getItem());
                    updateList();
                })
        ).setCaption("Löschen");

        grid.setSizeFull();
        setMargin(false);
        setSpacing(false);
        addComponents(tools, grid);
        setExpandRatio(grid, 1);
        setSizeFull();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        createContent();
        updateList();
    }

    public void updateList() {
        List<EditorTest> items;
        if (!filterText.isEmpty()) {
            items = facade.findAll();
        } else {
            items = facade.findAll();
        }
        grid.setItems(items);
    }
}
