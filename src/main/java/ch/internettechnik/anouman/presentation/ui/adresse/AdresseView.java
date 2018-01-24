package ch.internettechnik.anouman.presentation.ui.adresse;

import ch.internettechnik.anouman.backend.entity.Adresse;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.AdresseDeltaspikeFacade;
import ch.internettechnik.anouman.presentation.ui.Menu;
import ch.internettechnik.anouman.presentation.ui.field.BetragField;
import com.vaadin.cdi.CDIView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
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
import java.util.Collection;

@CDIView("AdresseView")
public class AdresseView extends VerticalLayout implements View, CrudListener<Adresse> {
    private static Logger logger = LoggerFactory.getLogger(AdresseView.class.getName());

    @Inject
    AdresseDeltaspikeFacade facade;

    GridCrud<Adresse> crud;
    CssLayout filterToolbar = new CssLayout();
    TextField filterTextFirma = new TextField();
    TextField filterTextNachname = new TextField();
    TextField filterTextOrt = new TextField();


    private Collection<Adresse> getItems() {
        if ((!filterTextFirma.isEmpty()) && (!filterTextNachname.isEmpty()) && (!filterTextOrt.isEmpty())) {
            //Suche mit Firma und Nachname und Ort
            logger.debug("Suche mit Firma und Nachname und Ort:" + filterTextFirma.getValue() + "," + filterTextNachname.getValue() + "," + filterTextOrt.getValue());
            return (facade.findByFirmaLikeIgnoreCaseAndNachnameLikeIgnoreCaseAndOrtLikeIgnoreCase(
                    filterTextFirma.getValue() + "%", filterTextNachname.getValue() + "%", filterTextOrt.getValue() + "%"));
        } else if ((!filterTextFirma.isEmpty()) && (!filterTextNachname.isEmpty()) && (filterTextOrt.isEmpty())) {
            //Suche mit Firma und Nachname
            logger.debug("Suche mit Firma und Nachname:" + filterTextFirma.getValue() + "," + filterTextNachname.getValue());
            return (facade.findByFirmaLikeIgnoreCaseAndNachnameLikeIgnoreCase(filterTextFirma.getValue() + "%", filterTextNachname.getValue() + "%"));
        } else if ((!filterTextFirma.isEmpty()) && (filterTextNachname.isEmpty()) && (!filterTextOrt.isEmpty())) {
            //Suche mit Firma und Ort
            logger.debug("Suche mit Firma und Ort:" + filterTextFirma.getValue() + "," + filterTextOrt.getValue());
            return (facade.findByFirmaLikeIgnoreCaseAndOrtLikeIgnoreCase(filterTextFirma.getValue() + "%", filterTextOrt.getValue() + "%"));

        } else if ((filterTextFirma.isEmpty()) && (!filterTextNachname.isEmpty()) && (filterTextOrt.isEmpty())) {
            //Suche mit Nachname
            logger.debug("Suche mit Nachname:" + filterTextNachname.getValue());
            return (facade.findByNachnameLikeIgnoreCase(filterTextNachname.getValue() + "%"));

        } else if ((filterTextFirma.isEmpty()) && (filterTextNachname.isEmpty()) && (!filterTextOrt.isEmpty())) {
            //Suche mit Ort
            logger.debug("Suche mit Ort:" + filterTextOrt.getValue());
            return (facade.findByOrtLikeIgnoreCase(filterTextOrt.getValue() + "%"));

        } else if ((!filterTextFirma.isEmpty()) && (filterTextNachname.isEmpty()) && (filterTextOrt.isEmpty())) {
            //Suche mit Firma
            logger.debug("Suche mit Firma:" + filterTextFirma.getValue());
            return (facade.findByFirmaLikeIgnoreCase(filterTextFirma.getValue() + "%"));

        } else if ((filterTextFirma.isEmpty()) && (!filterTextNachname.isEmpty()) && (!filterTextOrt.isEmpty())) {
            //Suche mit Nachname und Ort
            logger.debug("Suche mit Nachname und Ort:" + filterTextNachname.getValue() + "," + filterTextOrt.getValue());
            return (facade.findByNachnameLikeIgnoreCaseAndOrtLikeIgnoreCase(
                    filterTextNachname.getValue() + "%", filterTextOrt.getValue() + "%"));

        }
        return facade.findAll();
    }

    private Crud createCrud() {
        crud = new GridCrud<Adresse>(Adresse.class, new WindowBasedCrudLayout());
        crud.setCrudListener(this);

        VerticalCrudFormFactory<Adresse> formFactory = new VerticalCrudFormFactory<>(Adresse.class);

        crud.setCrudFormFactory(formFactory);

        formFactory.setUseBeanValidation(true);

        formFactory.setErrorListener(e -> Notification.show("Custom error message (simulated error)", Notification.Type.ERROR_MESSAGE));

        formFactory.setVisibleProperties(CrudOperation.READ, "id", "firma", "anrede", "vorname", "nachname", "strasse", "postleitzahl",
                "ort", "stundensatz");
        formFactory.setVisibleProperties(CrudOperation.ADD, "firma", "anrede", "vorname", "nachname", "strasse", "postleitzahl",
                "ort", "stundensatz");
        formFactory.setVisibleProperties(CrudOperation.UPDATE, "id", "firma", "anrede", "vorname", "nachname", "strasse", "postleitzahl",
                "ort", "stundensatz");
        formFactory.setVisibleProperties(CrudOperation.DELETE, "firma");

        formFactory.setDisabledProperties("id");
        formFactory.setDisabledProperties("anzahlRechnungen");

        crud.getGrid().setColumns("id", "firma", "anrede", "vorname", "nachname", "strasse", "postleitzahl",
                "ort", "stundensatz");

        crud.getGrid().addColumn(adresse -> adresse.getAnzahlRechnungen(), new ButtonRenderer(event -> {
            Adresse adresse = (Adresse) event.getItem();
            if (adresse.getAnzahlRechnungen() > 0) {
                UI.getCurrent().getNavigator().navigateTo("RechnungView/adresseId/" + adresse.getId().toString());
            }
        })).setCaption("Anzahl Rechnungen").setStyleGenerator(item -> "v-align-center");

        formFactory.setFieldType("stundensatz", BetragField.class);
        formFactory.setButtonCaption(CrudOperation.ADD, "Neue Adresse erstellen");
        formFactory.setButtonCaption(CrudOperation.DELETE, "Adresse löschen");

        crud.setRowCountCaption("%d Adressen gefunden");

        crud.getCrudLayout().addToolbarComponent(filterToolbar);
        crud.setClickRowToUpdate(false);
        crud.setUpdateOperationVisible(true);
        crud.setDeleteOperationVisible(true);

        return crud;
    }

    @PostConstruct
    void init() {
        filterTextFirma.setPlaceholder("Filter für Firma");
        filterTextFirma.addValueChangeListener(e -> crud.getGrid().setItems(getItems()));
        filterTextFirma.setValueChangeMode(ValueChangeMode.LAZY);

        filterTextNachname.setPlaceholder("Filter für Nachname");
        filterTextNachname.addValueChangeListener(e -> crud.getGrid().setItems(getItems()));
        filterTextNachname.setValueChangeMode(ValueChangeMode.LAZY);

        filterTextOrt.setPlaceholder("Filter für Ort");
        filterTextOrt.addValueChangeListener(e -> crud.getGrid().setItems(getItems()));
        filterTextOrt.setValueChangeMode(ValueChangeMode.LAZY);

        Button clearFilterTextBtn = new Button(VaadinIcons.RECYCLE);
        clearFilterTextBtn.setDescription("Entferne Filter");
        clearFilterTextBtn.addClickListener(e -> {
            filterTextFirma.clear();
            filterTextNachname.clear();
            filterTextOrt.clear();
        });

        filterToolbar.addComponents(filterTextFirma, filterTextNachname, filterTextOrt, clearFilterTextBtn);
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
            if (target.equals("id")) {
                crud.getGrid().select(facade.findBy(id));
            }
        }
    }

    @Override
    public Collection<Adresse> findAll() {
        return getItems();
    }

    @Override
    public Adresse add(Adresse adresse) {
        return facade.save(adresse);
    }

    @Override
    public Adresse update(Adresse adresse) {
        return facade.save(adresse);
    }

    @Override
    public void delete(Adresse adresse) {
        facade.delete(adresse);
    }
}
