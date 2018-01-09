package ch.internettechnik.anouman.presentation.ui.editortest;

import ch.internettechnik.anouman.backend.entity.EditorTest;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.EditorTestDeltaspikeFacade;
import ch.internettechnik.anouman.presentation.ui.Menu;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;

import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;

@UIScope
@SpringView(name = "EditorTestViewView")
public class EditorTestView extends VerticalLayout implements View {
    private static final Logger LOGGER = Logger.getLogger(EditorTestView.class.getName());

    TextField filterText = new TextField();
    Grid<EditorTest> grid = new Grid<>(EditorTest.class);

    @Inject
    private Menu menu;

    @Inject
    private EditorTestDeltaspikeFacade editorTestDeltaspikeFacade;

    @Inject
    private EditorTestForm form;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        setStyleName("anouman-background");

        filterText.setPlaceholder("Filter für Erster...");
        filterText.addValueChangeListener(e -> updateList());
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        Button clearFilterTextBtn = new Button(VaadinIcons.RECYCLE);
        clearFilterTextBtn.setDescription("Entferne Filter");
        clearFilterTextBtn.addClickListener(e -> filterText.clear());

        Button addBtn = new Button(VaadinIcons.PLUS);
        addBtn.addClickListener(event -> {
            grid.asSingleSelect().clear();
            form.setEntity(new EditorTest());
            form.openInModalPopup();
            form.setSavedHandler(val -> {
                editorTestDeltaspikeFacade.save(val);
                updateList();
                grid.select(val);
                form.closePopup();
            });
        });

        CssLayout tools = new CssLayout();
        tools.addComponents(filterText, clearFilterTextBtn, addBtn);
        tools.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        grid.setCaption("<h2>EditorTest</h2>");
        grid.setCaptionAsHtml(true);
        //grid.setColumns("id", "erster", "zweiter");
        grid.setSizeFull();

        // Render a button that deletes the data row (item)
        grid.addColumn(editorTest -> "löschen",
                new ButtonRenderer(event -> {
                    Notification.show("Lösche EditorTest id:" + event.getItem(), Notification.Type.HUMANIZED_MESSAGE);
                    editorTestDeltaspikeFacade.delete((EditorTest) event.getItem());
                    updateList();
                })
        );

        grid.addColumn(editorTest -> "ändern",
                new ButtonRenderer(event -> {
                    form.setEntity((EditorTest) event.getItem());
                    form.openInModalPopup();
                    form.setSavedHandler(val -> {
                        editorTestDeltaspikeFacade.save(val);
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


        updateList();
        addComponents(menu, tools);
        addComponentsAndExpand(grid);
    }

    public void updateList() {
        List<EditorTest> items;
        items = editorTestDeltaspikeFacade.findAll();
        grid.setItems(items);
    }

}
