package ch.internettechnik.anouman.presentation.ui.buchhaltung;

import ch.internettechnik.anouman.backend.entity.Buchhaltung;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.BuchhaltungDeltaspikeFacade;
import ch.internettechnik.anouman.presentation.ui.Menu;
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

@CDIView("BuchhaltungView")
public class BuchhaltungView extends VerticalLayout implements View, CrudListener<Buchhaltung> {
    private static Logger logger = LoggerFactory.getLogger(BuchhaltungView.class.getName());

    @Inject
    BuchhaltungDeltaspikeFacade buchhaltungDeltaspikeFacade;

    GridCrud<Buchhaltung> crud;
    CssLayout filterToolbar = new CssLayout();
    TextField filterTextBezeichnung = new TextField();


    private Collection<Buchhaltung> getItems() {
        if (!filterTextBezeichnung.isEmpty()) {
            //Suche mit Bezeichnung
            logger.debug("Suche mit Bezeichnung:" + filterTextBezeichnung.getValue());
            return buchhaltungDeltaspikeFacade
                    .findByBezeichnungLikeIgnoreCase(filterTextBezeichnung.getValue() + "%");
        }
        return buchhaltungDeltaspikeFacade.findAll();

    }

    private Crud createCrud() {
        crud = new GridCrud<Buchhaltung>(Buchhaltung.class, new WindowBasedCrudLayout());
        crud.setCrudListener(this);

        VerticalCrudFormFactory<Buchhaltung> formFactory = new VerticalCrudFormFactory<>(Buchhaltung.class);

        crud.setCrudFormFactory(formFactory);

        formFactory.setUseBeanValidation(true);

        formFactory.setErrorListener(e -> Notification.show("Custom error message (simulated error)", Notification.Type.ERROR_MESSAGE));

        formFactory.setVisibleProperties(CrudOperation.READ, "id", "bezeichnung", "jahr");
        formFactory.setVisibleProperties(CrudOperation.ADD, "id", "bezeichnung", "jahr");
        formFactory.setVisibleProperties(CrudOperation.UPDATE, "id", "bezeichnung", "jahr");
        formFactory.setVisibleProperties(CrudOperation.DELETE, "id", "bezeichnung", "jahr");

        formFactory.setDisabledProperties("id");

        crud.getGrid().setColumns("id", "bezeichnung", "jahr");

        crud.getGrid().addColumn(buchhaltung -> buchhaltung.getKontoklasse().size(), new ButtonRenderer(event -> {
            Buchhaltung buchhaltung = (Buchhaltung) event.getItem();
            UI.getCurrent().getNavigator().navigateTo("KontoklasseView/id/" + buchhaltung.getId().toString());
        })).setCaption("Kontoklasse").setStyleGenerator(item -> "v-align-center");

        //formFactory.setFieldType("anzahl", AnzahlField.class);
        //formFactory.setFieldType("stueckpreis", BetragField.class);
        formFactory.setButtonCaption(CrudOperation.ADD, "Neue Buchhaltung erstellen");
        formFactory.setButtonCaption(CrudOperation.DELETE, "Buchhaltung löschen");

        crud.setRowCountCaption("%d Buchhaltungen gefunden");

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

        Button clearFilterTextBtn = new Button(VaadinIcons.RECYCLE);
        clearFilterTextBtn.setDescription("Entferne Filter");
        clearFilterTextBtn.addClickListener(e -> {
            filterTextBezeichnung.clear();
        });

        filterToolbar.addComponents(filterTextBezeichnung, clearFilterTextBtn);
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
                crud.getGrid().select(buchhaltungDeltaspikeFacade.findBy(id));
            }
        }
    }

    @Override
    public Collection<Buchhaltung> findAll() {
        return getItems();
    }

    @Override
    public Buchhaltung add(Buchhaltung buchhaltung) {
        return buchhaltungDeltaspikeFacade.save(buchhaltung);
    }

    @Override
    public Buchhaltung update(Buchhaltung buchhaltung) {
        return buchhaltungDeltaspikeFacade.save(buchhaltung);
    }

    @Override
    public void delete(Buchhaltung buchhaltung) {
        buchhaltungDeltaspikeFacade.delete(buchhaltung);
    }
}
