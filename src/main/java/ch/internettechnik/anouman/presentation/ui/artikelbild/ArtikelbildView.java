package ch.internettechnik.anouman.presentation.ui.artikelbild;

import ch.internettechnik.anouman.backend.entity.Artikelbild;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ArtikelbildDeltaspikeFacade;
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

@CDIView("ArtikelbildView")
public class ArtikelbildView extends VerticalLayout implements View, CrudListener<Artikelbild> {
    private static Logger logger = LoggerFactory.getLogger(ArtikelbildView.class.getName());

    @Inject
    ArtikelbildDeltaspikeFacade artikelbildDeltaspikeFacade;

    GridCrud<Artikelbild> crud;
    CssLayout filterToolbar = new CssLayout();
    TextField filterTextTitel = new TextField();


    private Collection<Artikelbild> getItems() {
        if (!filterTextTitel.isEmpty()) {
            //Suche mit Bezeichnung
            logger.debug("Suche mit Titel:" + filterTextTitel.getValue());
            return artikelbildDeltaspikeFacade
                    .findByTitelLikeIgnoreCase(filterTextTitel.getValue() + "%");
        }
        return artikelbildDeltaspikeFacade.findAll();

    }

    private Crud createCrud() {
        crud = new GridCrud<Artikelbild>(Artikelbild.class, new WindowBasedCrudLayout());
        crud.setCrudListener(this);

        VerticalCrudFormFactory<Artikelbild> formFactory = new VerticalCrudFormFactory<>(Artikelbild.class);

        crud.setCrudFormFactory(formFactory);

        formFactory.setUseBeanValidation(true);

        formFactory.setErrorListener(e -> Notification.show("Custom error message (simulated error)", Notification.Type.ERROR_MESSAGE));

        formFactory.setVisibleProperties(CrudOperation.READ, "id", "titel", "mimetype");
        formFactory.setVisibleProperties(CrudOperation.ADD, "id", "titel", "mimetype");
        formFactory.setVisibleProperties(CrudOperation.UPDATE, "id", "titel", "mimetype");
        formFactory.setVisibleProperties(CrudOperation.DELETE, "id", "titel");

        formFactory.setDisabledProperties("id");

        crud.getGrid().setColumns("id", "titel", "mimetype");

        crud.getGrid().addColumn(artikelbild -> artikelbild.getArtikel().getId(), new ButtonRenderer(event -> {
            Artikelbild artikelbild = (Artikelbild) event.getItem();
            UI.getCurrent().getNavigator().navigateTo("ArtikelView/id/" + artikelbild.getArtikel().getId().toString());
        })).setCaption("Artikel").setStyleGenerator(item -> "v-align-center");

        //formFactory.setFieldType("anzahl", AnzahlField.class);
        //formFactory.setFieldType("stueckpreis", BetragField.class);
        formFactory.setButtonCaption(CrudOperation.ADD, "Neues Artikelbild erstellen");
        formFactory.setButtonCaption(CrudOperation.DELETE, "Artikelbild löschen");

        crud.setRowCountCaption("%d Artikelbilder gefunden");

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

        Button clearFilterTextBtn = new Button(VaadinIcons.RECYCLE);
        clearFilterTextBtn.setDescription("Entferne Filter");
        clearFilterTextBtn.addClickListener(e -> {
            filterTextTitel.clear();
        });

        filterToolbar.addComponents(filterTextTitel, clearFilterTextBtn);
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
                crud.getGrid().select(artikelbildDeltaspikeFacade.findBy(id));
            }
        }
    }

    @Override
    public Collection<Artikelbild> findAll() {
        return getItems();
    }

    @Override
    public Artikelbild add(Artikelbild artikelbild) {
        return artikelbildDeltaspikeFacade.save(artikelbild);
    }

    @Override
    public Artikelbild update(Artikelbild artikelbild) {
        return artikelbildDeltaspikeFacade.save(artikelbild);
    }

    @Override
    public void delete(Artikelbild artikelbild) {
        artikelbildDeltaspikeFacade.delete(artikelbild);
    }
}
