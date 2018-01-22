package ch.internettechnik.anouman.presentation.ui.rechnungsposition;

import ch.internettechnik.anouman.backend.entity.Rechnung;
import ch.internettechnik.anouman.backend.entity.Rechnungsposition;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.RechnungDeltaspikeFacade;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.RechnungspositionDeltaspikeFacade;
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

@CDIView("RechnungspositionView")
public class RechnungspositionView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(RechnungspositionView.class.getName());

    TextField filterTextBezeichnung = new TextField();
    ComboBox<Rechnung> filterRechnung = new ComboBox<>();
    Grid<Rechnungsposition> grid = new Grid<>();

    @Inject
    private Menu menu;

    @Inject
    private RechnungspositionDeltaspikeFacade rechnungspositionDeltaspikeFacade;

    @Inject
    private RechnungDeltaspikeFacade rechnungDeltaspikeFacade;

    @Inject
    private RechnungspositionForm form;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        setStyleName("anouman-background");

        filterTextBezeichnung.setPlaceholder("Filter für Bezeichnung");
        filterTextBezeichnung.addValueChangeListener(e -> updateList());
        filterTextBezeichnung.setValueChangeMode(ValueChangeMode.LAZY);
        filterRechnung.setPlaceholder("Filter für Rechnung");
        filterRechnung.setItems(rechnungDeltaspikeFacade.findAll());
        filterRechnung.setItemCaptionGenerator(item -> item.getBezeichnung() + " " + item.getAdresse().getFirma() + " " + item.getAdresse().getOrt() + " id:" + item.getId());
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
                grid.select(rechnungspositionDeltaspikeFacade.findBy(id));
            }
        }

        Button clearFilterTextBtn = new Button(VaadinIcons.RECYCLE);
        clearFilterTextBtn.setDescription("Entferne Filter");
        clearFilterTextBtn.addClickListener(e -> {
            filterTextBezeichnung.clear();
            filterRechnung.clear();
        });

        Button addBtn = new Button(VaadinIcons.PLUS);
        addBtn.addClickListener(event -> {
            grid.asSingleSelect().clear();
            Rechnungsposition rp = new Rechnungsposition();
            rp.setRechnung(rechnungDeltaspikeFacade.findAll().get(0));
            rp.setAnzahl(0d);
            rp.setStueckpreis(0d);
            if (!filterRechnung.isEmpty()) rp.setRechnung(filterRechnung.getValue());
            form.setEntity(rp);
            form.openInModalPopup();
            form.setSavedHandler(rechnungsposition -> {
                rechnungspositionDeltaspikeFacade.save(rechnungsposition);
                updateList();
                grid.select(rechnungsposition);
                form.closePopup();
            });
        });

        CssLayout tools = new CssLayout();
        tools.addComponents(filterRechnung, filterTextBezeichnung, clearFilterTextBtn, addBtn);
        tools.setWidth(50, Unit.PERCENTAGE);
        tools.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        grid.setCaption("Rechnungsposition");
        grid.setCaptionAsHtml(true);
        grid.addColumn(Rechnungsposition::getId).setCaption("id");
        grid.addColumn(Rechnungsposition::getBezeichnung).setCaption("Bezeichnung");
        grid.addColumn(Rechnungsposition::getAnzahl).setCaption("Anzahl");
        grid.addColumn(Rechnungsposition::getMengeneinheit).setCaption("Mengeneinheit");
        grid.addColumn(Rechnungsposition::getStueckpreis).setCaption("Stückpreis");
        grid.addColumn(Rechnungsposition::getPositionstotal).setCaption("Positionstotal");
        grid.addColumn(rechnungsposition -> rechnungsposition.getRechnung().getBezeichnung() + " " +
                        rechnungsposition.getRechnung().getAdresse().getNachname() + " " + rechnungsposition.getRechnung().getAdresse().getOrt() + " id:" + rechnungsposition.getRechnung().getId(),
                new ButtonRenderer(event -> {
                    Rechnungsposition val = (Rechnungsposition) event.getItem();
                    UI.getCurrent().getNavigator().navigateTo("RechnungOldView/id/" + val.getRechnung().getId());
                })
        ).setCaption("Rechnung").setStyleGenerator(item -> "v-align-center");

        grid.setSizeFull();

        // Render a button that deletes the data row (item)
        grid.addColumn(rechnungsposition -> "löschen",
                new ButtonRenderer(event -> {
                    Rechnungsposition rechnungsposition = (Rechnungsposition) event.getItem();
                    Notification.show("Lösche Rechnungsposition id:" + rechnungsposition, Notification.Type.HUMANIZED_MESSAGE);
                    rechnungspositionDeltaspikeFacade.delete(rechnungsposition);
                    //rechnungspositionService.delete((Rechnungsposition) event.getItem());
                    updateList();
                })
        );

        grid.addColumn(rechnungsposition -> "ändern",
                new ButtonRenderer(event -> {
                    form.setEntity((Rechnungsposition) event.getItem());
                    form.openInModalPopup();
                    form.setSavedHandler(rechnungsposition -> {
                        rechnungspositionDeltaspikeFacade.save(rechnungsposition);
                        updateList();
                        grid.select(rechnungsposition);
                        form.closePopup();
                    });
                    form.setResetHandler(rechnungsposition -> {
                        updateList();
                        grid.select(rechnungsposition);
                        form.closePopup();
                    });
                }));

        updateList();
        addComponents(menu, tools);
        addComponentsAndExpand(grid);
    }

    public void updateList() {
        if ((!filterRechnung.isEmpty()) && (!filterTextBezeichnung.isEmpty())) {
            // Such mit Rechnung und Bezeichnung
            logger.debug("Suche mit Rechnung und Bezeichnung:" + filterRechnung.getValue().getId() + "," + filterTextBezeichnung.getValue());
            grid.setItems(rechnungspositionDeltaspikeFacade.findByRechnungAndBezeichnungLikeIgnoreCase(filterRechnung.getValue(), filterTextBezeichnung.getValue() + "%"));
            return;
        } else if ((!filterRechnung.isEmpty()) && (filterTextBezeichnung.isEmpty())) {
            // Suche mit Rechnung
            logger.debug("Suche mit Rechnung:" + filterRechnung.getValue().getId());
            grid.setItems(rechnungspositionDeltaspikeFacade.findByRechnung(filterRechnung.getValue()));
            return;
        } else if ((filterRechnung.isEmpty()) && (!filterTextBezeichnung.isEmpty())) {
            // Suche mit Bezeichnung
            logger.debug("Suche mit Bezeichnung:" + filterTextBezeichnung.getValue());
            grid.setItems(rechnungspositionDeltaspikeFacade.findByBezeichnungLikeIgnoreCase(filterTextBezeichnung.getValue() + "%"));
            return;
        }
        grid.setItems(rechnungspositionDeltaspikeFacade.findAll());
    }
}
