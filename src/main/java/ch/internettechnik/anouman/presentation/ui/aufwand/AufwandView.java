package ch.internettechnik.anouman.presentation.ui.aufwand;

import ch.internettechnik.anouman.backend.entity.Aufwand;
import ch.internettechnik.anouman.backend.entity.Rechnung;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.AufwandDeltaspikeFacade;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.RechnungDeltaspikeFacade;
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


// @todo : java.lang.IllegalStateException: Property type 'java.util.Date' doesn't match the field type 'java.time.LocalDateTime'.
// Binding should be configured manually using converter.
@CDIView("AufwandView")
public class AufwandView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(AufwandView.class.getName());

    TextField filterTextTitel = new TextField();
    ComboBox<Rechnung> filterRechnung = new ComboBox<Rechnung>();
    Grid<Aufwand> grid = new Grid<>();

    @Inject
    private Menu menu;

    @Inject
    private AufwandDeltaspikeFacade facade;

    @Inject
    private RechnungDeltaspikeFacade rechnungDeltaspikeFacade;

    @Inject
    private AufwandForm form;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        setStyleName("anouman-background");

        filterTextTitel.setPlaceholder("Filter Titel");
        filterTextTitel.addValueChangeListener(e -> updateList());
        filterTextTitel.setValueChangeMode(ValueChangeMode.LAZY);
        filterRechnung.setPlaceholder("Filter Rechnung");
        filterRechnung.setItems(rechnungDeltaspikeFacade.findAll());
        filterRechnung.setItemCaptionGenerator(rechnung -> rechnung.getBezeichnung() + " id:" + rechnung.getId());
        filterRechnung.setEmptySelectionAllowed(false);
        filterRechnung.addValueChangeListener(valueChangeEvent -> updateList());

        if (viewChangeEvent.getParameters() != null) {
            String[] msgs = viewChangeEvent.getParameters().split("/");
            String target = new String();
            Long id = new Long(0);
            for (String msg : msgs) {
                if (target.isEmpty()) {
                    target = msg;
                } else {
                    id = Long.valueOf(msg);
                }
            }
            if (target.equals("rechnungId")) {
                filterRechnung.setSelectedItem(rechnungDeltaspikeFacade.findBy(id));
                updateList();
            } else if (target.equals("id")) {
                grid.select(facade.findBy(id));
            }
        }

        Button clearFilterTextBtn = new Button(VaadinIcons.RECYCLE);
        clearFilterTextBtn.setDescription("Entferne Filter");
        clearFilterTextBtn.addClickListener(e -> {
            filterTextTitel.clear();
            filterRechnung.clear();
        });

        Button addBtn = new Button(VaadinIcons.PLUS);
        addBtn.addClickListener(event -> {
            grid.asSingleSelect().clear();
            Aufwand aufwand = new Aufwand();
            aufwand.setRechnung(rechnungDeltaspikeFacade.findAll().get(0));
            if (!filterRechnung.isEmpty()) aufwand.setRechnung(filterRechnung.getValue());
            form.setEntity(aufwand);
            form.openInModalPopup();
            form.setSavedHandler(val -> {
                facade.save(val);
                updateList();
                grid.select(val);
                form.closePopup();
            });
        });

        CssLayout tools = new CssLayout();
        tools.addComponents(filterRechnung, filterTextTitel, clearFilterTextBtn, addBtn);
        tools.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        grid.setCaption("Aufwand");
        grid.setCaptionAsHtml(true);
        grid.addColumn(Aufwand::getId).setCaption("id");
        grid.addColumn(Aufwand::getTitel).setCaption("Titel");
        grid.addColumn(Aufwand::getBezeichnung).setCaption("Bezeichnung");
        grid.addColumn(Aufwand::getStart).setCaption("Startzeit");
        grid.addColumn(Aufwand::getEnd).setCaption("Endzeit");
        grid.addColumn(Aufwand::getDauerInStunden).setCaption("Dauer in Stunden");
        grid.addColumn(aufwand -> aufwand.getRechnung().getBezeichnung() + " " +
                        aufwand.getRechnung().getAdresse().getNachname() + " " +
                        aufwand.getRechnung().getAdresse().getOrt() + " id:" +
                        aufwand.getRechnung().getId(),
                new ButtonRenderer(event -> {
                    Aufwand aufwand = (Aufwand) event.getItem();
                    UI.getCurrent().getNavigator().navigateTo("Rechnung/id/" + aufwand.getRechnung().getId());
                })
        ).setCaption("Rechnung").setStyleGenerator(item -> "v-align-center");

        grid.setSizeFull();

        // Render a button that deletes the data row (item)
        grid.addColumn(aufwand -> "löschen",
                new ButtonRenderer(event -> {
                    Notification.show("Lösche Aufwand id:" + event.getItem(), Notification.Type.HUMANIZED_MESSAGE);
                    facade.delete((Aufwand) event.getItem());
                    updateList();
                })
        );

        grid.addColumn(adresse -> "ändern",
                new ButtonRenderer(event -> {
                    form.setEntity((Aufwand) event.getItem());
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
        if ((!filterRechnung.isEmpty()) && (!filterTextTitel.isEmpty())) {
            //Suche mit Rechnung und Titel
            logger.debug("Suche mit Rechnung und Titel:" + filterRechnung.getValue().getId() + "," + filterTextTitel.getValue());
            grid.setItems(facade.findByRechnungAndTitelLikeIgnoreCase(filterRechnung.getValue(), filterTextTitel.getValue() + "%"));
            return;
        } else if ((!filterRechnung.isEmpty()) && (filterTextTitel.isEmpty())) {
            //Suche mit Rechnung
            logger.debug("Suche mit Rechnung:" + filterRechnung.getValue().getId());
            grid.setItems(facade.findByRechnung(filterRechnung.getValue()));
            return;
        } else if ((filterRechnung.isEmpty()) && (!filterTextTitel.isEmpty())) {
            //Suche mit Titel
            logger.debug("Suche mit Titel:" + filterTextTitel.getValue());
            grid.setItems(facade.findByTitelLikeIgnoreCase(filterTextTitel.getValue() + "%"));
            return;
        }
        grid.setItems(facade.findAll());
    }

}
