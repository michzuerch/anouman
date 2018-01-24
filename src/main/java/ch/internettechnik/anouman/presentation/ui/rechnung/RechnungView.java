package ch.internettechnik.anouman.presentation.ui.rechnung;

import ch.internettechnik.anouman.backend.entity.Adresse;
import ch.internettechnik.anouman.backend.entity.Rechnung;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.AdresseDeltaspikeFacade;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.RechnungDeltaspikeFacade;
import ch.internettechnik.anouman.presentation.ui.Menu;
import com.vaadin.cdi.CDIView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.LocalDateRenderer;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.crudui.crud.Crud;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.impl.form.factory.VerticalCrudFormFactory;
import org.vaadin.crudui.layout.impl.WindowBasedCrudLayout;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Collection;

@CDIView("RechnungView")
public class RechnungView extends VerticalLayout implements View, CrudListener<Rechnung> {
    private static Logger logger = LoggerFactory.getLogger(ch.internettechnik.anouman.presentation.ui.rechnung.RechnungView.class.getName());

    @Inject
    RechnungDeltaspikeFacade rechnungDeltaspikeFacade;

    @Inject
    AdresseDeltaspikeFacade adresseDeltaspikeFacade;

    GridCrud<Rechnung> crud;
    CssLayout filterToolbar = new CssLayout();
    TextField filterTextBezeichnung = new TextField();
    ComboBox<Adresse> filterAdresse = new ComboBox<>();


    private Collection<Rechnung> getItems() {
        if ((!filterAdresse.isEmpty()) && (!filterTextBezeichnung.isEmpty())) {
            //Suche mit Adresse und Bezeichnung
            logger.debug("Suche mit Adresse und Bezeichnung:" + filterAdresse.getValue().getId() + "," + filterTextBezeichnung.getValue());
            return rechnungDeltaspikeFacade.findByAdresseAndBezeichnungLikeIgnoreCase(
                    filterAdresse.getValue(), filterTextBezeichnung.getValue() + "%");
        } else if ((filterAdresse.isEmpty()) && (!filterTextBezeichnung.isEmpty())) {
            //Suche mit Bezeichnung
            logger.debug("Suche mit Bezeichnung:" + filterTextBezeichnung.getValue());
            return rechnungDeltaspikeFacade.findByBezeichnungLikeIgnoreCase(filterTextBezeichnung.getValue() + "%");
        } else if ((!filterAdresse.isEmpty()) && (filterTextBezeichnung.isEmpty())) {
            //Suche mit Adresse
            logger.debug("Suche mit Adresse:" + filterAdresse.getValue());
            return rechnungDeltaspikeFacade.findByAdresse(filterAdresse.getValue());
        }
        return (rechnungDeltaspikeFacade.findAll());
    }

    private Crud createCrud() {
        crud = new GridCrud<Rechnung>(Rechnung.class, new WindowBasedCrudLayout());
        crud.setCrudListener(this);

        VerticalCrudFormFactory<Rechnung> formFactory = new VerticalCrudFormFactory<>(Rechnung.class);

        crud.setCrudFormFactory(formFactory);

        formFactory.setUseBeanValidation(true);

        formFactory.setErrorListener(e -> Notification.show("Custom error message (simulated error)", Notification.Type.ERROR_MESSAGE));

        formFactory.setVisibleProperties(CrudOperation.READ, "id", "bezeichnung", "rechnungsdatum", "faelligInTagen", "bezahlt", "verschickt", "adresse");
        formFactory.setVisibleProperties(CrudOperation.ADD, "bezeichnung", "rechnungsdatum", "faelligInTagen", "bezahlt", "verschickt", "adresse");
        formFactory.setVisibleProperties(CrudOperation.UPDATE, "id", "bezeichnung", "rechnungsdatum", "faelligInTagen", "bezahlt", "verschickt", "adresse");
        formFactory.setVisibleProperties(CrudOperation.DELETE, "bezeichnung");

        formFactory.setDisabledProperties("id");

        crud.getGrid().setColumns("id", "bezeichnung", "rechnungsdatum", "faelligInTagen", "bezahlt", "verschickt");
        crud.getGrid().addColumn(rechnung -> rechnung.getAdresse().getFirma() + " " +
                        rechnung.getAdresse().getNachname() + " " + rechnung.getAdresse().getOrt() + " id:" + rechnung.getAdresse().getId(),
                new ButtonRenderer(event -> {
                    Rechnung rechnung = (Rechnung) event.getItem();
                    UI.getCurrent().getNavigator().navigateTo("AdresseView/id/" + rechnung.getAdresse().getId());
                })
        ).setCaption("Adresse").setStyleGenerator(item -> "v-align-center");
        crud.getGrid().addColumn(rechnung -> rechnung.getAnzahlRechnungspositionen(), new ButtonRenderer(event -> {
            Rechnung rechnung = (Rechnung) event.getItem();
            UI.getCurrent().getNavigator().navigateTo("RechnungspositionView/rechnungId/" + rechnung.getId());
        })).setCaption("Rechnungspositionen").setStyleGenerator(item -> "v-align-center");

        crud.getGrid().addColumn(rechnung -> rechnung.getAnzahlAufwands(), new ButtonRenderer(event -> {
            Rechnung rechnung = (Rechnung) event.getItem();
            UI.getCurrent().getNavigator().navigateTo("AufwandView/rechnungId/" + rechnung.getId());
        })).setCaption("Aufwand").setStyleGenerator(item -> "v-align-center");

        //((Grid.Column<Rechnung, LocalDate>) crud.getGrid().getColumn("rechnungsdatum")).setRenderer(new LocalDateRenderer("%1$tY-%1$tm-%1$te"));
        ((Grid.Column<Rechnung, LocalDate>) crud.getGrid().getColumn("rechnungsdatum")).setRenderer(new LocalDateRenderer());

        formFactory.setFieldType("rechnungsdatum", InlineDateField.class);
        formFactory.setButtonCaption(CrudOperation.ADD, "Neue Rechnung erstellen");
        formFactory.setButtonCaption(CrudOperation.DELETE, "Rechnung löschen");

        crud.setRowCountCaption("%d Rechnungen gefunden");

        crud.getCrudLayout().addToolbarComponent(filterToolbar);
        crud.setClickRowToUpdate(false);
        crud.setUpdateOperationVisible(true);
        crud.setDeleteOperationVisible(true);

        return crud;
    }

    @PostConstruct
    void init() {
        filterTextBezeichnung.setPlaceholder("Filter für Bezeichnung");
        filterTextBezeichnung.addValueChangeListener(e -> crud.getGrid().setItems(getItems()));
        filterTextBezeichnung.setValueChangeMode(ValueChangeMode.LAZY);

        filterAdresse.setPlaceholder("Filter für Adresse");
        filterAdresse.addValueChangeListener(e -> crud.getGrid().setItems(getItems()));
        filterAdresse.setItems(adresseDeltaspikeFacade.findAll());
        filterAdresse.setItemCaptionGenerator(item -> item.getId() + " " + item.getFirma() + " " + item.getNachname());

        Button clearFilterTextBtn = new Button(VaadinIcons.RECYCLE);
        clearFilterTextBtn.setDescription("Entferne Filter");
        clearFilterTextBtn.addClickListener(e -> {
            filterTextBezeichnung.clear();
            filterAdresse.clear();
        });

        filterToolbar.addComponents(filterTextBezeichnung, filterAdresse, clearFilterTextBtn);
        filterToolbar.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        addComponents(new Menu());
        addComponentsAndExpand(createCrud());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (event.getParameters() != null) {
            String[] msgs = event.getParameters().split("/");
            String target = new String();
            Long id = new Long(0);
            for (String msg : msgs) {
                if (target.isEmpty()) {
                    target = msg;
                } else {
                    id = Long.valueOf(msg);
                }
            }
            if (target.equals("adresseId")) {
                filterAdresse.setSelectedItem(adresseDeltaspikeFacade.findBy(id));
                crud.getGrid().setItems(getItems());
            } else if (target.equals("id")) {
                crud.getGrid().select(rechnungDeltaspikeFacade.findBy(id));
            }
        }
    }


    @Override
    public Collection<Rechnung> findAll() {
        return getItems();
    }

    @Override
    public Rechnung add(Rechnung rechnung) {
        return rechnungDeltaspikeFacade.save(rechnung);
    }

    @Override
    public Rechnung update(Rechnung rechnung) {
        return rechnungDeltaspikeFacade.save(rechnung);
    }

    @Override
    public void delete(Rechnung rechnung) {
        rechnungDeltaspikeFacade.delete(rechnung);
    }

}
