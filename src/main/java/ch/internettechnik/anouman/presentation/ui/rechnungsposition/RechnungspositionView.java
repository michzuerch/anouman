package ch.internettechnik.anouman.presentation.ui.rechnungsposition;

import ch.internettechnik.anouman.backend.entity.Rechnung;
import ch.internettechnik.anouman.backend.entity.Rechnungsposition;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.RechnungDeltaspikeFacade;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.RechnungspositionDeltaspikeFacade;
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
import org.vaadin.crudui.form.impl.form.factory.VerticalCrudFormFactory;
import org.vaadin.crudui.layout.impl.WindowBasedCrudLayout;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Collection;

@CDIView("RechnungspositionView")
public class RechnungspositionView extends VerticalLayout implements View, CrudListener<Rechnungsposition> {
    private static Logger logger = LoggerFactory.getLogger(RechnungspositionView.class.getName());

    @Inject
    RechnungspositionDeltaspikeFacade rechnungspositionDeltaspikeFacade;

    @Inject
    RechnungDeltaspikeFacade rechnungDeltaspikeFacade;

    GridCrud<Rechnungsposition> crud;
    CssLayout filterToolbar = new CssLayout();
    TextField filterTextBezeichnung = new TextField();
    ComboBox<Rechnung> filterRechnung = new ComboBox<>();


    private Collection<Rechnungsposition> getItems() {
        if ((!filterRechnung.isEmpty()) && (!filterTextBezeichnung.isEmpty())) {
            // Such mit Rechnung und Bezeichnung
            logger.debug("Suche mit Rechnung und Bezeichnung:" + filterRechnung.getValue().getId() + "," + filterTextBezeichnung.getValue());
            return (rechnungspositionDeltaspikeFacade.findByRechnungAndBezeichnungLikeIgnoreCase(filterRechnung.getValue(), filterTextBezeichnung.getValue() + "%"));

        } else if ((!filterRechnung.isEmpty()) && (filterTextBezeichnung.isEmpty())) {
            // Suche mit Rechnung
            logger.debug("Suche mit Rechnung:" + filterRechnung.getValue().getId());
            return (rechnungspositionDeltaspikeFacade.findByRechnung(filterRechnung.getValue()));
        } else if ((filterRechnung.isEmpty()) && (!filterTextBezeichnung.isEmpty())) {
            // Suche mit Bezeichnung
            logger.debug("Suche mit Bezeichnung:" + filterTextBezeichnung.getValue());
            return (rechnungspositionDeltaspikeFacade.findByBezeichnungLikeIgnoreCase(filterTextBezeichnung.getValue() + "%"));
        }
        return (rechnungspositionDeltaspikeFacade.findAll());
    }

    private Crud createCrud() {
        crud = new GridCrud<Rechnungsposition>(Rechnungsposition.class, new WindowBasedCrudLayout());
        crud.setCrudListener(this);

        VerticalCrudFormFactory<Rechnungsposition> formFactory = new VerticalCrudFormFactory<>(Rechnungsposition.class);

        crud.setCrudFormFactory(formFactory);

        formFactory.setUseBeanValidation(true);

        formFactory.setErrorListener(e -> Notification.show("Custom error message (simulated error)", Notification.Type.ERROR_MESSAGE));

        formFactory.setVisibleProperties(CrudOperation.READ, "id", "bezeichnung", "bezeichnunglang", "mengeneinheit", "stueckpreis", "anzahl", "rechnung");
        formFactory.setVisibleProperties(CrudOperation.ADD, "id", "bezeichnung", "bezeichnunglang", "mengeneinheit", "stueckpreis", "anzahl", "rechnung");
        formFactory.setVisibleProperties(CrudOperation.UPDATE, "id", "bezeichnung", "bezeichnunglang", "mengeneinheit", "stueckpreis", "anzahl", "rechnung");
        formFactory.setVisibleProperties(CrudOperation.DELETE, "id", "bezeichnung");

        formFactory.setDisabledProperties("id");
        formFactory.setDisabledProperties("anzahlRechnungen");

        crud.getGrid().setColumns("id", "bezeichnung", "bezeichnunglang", "mengeneinheit", "stueckpreis", "anzahl");

        crud.getGrid().addColumn(rechnungsposition -> rechnungsposition.getRechnung().getId(), new ButtonRenderer(event -> {
            Rechnungsposition rechnungsposition = (Rechnungsposition) event.getItem();
            UI.getCurrent().getNavigator().navigateTo("RechnungView/id/" + rechnungsposition.getRechnung().getId().toString());
        })).setCaption("Rechnung").setStyleGenerator(item -> "v-align-center");

        formFactory.setFieldType("anzahl", AnzahlField.class);
        formFactory.setFieldType("stueckpreis", BetragField.class);

        formFactory.setFieldProvider("rechnung", new NativeSelectProvider<Rechnung>("Rechnung", rechnungDeltaspikeFacade.findAll(),
                item -> item.getId() + " " + item.getBezeichnung() + " " + item.getRechnungsdatum().toString()));

        formFactory.setButtonCaption(CrudOperation.ADD, "Neue Rechnungsposition erstellen");
        formFactory.setButtonCaption(CrudOperation.DELETE, "Rechnungsposition löschen");

        crud.setRowCountCaption("%d Rechnungspositionen gefunden");

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

        filterRechnung.setPlaceholder("Filter für Rechnung");
        filterRechnung.addValueChangeListener(e -> crud.getGrid().setItems(getItems()));
        filterRechnung.setItems(rechnungDeltaspikeFacade.findAll());
        filterRechnung.setItemCaptionGenerator(item -> item.getId() + " " + item.getBezeichnung() + " " + item.getRechnungstotal());


        Button clearFilterTextBtn = new Button(VaadinIcons.RECYCLE);
        clearFilterTextBtn.setDescription("Entferne Filter");
        clearFilterTextBtn.addClickListener(e -> {
            filterTextBezeichnung.clear();
            filterRechnung.clear();
        });

        filterToolbar.addComponents(filterTextBezeichnung, filterRechnung, clearFilterTextBtn);
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
                crud.getGrid().select(rechnungspositionDeltaspikeFacade.findBy(id));
            } else if (target.equals("rechnungId")) {
                filterRechnung.setValue(rechnungDeltaspikeFacade.findBy(id));
            }
        }
    }

    @Override
    public Collection<Rechnungsposition> findAll() {
        return getItems();
    }

    @Override
    public Rechnungsposition add(Rechnungsposition rechnungsposition) {
        return rechnungspositionDeltaspikeFacade.save(rechnungsposition);
    }

    @Override
    public Rechnungsposition update(Rechnungsposition rechnungsposition) {
        return rechnungspositionDeltaspikeFacade.save(rechnungsposition);
    }

    @Override
    public void delete(Rechnungsposition rechnungsposition) {
        rechnungspositionDeltaspikeFacade.delete(rechnungsposition);
    }
}
