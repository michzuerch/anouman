package ch.internettechnik.anouman.presentation.ui.artikel;

import ch.internettechnik.anouman.backend.entity.Artikel;
import ch.internettechnik.anouman.backend.entity.Artikelkategorie;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ArtikelDeltaspikeFacade;
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

@CDIView("ArtikelView")
public class ArtikelView extends VerticalLayout implements View, CrudListener<Artikel> {
    private static Logger logger = LoggerFactory.getLogger(ArtikelView.class.getName());

    @Inject
    ArtikelDeltaspikeFacade artikelDeltaspikeFacade;

    @Inject
    ArtikelkategorieDeltaspikeFacade artikelkategorieDeltaspikeFacade;

    GridCrud<Artikel> crud;
    CssLayout filterToolbar = new CssLayout();
    TextField filterTextBezeichnung = new TextField();
    ComboBox<Artikelkategorie> filterArtikelkategorie = new ComboBox<>();


    private Collection<Artikel> getItems() {
        if ((!filterArtikelkategorie.isEmpty()) && (!filterTextBezeichnung.isEmpty())) {
            // Such mit Rechnung und Bezeichnung
            logger.debug("Suche mit Artikelkategorie und Bezeichnung:" + filterArtikelkategorie.getValue().getId() + "," + filterTextBezeichnung.getValue());
            return (artikelDeltaspikeFacade.findByArtikelkategorieAndBezeichnungLikeIgnoreCase(filterArtikelkategorie.getValue(), filterTextBezeichnung.getValue() + "%"));

        } else if ((!filterArtikelkategorie.isEmpty()) && (filterTextBezeichnung.isEmpty())) {
            // Suche mit Rechnung
            logger.debug("Suche mit Artikelkategorie:" + filterArtikelkategorie.getValue().getId());
            return (artikelDeltaspikeFacade.findByArtikelkategorie(filterArtikelkategorie.getValue()));
        } else if ((filterArtikelkategorie.isEmpty()) && (!filterTextBezeichnung.isEmpty())) {
            // Suche mit Bezeichnung
            logger.debug("Suche mit Bezeichnung:" + filterTextBezeichnung.getValue());
            return (artikelDeltaspikeFacade.findByBezeichnungLikeIgnoreCase(filterTextBezeichnung.getValue() + "%"));
        }
        return (artikelDeltaspikeFacade.findAll());
    }

    private Crud createCrud() {
        crud = new GridCrud<Artikel>(Artikel.class, new WindowBasedCrudLayout());
        crud.setCrudListener(this);

        VerticalCrudFormFactory<Artikel> formFactory = new VerticalCrudFormFactory<>(Artikel.class);

        crud.setCrudFormFactory(formFactory);

        formFactory.setUseBeanValidation(true);

        formFactory.setErrorListener(e -> Notification.show("Custom error message (simulated error)", Notification.Type.ERROR_MESSAGE));

        formFactory.setVisibleProperties(CrudOperation.READ, "id", "bezeichnung", "bezeichnungLang", "mengeneinheit", "anzahl", "stueckpreis");
        formFactory.setVisibleProperties(CrudOperation.ADD, "id", "bezeichnung", "bezeichnungLang", "mengeneinheit", "anzahl", "stueckpreis");
        formFactory.setVisibleProperties(CrudOperation.UPDATE, "id", "bezeichnung", "bezeichnungLang", "mengeneinheit", "anzahl", "stueckpreis");
        formFactory.setVisibleProperties(CrudOperation.DELETE, "id", "bezeichnung");

        formFactory.setDisabledProperties("id");

        crud.getGrid().setColumns("id", "bezeichnung", "bezeichnungLang", "mengeneinheit", "anzahl", "stueckpreis");

        crud.getGrid().addColumn(artikel -> artikel.getArtikelkategorie().getId(), new ButtonRenderer(event -> {
            Artikel artikel = (Artikel) event.getItem();
            UI.getCurrent().getNavigator().navigateTo("ArtikelkategorieView/id/" + artikel.getArtikelkategorie().getId().toString());
        })).setCaption("Artikelkategorie").setStyleGenerator(item -> "v-align-center");

        crud.getGrid().addColumn(artikel -> artikel.getArtikelkategorie().getId(), new ButtonRenderer(event -> {
            Artikel artikel = (Artikel) event.getItem();
            UI.getCurrent().getNavigator().navigateTo("ArtikelbildView/id/" + artikel.getArtikelkategorie().getId().toString());
        })).setCaption("Artikelbild").setStyleGenerator(item -> "v-align-center");

        formFactory.setFieldType("anzahl", AnzahlField.class);
        formFactory.setFieldType("stueckpreis", BetragField.class);
        formFactory.setButtonCaption(CrudOperation.ADD, "Neuen Artikel erstellen");
        formFactory.setButtonCaption(CrudOperation.DELETE, "Artikel löschen");

        crud.setRowCountCaption("%d Artikel gefunden");

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

        filterArtikelkategorie.setPlaceholder("Filter für Artikelkategorie");
        filterArtikelkategorie.addValueChangeListener(e -> crud.getGrid().setItems(getItems()));
        filterArtikelkategorie.setItems(artikelkategorieDeltaspikeFacade.findAll());
        filterArtikelkategorie.setItemCaptionGenerator(item -> item.getId() + " " + item.getBezeichnung() + " " + item.getRechnungstotal());

        Button clearFilterTextBtn = new Button(VaadinIcons.RECYCLE);
        clearFilterTextBtn.setDescription("Entferne Filter");
        clearFilterTextBtn.addClickListener(e -> {
            filterTextBezeichnung.clear();
            filterArtikelkategorie.clear();
        });

        filterToolbar.addComponents(filterTextBezeichnung, filterArtikelkategorie, clearFilterTextBtn);
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
                crud.getGrid().select(artikelDeltaspikeFacade.findBy(id));
            }
        }
    }

    @Override
    public Collection<Artikel> findAll() {
        return getItems();
    }

    @Override
    public Artikel add(Artikel artikel) {
        return artikelDeltaspikeFacade.save(artikel);
    }

    @Override
    public Artikel update(Artikel artikel) {
        return artikelDeltaspikeFacade.save(artikel);
    }

    @Override
    public void delete(Artikel artikel) {
        artikelDeltaspikeFacade.delete(artikel);
    }
}
