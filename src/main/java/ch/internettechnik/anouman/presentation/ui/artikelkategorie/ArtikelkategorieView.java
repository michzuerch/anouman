package ch.internettechnik.anouman.presentation.ui.artikelkategorie;

import ch.internettechnik.anouman.backend.entity.Artikelkategorie;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ArtikelkategorieDeltaspikeFacade;
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
import org.vaadin.crudui.form.impl.form.factory.VerticalCrudFormFactory;
import org.vaadin.crudui.layout.impl.WindowBasedCrudLayout;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Collection;

@CDIView("ArtikelkategorieView")
public class ArtikelkategorieView extends VerticalLayout implements View, CrudListener<Artikelkategorie> {
    private static Logger logger = LoggerFactory.getLogger(ArtikelkategorieView.class.getName());

    @Inject
    ArtikelkategorieDeltaspikeFacade artikelkategorieDeltaspikeFacade;

    GridCrud<Artikelkategorie> crud;
    CssLayout filterToolbar = new CssLayout();
    TextField filterTextBezeichnung = new TextField();


    private Collection<Artikelkategorie> getItems() {
        if (!filterTextBezeichnung.isEmpty()) {
            //Suche mit Bezeichnung
            logger.debug("Suche mit Bezeichnung:" + filterTextBezeichnung.getValue());
            return artikelkategorieDeltaspikeFacade
                    .findByBezeichnungLikeIgnoreCase(filterTextBezeichnung.getValue() + "%");
        }
        return artikelkategorieDeltaspikeFacade.findAll();

    }

    private Crud createCrud() {
        crud = new GridCrud<Artikelkategorie>(Artikelkategorie.class, new WindowBasedCrudLayout());
        crud.setCrudListener(this);

        VerticalCrudFormFactory<Artikelkategorie> formFactory = new VerticalCrudFormFactory<>(Artikelkategorie.class);

        crud.setCrudFormFactory(formFactory);

        formFactory.setUseBeanValidation(true);

        formFactory.setErrorListener(e -> Notification.show("Custom error message (simulated error)", Notification.Type.ERROR_MESSAGE));

        formFactory.setVisibleProperties(CrudOperation.READ, "id", "bezeichnung");
        formFactory.setVisibleProperties(CrudOperation.ADD, "id", "bezeichnung");
        formFactory.setVisibleProperties(CrudOperation.UPDATE, "id", "bezeichnung");
        formFactory.setVisibleProperties(CrudOperation.DELETE, "id", "bezeichnung");

        formFactory.setDisabledProperties("id");

        crud.getGrid().setColumns("id", "bezeichnung");

        crud.getGrid().addColumn(artikelkategorie -> artikelkategorie.getArtikels().size(), new ButtonRenderer(event -> {
            Artikelkategorie artikelkategorie = (Artikelkategorie) event.getItem();
            UI.getCurrent().getNavigator().navigateTo("ArtikelView/artikelkategorieId/" + artikelkategorie.getId().toString());
        })).setCaption("Anzahl Artikel").setStyleGenerator(item -> "v-align-center");

        formFactory.setFieldType("anzahl", AnzahlField.class);
        formFactory.setFieldType("stueckpreis", BetragField.class);
        formFactory.setButtonCaption(CrudOperation.ADD, "Neue Artikelkategorie erstellen");
        formFactory.setButtonCaption(CrudOperation.DELETE, "Artikelkategorie löschen");

        crud.setRowCountCaption("%d Artikelkategorien gefunden");

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
                crud.getGrid().select(artikelkategorieDeltaspikeFacade.findBy(id));
            }
        }
    }

    @Override
    public Collection<Artikelkategorie> findAll() {
        return getItems();
    }

    @Override
    public Artikelkategorie add(Artikelkategorie artikelkategorie) {
        return artikelkategorieDeltaspikeFacade.save(artikelkategorie);
    }

    @Override
    public Artikelkategorie update(Artikelkategorie artikelkategorie) {
        return artikelkategorieDeltaspikeFacade.save(artikelkategorie);
    }

    @Override
    public void delete(Artikelkategorie artikelkategorie) {
        artikelkategorieDeltaspikeFacade.delete(artikelkategorie);
    }
}
