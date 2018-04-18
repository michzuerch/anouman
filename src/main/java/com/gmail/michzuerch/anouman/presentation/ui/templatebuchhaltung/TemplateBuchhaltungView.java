package com.gmail.michzuerch.anouman.presentation.ui.templatebuchhaltung;

import com.gmail.michzuerch.anouman.backend.entity.TemplateBuchhaltung;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.TemplateBuchhaltungDeltaspikeFacade;
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

import javax.inject.Inject;
import java.util.Collection;

@CDIView("TemplateBuchhaltungView")
public class TemplateBuchhaltungView extends VerticalLayout implements View, CrudListener<TemplateBuchhaltung> {
    private static Logger logger = LoggerFactory.getLogger(TemplateBuchhaltungView.class.getName());

    @Inject
    TemplateBuchhaltungDeltaspikeFacade templateBuchhaltungDeltaspikeFacade;

    GridCrud<TemplateBuchhaltung> crud;
    CssLayout filterToolbar = new CssLayout();
    TextField filterTextBezeichnung = new TextField();


    private Collection<TemplateBuchhaltung> getItems() {
        if (!filterTextBezeichnung.isEmpty()) {
            //Suche mit Bezeichnung
            logger.debug("Suche mit Bezeichnung:" + filterTextBezeichnung.getValue());
            return templateBuchhaltungDeltaspikeFacade
                    .findByBezeichnungLikeIgnoreCase(filterTextBezeichnung.getValue() + "%");
        }
        return templateBuchhaltungDeltaspikeFacade.findAll();

    }

    private Crud createCrud() {
        crud = new GridCrud<TemplateBuchhaltung>(TemplateBuchhaltung.class, new WindowBasedCrudLayout());
        crud.setCrudListener(this);

        VerticalCrudFormFactory<TemplateBuchhaltung> formFactory = new VerticalCrudFormFactory<>(TemplateBuchhaltung.class);

        crud.setCrudFormFactory(formFactory);

        formFactory.setUseBeanValidation(true);

        formFactory.setErrorListener(e -> Notification.show("Custom error message (simulated error)", Notification.Type.ERROR_MESSAGE));

        formFactory.setVisibleProperties(CrudOperation.READ, "id", "bezeichnung");
        formFactory.setVisibleProperties(CrudOperation.ADD, "id", "bezeichnung");
        formFactory.setVisibleProperties(CrudOperation.UPDATE, "id", "bezeichnung");
        formFactory.setVisibleProperties(CrudOperation.DELETE, "id", "bezeichnung");

        formFactory.setDisabledProperties("id");

        crud.getGrid().setColumns("id", "bezeichnung");

        crud.getGrid().addColumn(templatebuchhaltung -> templatebuchhaltung.getTemplateKontoklasses().size(), new ButtonRenderer(event -> {
            TemplateBuchhaltung templateBuchhaltung = (TemplateBuchhaltung) event.getItem();
            UI.getCurrent().getNavigator().navigateTo("TemplateKontoklasseView/id/" + templateBuchhaltung.getId().toString());
        })).setCaption("Template Kontoklasse").setStyleGenerator(item -> "v-align-center");

        //formFactory.setFieldType("anzahl", AnzahlField.class);
        //formFactory.setFieldType("stueckpreis", BetragField.class);
        formFactory.setButtonCaption(CrudOperation.ADD, "Neue Template Buchhaltung erstellen");
        formFactory.setButtonCaption(CrudOperation.DELETE, "Template Buchhaltung löschen");

        crud.setRowCountCaption("%d Template Buchhaltungen gefunden");

        crud.getCrudLayout().addToolbarComponent(filterToolbar);
        crud.setClickRowToUpdate(false);
        crud.setUpdateOperationVisible(true);
        crud.setDeleteOperationVisible(true);

        return crud;
    }

    private void createContent() {
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

        crud.setSizeFull();
        setMargin(false);
        setSpacing(false);
        addComponents(filterToolbar, crud);
        setExpandRatio(crud, 1);
        setSizeFull();
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        createContent();
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
                crud.getGrid().select(templateBuchhaltungDeltaspikeFacade.findBy(id));
            }
        }
    }

    @Override
    public Collection<TemplateBuchhaltung> findAll() {
        return getItems();
    }

    @Override
    public TemplateBuchhaltung add(TemplateBuchhaltung templateBuchhaltung) {
        return templateBuchhaltungDeltaspikeFacade.save(templateBuchhaltung);
    }

    @Override
    public TemplateBuchhaltung update(TemplateBuchhaltung templateBuchhaltung) {
        return templateBuchhaltungDeltaspikeFacade.save(templateBuchhaltung);
    }

    @Override
    public void delete(TemplateBuchhaltung templateBuchhaltung) {
        templateBuchhaltungDeltaspikeFacade.delete(templateBuchhaltung);
    }
}
