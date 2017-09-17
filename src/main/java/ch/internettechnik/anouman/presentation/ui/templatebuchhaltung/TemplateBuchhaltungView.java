package ch.internettechnik.anouman.presentation.ui.templatebuchhaltung;

import ch.internettechnik.anouman.backend.entity.TemplateBuchhaltung;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.TemplateBuchhaltungFacade;
import ch.internettechnik.anouman.presentation.ui.Menu;
import ch.internettechnik.anouman.presentation.ui.templatebuchhaltung.form.TemplateBuchhaltungForm;
import com.vaadin.cdi.CDIView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

@CDIView(value = "TemplateBuchhaltung")
public class TemplateBuchhaltungView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(TemplateBuchhaltungView.class.getName());

    TextField filterTextBezeichnung = new TextField();
    Grid<TemplateBuchhaltung> grid = new Grid<>();

    @Inject
    private Menu menu;

    @Inject
    private TemplateBuchhaltungFacade facade;

    @Inject
    private TemplateBuchhaltungForm form;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        filterTextBezeichnung.setPlaceholder("Filter für Bezeichnung...");
        filterTextBezeichnung.addValueChangeListener(e -> updateList());
        filterTextBezeichnung.setValueChangeMode(ValueChangeMode.LAZY);

        Button clearFilterTextBtn = new Button(VaadinIcons.RECYCLE);
        clearFilterTextBtn.setDescription("Entferne Filter");
        clearFilterTextBtn.addClickListener(e -> filterTextBezeichnung.clear());

        Button addBtn = new Button(VaadinIcons.PLUS);
        addBtn.addClickListener(event -> {
            grid.asSingleSelect().clear();
            form.setEntity(new TemplateBuchhaltung());
            form.openInModalPopup();
            form.setSavedHandler(val -> {
                facade.save(val);
                updateList();
                grid.select(val);
                form.closePopup();
            });
        });

        Button mehrwertsteuercodeBtn = new Button(VaadinIcons.CODE);
        mehrwertsteuercodeBtn.setCaption("Mehrwertsteuercodes dieser Template-Buchhaltung");



        CssLayout tools = new CssLayout();
        tools.addComponents(filterTextBezeichnung, clearFilterTextBtn, addBtn);
        tools.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        grid.setCaption("Template Buchhaltung");
        grid.setCaptionAsHtml(true);
        grid.addColumn(TemplateBuchhaltung::getId).setCaption("id");
        grid.addColumn(TemplateBuchhaltung::getBezeichnung).setCaption("Bezeichnung");
        grid.addColumn(buchhaltung -> buchhaltung.getTemplateKontoklasses().size()).setCaption("Anzahl Kontoklassen");

        grid.setSizeFull();

        // Render a button that deletes the data row (item)
        grid.addColumn(adresse -> "löschen",
                new ButtonRenderer(event -> {
                    Notification.show("Lösche Template Buchhaltung id:" + event.getItem(), Notification.Type.HUMANIZED_MESSAGE);
                    facade.delete((TemplateBuchhaltung) event.getItem());
                    updateList();
                })
        );

        grid.addColumn(buchhaltung -> "ändern",
                new ButtonRenderer(event -> {
                    form.setEntity((TemplateBuchhaltung) event.getItem());
                    form.openInModalPopup();
                    form.setSavedHandler(val -> {
                        facade.save(val);
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
        if (!filterTextBezeichnung.isEmpty()) {
            //Suche mit Bezeichnung
            logger.debug("Suche mit Bezeichnung:" + filterTextBezeichnung.getValue());
            grid.setItems(facade.findByBezeichnungLikeIgnoreCase(filterTextBezeichnung.getValue() + "%"));
            return;
        }
        grid.setItems(facade.findAll());
    }

}
