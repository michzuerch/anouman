package ch.internettechnik.anouman.presentation.ui.uzerrole;

import ch.internettechnik.anouman.backend.entity.Uzer;
import ch.internettechnik.anouman.backend.entity.UzerRole;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.UzerDeltaspikeFacade;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.UzerRoleDeltaspikeFacade;
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

@CDIView("UzerRoleView")
public class UzerRoleView extends VerticalLayout implements View, CrudListener<UzerRole> {
    private static Logger logger = LoggerFactory.getLogger(UzerRoleView.class.getName());

    @Inject
    UzerRoleDeltaspikeFacade uzerRoleDeltaspikeFacade;

    @Inject
    UzerDeltaspikeFacade uzerDeltaspikeFacade;

    GridCrud<UzerRole> crud;
    CssLayout filterToolbar = new CssLayout();
    TextField filterTextRole = new TextField();
    ComboBox<Uzer> filterUzer = new ComboBox<>();


    private Collection<UzerRole> getItems() {
        if ((!filterUzer.isEmpty()) && (!filterTextRole.isEmpty())) {
            // Such mit Rechnung und Bezeichnung
            logger.debug("Suche mit Uzer und Role:" + filterUzer.getValue().getId() + "," + filterTextRole.getValue());
            return (uzerRoleDeltaspikeFacade.findByUzerAndRoleLikeIgnoreCase(filterUzer.getValue(), filterTextRole.getValue() + "%"));

        } else if ((!filterUzer.isEmpty()) && (filterTextRole.isEmpty())) {
            // Suche mit Rechnung
            logger.debug("Suche mit Uzer:" + filterUzer.getValue().getId());
            return (uzerRoleDeltaspikeFacade.findByUzer(filterUzer.getValue()));
        } else if ((filterUzer.isEmpty()) && (!filterTextRole.isEmpty())) {
            // Suche mit Bezeichnung
            logger.debug("Suche mit Role:" + filterTextRole.getValue());
            return (uzerRoleDeltaspikeFacade.findByRoleLikeIgnoreCase(filterTextRole.getValue() + "%"));
        }
        return (uzerRoleDeltaspikeFacade.findAll());
    }

    private Crud createCrud() {
        crud = new GridCrud<UzerRole>(UzerRole.class, new WindowBasedCrudLayout());
        crud.setCrudListener(this);

        VerticalCrudFormFactory<UzerRole> formFactory = new VerticalCrudFormFactory<>(UzerRole.class);

        crud.setCrudFormFactory(formFactory);

        formFactory.setUseBeanValidation(true);

        formFactory.setErrorListener(e -> Notification.show("Custom error message (simulated error)", Notification.Type.ERROR_MESSAGE));

        formFactory.setVisibleProperties(CrudOperation.READ, "id", "role");
        formFactory.setVisibleProperties(CrudOperation.ADD, "id", "role");
        formFactory.setVisibleProperties(CrudOperation.UPDATE, "id", "role");
        formFactory.setVisibleProperties(CrudOperation.DELETE, "id", "role");

        formFactory.setDisabledProperties("id");

        crud.getGrid().setColumns("id", "role");

        crud.getGrid().addColumn(uzerRole -> uzerRole.getAnzahlUzers(), new ButtonRenderer(event -> {
            UzerRole uzerRole = (UzerRole) event.getItem();
            //@todo ManyToMany für Roles
            UI.getCurrent().getNavigator().navigateTo("UzerView/id/");
        })).setCaption("User Role").setStyleGenerator(item -> "v-align-center");

        formFactory.setButtonCaption(CrudOperation.ADD, "Neue User Role erstellen");
        formFactory.setButtonCaption(CrudOperation.DELETE, "User Role löschen");

        crud.setRowCountCaption("%d User Roles gefunden");

        crud.getCrudLayout().addToolbarComponent(filterToolbar);
        crud.setClickRowToUpdate(false);
        crud.setUpdateOperationVisible(true);
        crud.setDeleteOperationVisible(true);

        return crud;
    }

    @PostConstruct
    void init() {
        filterTextRole.setPlaceholder("Filter für Role");
        filterTextRole.addValueChangeListener(e -> crud.getGrid().setItems(getItems()));
        filterTextRole.setValueChangeMode(ValueChangeMode.LAZY);

        filterUzer.setPlaceholder("Filter für User");
        filterUzer.addValueChangeListener(e -> crud.getGrid().setItems(getItems()));
        filterUzer.setItems(uzerDeltaspikeFacade.findAll());
        filterUzer.setItemCaptionGenerator(item -> item.getId() + " " + item.getPrincipal());


        Button clearFilterTextBtn = new Button(VaadinIcons.RECYCLE);
        clearFilterTextBtn.setDescription("Entferne Filter");
        clearFilterTextBtn.addClickListener(e -> {
            filterTextRole.clear();
            filterUzer.clear();
        });

        filterToolbar.addComponents(filterTextRole, filterUzer, clearFilterTextBtn);
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
                crud.getGrid().select(uzerRoleDeltaspikeFacade.findBy(id));
            }
        }
    }

    @Override
    public Collection<UzerRole> findAll() {
        return getItems();
    }

    @Override
    public UzerRole add(UzerRole uzerRole) {
        return uzerRoleDeltaspikeFacade.save(uzerRole);
    }

    @Override
    public UzerRole update(UzerRole uzerRole) {
        return uzerRoleDeltaspikeFacade.save(uzerRole);
    }

    @Override
    public void delete(UzerRole uzerRole) {
        uzerRoleDeltaspikeFacade.delete(uzerRole);
    }
}
