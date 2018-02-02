package ch.internettechnik.anouman.presentation.ui.aufwand;

import ch.internettechnik.anouman.backend.entity.Aufwand;
import ch.internettechnik.anouman.backend.entity.Rechnung;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.AufwandDeltaspikeFacade;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.RechnungDeltaspikeFacade;
import ch.internettechnik.anouman.presentation.ui.Menu;
import ch.internettechnik.anouman.presentation.ui.field.AnzahlField;
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
import org.vaadin.crudui.form.impl.field.provider.NativeSelectProvider;
import org.vaadin.crudui.form.impl.form.factory.GridLayoutCrudFormFactory;
import org.vaadin.crudui.layout.impl.WindowBasedCrudLayout;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Collection;

@CDIView("AufwandView")
public class AufwandView extends VerticalLayout implements View, CrudListener<Aufwand> {
    private static Logger logger = LoggerFactory.getLogger(AufwandView.class.getName());

    @Inject
    AufwandDeltaspikeFacade aufwandDeltaspikeFacade;

    @Inject
    RechnungDeltaspikeFacade rechnungDeltaspikeFacade;

    GridCrud<Aufwand> crud;
    CssLayout filterToolbar = new CssLayout();
    TextField filterTextTitel = new TextField();
    ComboBox<Rechnung> filterRechnung = new ComboBox<>();


    private Collection<Aufwand> getItems() {
        if ((!filterRechnung.isEmpty()) && (!filterTextTitel.isEmpty())) {
            // Such mit Rechnung und Bezeichnung
            logger.debug("Suche mit Rechnung und Bezeichnung:" + filterRechnung.getValue().getId() + "," + filterTextTitel.getValue());
            return (aufwandDeltaspikeFacade.findByRechnungAndTitelLikeIgnoreCase(filterRechnung.getValue(), filterTextTitel.getValue() + "%"));

        } else if ((!filterRechnung.isEmpty()) && (filterTextTitel.isEmpty())) {
            // Suche mit Rechnung
            logger.debug("Suche mit Rechnung:" + filterRechnung.getValue().getId());
            return (aufwandDeltaspikeFacade.findByRechnung(filterRechnung.getValue()));
        } else if ((filterRechnung.isEmpty()) && (!filterTextTitel.isEmpty())) {
            // Suche mit Bezeichnung
            logger.debug("Suche mit Titel:" + filterTextTitel.getValue());
            return (aufwandDeltaspikeFacade.findByTitelLikeIgnoreCase(filterTextTitel.getValue() + "%"));
        }
        return (aufwandDeltaspikeFacade.findAll());
    }

    private Crud createCrud() {
        crud = new GridCrud<Aufwand>(Aufwand.class, new WindowBasedCrudLayout());
        crud.setCrudListener(this);


        //@todo Layout anpassen
        GridLayoutCrudFormFactory<Aufwand> formFactory = new GridLayoutCrudFormFactory<>(Aufwand.class, 3, 2);


        //VerticalCrudFormFactory<Aufwand> formFactory = new VerticalCrudFormFactory<>(Aufwand.class);

        crud.setCrudFormFactory(formFactory);

        formFactory.setUseBeanValidation(true);

        formFactory.setErrorListener(e -> Notification.show("Custom error message (simulated error)", Notification.Type.ERROR_MESSAGE));

        formFactory.setVisibleProperties(CrudOperation.READ, "id", "titel", "bezeichnung", "start", "end", "rechnung");
        formFactory.setVisibleProperties(CrudOperation.ADD, "id", "titel", "bezeichnung", "start", "end", "rechnung");
        formFactory.setVisibleProperties(CrudOperation.UPDATE, "id", "titel", "bezeichnung", "start", "end", "rechnung");
        formFactory.setVisibleProperties(CrudOperation.DELETE, "id", "titel");

        formFactory.setDisabledProperties("id");

        crud.getGrid().setColumns("id", "titel", "bezeichnung", "start", "end");

        crud.getGrid().addColumn(aufwand -> aufwand.getRechnung().getId(), new ButtonRenderer(event -> {
            Aufwand aufwand = (Aufwand) event.getItem();
            UI.getCurrent().getNavigator().navigateTo("RechnungView/id/" + aufwand.getRechnung().getId().toString());
        })).setCaption("Rechnung").setStyleGenerator(item -> "v-align-center");

        formFactory.setFieldType("bezeichnung", TextArea.class);
        formFactory.setFieldType("start", InlineDateTimeField.class);
        formFactory.setFieldType("end", InlineDateTimeField.class);
        formFactory.setFieldType("anzahl", AnzahlField.class);
        formFactory.setFieldType("stueckpreis", BetragField.class);

        formFactory.setFieldProvider("rechnung", new NativeSelectProvider<Rechnung>("Rechnung", rechnungDeltaspikeFacade.findAll(),
                item -> item.getId() + " " + item.getBezeichnung() + " " + item.getRechnungsdatum().toString()));

        formFactory.setButtonCaption(CrudOperation.ADD, "Neuen Aufwand erstellen");
        formFactory.setButtonCaption(CrudOperation.DELETE, "Aufwand löschen");

        crud.setRowCountCaption("%d Aufwands gefunden");

        crud.getCrudLayout().addToolbarComponent(filterToolbar);
        crud.setClickRowToUpdate(false);
        crud.setUpdateOperationVisible(true);
        crud.setDeleteOperationVisible(true);

        return crud;
    }

    @PostConstruct
    void init() {
        filterTextTitel.setPlaceholder("Filter für Bezeichnung");
        filterTextTitel.addValueChangeListener(e -> crud.getGrid().setItems(getItems()));
        filterTextTitel.setValueChangeMode(ValueChangeMode.LAZY);

        filterRechnung.setPlaceholder("Filter für Rechnung");
        filterRechnung.addValueChangeListener(e -> crud.getGrid().setItems(getItems()));
        filterRechnung.setItems(rechnungDeltaspikeFacade.findAll());
        filterRechnung.setItemCaptionGenerator(item -> item.getId() + " " + item.getBezeichnung() + " " + item.getRechnungstotal());


        Button clearFilterTextBtn = new Button(VaadinIcons.RECYCLE);
        clearFilterTextBtn.setDescription("Entferne Filter");
        clearFilterTextBtn.addClickListener(e -> {
            filterTextTitel.clear();
            filterRechnung.clear();
        });

        filterToolbar.addComponents(filterTextTitel, filterRechnung, clearFilterTextBtn);
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
                crud.getGrid().select(aufwandDeltaspikeFacade.findBy(id));
            } else if (target.equals("rechnungId")) {
                filterRechnung.setValue(rechnungDeltaspikeFacade.findBy(id));
            }
        }
    }

    @Override
    public Collection<Aufwand> findAll() {
        return getItems();
    }

    @Override
    public Aufwand add(Aufwand aufwand) {
        return aufwandDeltaspikeFacade.save(aufwand);
    }

    @Override
    public Aufwand update(Aufwand aufwand) {
        return aufwandDeltaspikeFacade.save(aufwand);
    }

    @Override
    public void delete(Aufwand aufwand) {
        aufwandDeltaspikeFacade.delete(aufwand);
    }
}
