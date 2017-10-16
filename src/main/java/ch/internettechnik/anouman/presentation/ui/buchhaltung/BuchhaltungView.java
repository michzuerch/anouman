package ch.internettechnik.anouman.presentation.ui.buchhaltung;

import ch.internettechnik.anouman.backend.entity.Buchhaltung;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.BuchhaltungFacade;
import ch.internettechnik.anouman.presentation.ui.Menu;
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

@CDIView(value = "Buchhaltung")
public class BuchhaltungView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(BuchhaltungView.class.getName());

    TextField filterTextBezeichnung = new TextField();
    Grid<Buchhaltung> grid = new Grid<>();

    @Inject
    private Menu menu;

    @Inject
    private BuchhaltungFacade facade;

    @Inject
    private BuchhaltungForm form;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        setStyleName("anouman-background");

        filterTextBezeichnung.setPlaceholder("Filter für Bezeichnung...");
        filterTextBezeichnung.addValueChangeListener(e -> updateList());
        filterTextBezeichnung.setValueChangeMode(ValueChangeMode.LAZY);

        Button clearFilterTextBtn = new Button(VaadinIcons.RECYCLE);
        clearFilterTextBtn.setDescription("Entferne Filter");
        clearFilterTextBtn.addClickListener(e -> filterTextBezeichnung.clear());

        Button addBtn = new Button(VaadinIcons.PLUS);
        addBtn.addClickListener(event -> {
            grid.asSingleSelect().clear();
            form.setEntity(new Buchhaltung());
            form.openInModalPopup();
            form.setSavedHandler(val -> {
                facade.save(val);
                updateList();
                grid.select(val);
                form.closePopup();
            });
        });

        CssLayout tools = new CssLayout();
        tools.addComponents(filterTextBezeichnung, clearFilterTextBtn, addBtn);
        tools.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        grid.setCaption("Buchhaltung");
        grid.setCaptionAsHtml(true);
        grid.addColumn(Buchhaltung::getId).setHidable(true).setCaption("id");
        grid.addColumn(Buchhaltung::getBezeichnung).setCaption("Bezeichnung");
        grid.addColumn(Buchhaltung::getJahr).setCaption("Jahr");
        grid.addColumn(buchhaltung -> buchhaltung.getKontoklasse().size()).setCaption("Anzahl Kontoklassen");

        grid.setSizeFull();

        // Render a button that deletes the data row (item)
        grid.addColumn(adresse -> "löschen",
                new ButtonRenderer(event -> {
                    Notification.show("Lösche Buchhaltung id:" + event.getItem(), Notification.Type.HUMANIZED_MESSAGE);
                    facade.delete((Buchhaltung) event.getItem());
                    updateList();
                })
        );

        grid.addColumn(buchhaltung -> "ändern",
                new ButtonRenderer(event -> {
                    form.setEntity((Buchhaltung) event.getItem());
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
