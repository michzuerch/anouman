package ch.internettechnik.anouman.presentation.ui.uzer;

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

@CDIView("UzerView")
public class UzerView extends VerticalLayout implements View, CrudListener<Uzer> {
    private static Logger logger = LoggerFactory.getLogger(UzerView.class.getName());

    @Inject
    UzerDeltaspikeFacade uzerDeltaspikeFacade;

    @Inject
    UzerRoleDeltaspikeFacade uzerRoleDeltaspikeFacade;

    GridCrud<Uzer> crud;
    CssLayout filterToolbar = new CssLayout();
    TextField filterTextPrincipal = new TextField();
    ComboBox<UzerRole> filterUzerRole = new ComboBox<>();


    private Collection<Uzer> getItems() {
        if ((!filterUzerRole.isEmpty()) && (!filterTextPrincipal.isEmpty())) {
            // Such mit Rechnung und Bezeichnung
            logger.debug("Suche mit UzerRole und Principal:" + filterUzerRole.getValue().getId() + "," + filterTextPrincipal.getValue());
            return (uzerDeltaspikeFacade.findByUzerRoleAndPrincipalLikeIgnoreCase(filterUzerRole.getValue(), filterTextPrincipal.getValue() + "%"));

        } else if ((!filterUzerRole.isEmpty()) && (filterTextPrincipal.isEmpty())) {
            // Suche mit Rechnung
            logger.debug("Suche mit Rechnung:" + filterUzerRole.getValue().getId());
            return (uzerDeltaspikeFacade.findByUzerRole(filterUzerRole.getValue()));
        } else if ((filterUzerRole.isEmpty()) && (!filterTextPrincipal.isEmpty())) {
            // Suche mit Bezeichnung
            logger.debug("Suche mit Principal:" + filterTextPrincipal.getValue());
            return (uzerDeltaspikeFacade.findByPrincipalLikeIgnoreCase(filterTextPrincipal.getValue() + "%"));
        }
        return (uzerDeltaspikeFacade.findAll());
    }

    private Crud createCrud() {
        crud = new GridCrud<Uzer>(Uzer.class, new WindowBasedCrudLayout());
        crud.setCrudListener(this);

        VerticalCrudFormFactory<Uzer> formFactory = new VerticalCrudFormFactory<>(Uzer.class);

        crud.setCrudFormFactory(formFactory);

        formFactory.setUseBeanValidation(true);

        formFactory.setErrorListener(e -> Notification.show("Custom error message (simulated error)", Notification.Type.ERROR_MESSAGE));

        formFactory.setVisibleProperties(CrudOperation.READ, "id", "principal", "description");
        formFactory.setVisibleProperties(CrudOperation.ADD, "id", "principal", "description");
        formFactory.setVisibleProperties(CrudOperation.UPDATE, "id", "principal", "description");
        formFactory.setVisibleProperties(CrudOperation.DELETE, "id", "principal");

        formFactory.setDisabledProperties("id");

        crud.getGrid().setColumns("id", "principal", "description");

        crud.getGrid().addColumn(uzer -> uzer.getAnzahlUzerRoles(), new ButtonRenderer(event -> {
            Uzer uzer = (Uzer) event.getItem();
            //@todo ManyToMany für Roles
            UI.getCurrent().getNavigator().navigateTo("UzerRoleOldView/id/");
        })).setCaption("User Role").setStyleGenerator(item -> "v-align-center");

        formFactory.setButtonCaption(CrudOperation.ADD, "Neuen User erstellen");
        formFactory.setButtonCaption(CrudOperation.DELETE, "User löschen");

        crud.setRowCountCaption("%d User gefunden");

        crud.getCrudLayout().addToolbarComponent(filterToolbar);
        crud.setClickRowToUpdate(false);
        crud.setUpdateOperationVisible(true);
        crud.setDeleteOperationVisible(true);

        return crud;
    }

    @PostConstruct
    void init() {
        filterTextPrincipal.setPlaceholder("Filter für Principal");
        filterTextPrincipal.addValueChangeListener(e -> crud.getGrid().setItems(getItems()));
        filterTextPrincipal.setValueChangeMode(ValueChangeMode.LAZY);

        filterUzerRole.setPlaceholder("Filter für User Role");
        filterUzerRole.addValueChangeListener(e -> crud.getGrid().setItems(getItems()));
        filterUzerRole.setItems(uzerRoleDeltaspikeFacade.findAll());
        filterUzerRole.setItemCaptionGenerator(item -> item.getId() + " " + item.getRole());


        Button clearFilterTextBtn = new Button(VaadinIcons.RECYCLE);
        clearFilterTextBtn.setDescription("Entferne Filter");
        clearFilterTextBtn.addClickListener(e -> {
            filterTextPrincipal.clear();
            filterUzerRole.clear();
        });

        filterToolbar.addComponents(filterTextPrincipal, filterUzerRole, clearFilterTextBtn);
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
                crud.getGrid().select(uzerDeltaspikeFacade.findBy(id));
            }
        }
    }

    @Override
    public Collection<Uzer> findAll() {
        return getItems();
    }

    @Override
    public Uzer add(Uzer uzer) {
        return uzerDeltaspikeFacade.save(uzer);
    }

    @Override
    public Uzer update(Uzer uzer) {
        return uzerDeltaspikeFacade.save(uzer);
    }

    @Override
    public void delete(Uzer uzer) {
        uzerDeltaspikeFacade.delete(uzer);
    }
}
