package com.gmail.michzuerch.anouman.presentation.ui.templatebuchhaltung.templatemehrwertsteuercode;

import com.gmail.michzuerch.anouman.backend.entity.TemplateBuchhaltung;
import com.gmail.michzuerch.anouman.backend.entity.TemplateMehrwertsteuercode;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.TemplateBuchhaltungDeltaspikeFacade;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.TemplateMehrwertsteuercodeDeltaspikeFacade;
import com.gmail.michzuerch.anouman.presentation.ui.field.ProzentField;
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
import org.vaadin.teemusa.flexlayout.*;

import javax.inject.Inject;
import java.util.Collection;

@CDIView("TemplateMehrwertsteuercodeView")
public class TemplateMehrwertsteuercodeView extends VerticalLayout implements View, CrudListener<TemplateMehrwertsteuercode> {
    private static Logger logger = LoggerFactory.getLogger(TemplateMehrwertsteuercodeView.class.getName());

    @Inject
    TemplateMehrwertsteuercodeDeltaspikeFacade templateMehrwertsteuercodeDeltaspikeFacade;

    @Inject
    TemplateBuchhaltungDeltaspikeFacade templateBuchhaltungDeltaspikeFacade;

    GridCrud<TemplateMehrwertsteuercode> crud;
    CssLayout filterToolbar = new CssLayout();
    TextField filterTextBezeichnung = new TextField();
    ComboBox<TemplateBuchhaltung> filterTemplateBuchhaltung = new ComboBox<>();

    private Collection<TemplateMehrwertsteuercode> getItems() {
        if (!filterTemplateBuchhaltung.isEmpty()) {
            logger.debug("Suche mit Template Buchhaltung");
            return templateMehrwertsteuercodeDeltaspikeFacade.findByTemplateBuchhaltung(filterTemplateBuchhaltung.getValue());
        } else if (!filterTextBezeichnung.isEmpty()) {
            //Suche mit Bezeichnung
            logger.debug("Suche mit Bezeichnung:" + filterTextBezeichnung.getValue());
            return templateMehrwertsteuercodeDeltaspikeFacade.findByBezeichnungLikeIgnoreCase(filterTextBezeichnung.getValue() + "%");
        } else
            return templateMehrwertsteuercodeDeltaspikeFacade.findAll();
    }

    private Crud createCrud() {
        crud = new GridCrud<TemplateMehrwertsteuercode>(TemplateMehrwertsteuercode.class, new WindowBasedCrudLayout());
        crud.setCrudListener(this);

        VerticalCrudFormFactory<TemplateMehrwertsteuercode> formFactory = new VerticalCrudFormFactory<>(TemplateMehrwertsteuercode.class);

        crud.setCrudFormFactory(formFactory);

        formFactory.setUseBeanValidation(true);

        formFactory.setErrorListener(e -> Notification.show("Custom error message (simulated error)", Notification.Type.ERROR_MESSAGE));

        formFactory.setVisibleProperties(CrudOperation.READ, "id", "bezeichnung", "code", "prozent", "verkauf", "templateBuchhaltung", "templateMehrwertsteuerKonto");
        formFactory.setVisibleProperties(CrudOperation.ADD, "id", "bezeichnung", "code", "prozent", "verkauf", "templateBuchhaltung", "templateMehrwertsteuerKonto");
        formFactory.setVisibleProperties(CrudOperation.UPDATE, "id", "bezeichnung", "code", "prozent", "verkauf", "templateBuchhaltung", "templateMehrwertsteuerKonto");
        formFactory.setVisibleProperties(CrudOperation.DELETE, "id", "bezeichnung");

        formFactory.setDisabledProperties("id");

        crud.getGrid().setColumns("id", "bezeichnung", "code", "prozent", "verkauf");

        crud.getGrid().addColumn(templateMehrwertsteuercode ->
                        templateMehrwertsteuercode.getTemplateBuchhaltung().getId() + " " +
                                templateMehrwertsteuercode.getTemplateBuchhaltung().getBezeichnung(),
                new ButtonRenderer(event -> {
                    TemplateMehrwertsteuercode templateMehrwertsteuercode = (TemplateMehrwertsteuercode) event.getItem();
                    UI.getCurrent().getNavigator().navigateTo("TemplateBuchhaltungView/id/" + templateMehrwertsteuercode.getTemplateBuchhaltung().getId().toString());
                })).setCaption("Template Buchhaltung").setStyleGenerator(item -> "v-align-center");

        crud.getGrid().addColumn(templateMehrwertsteuercode ->
                        templateMehrwertsteuercode.getTemplateMehrwertsteuerKonto().getBezeichnung() + " " +
                                templateMehrwertsteuercode.getTemplateMehrwertsteuerKonto().getId(),
                new ButtonRenderer<>(event -> {
                    TemplateMehrwertsteuercode templateMehrwertsteuercode = event.getItem();
                    UI.getCurrent().getNavigator().navigateTo("TemplateKontoView/id/" + templateMehrwertsteuercode.getTemplateMehrwertsteuerKonto().getId());
                })).setCaption("Konto Mehrwertsteuer").setStyleGenerator(item -> "v-align-center");

        //formFactory.setFieldType("anzahl", AnzahlField.class);
        formFactory.setFieldType("prozent", ProzentField.class);

        formFactory.setFieldProvider("templateBuchhaltung",
                new NativeSelectProvider<TemplateBuchhaltung>("Template Buchhaltung", templateBuchhaltungDeltaspikeFacade.findAll(),
                        item -> item.getId() + " " + item.getBezeichnung()));


        formFactory.setButtonCaption(CrudOperation.ADD, "Neuen Template Mehrwertsteuercode erstellen");
        formFactory.setButtonCaption(CrudOperation.DELETE, "Template Mehrwertsteuercode löschen");

        crud.setRowCountCaption("%d Template Mehrwertsteuercodes gefunden");

        crud.getCrudLayout().addToolbarComponent(filterToolbar);
        crud.setClickRowToUpdate(false);
        crud.setUpdateOperationVisible(true);
        crud.setDeleteOperationVisible(true);

        return crud;
    }


    private Component createContent() {
        FlexLayout layout = new FlexLayout();

        layout.setFlexDirection(FlexDirection.Row);
        layout.setAlignItems(AlignItems.FlexEnd);
        layout.setJustifyContent(JustifyContent.SpaceBetween);
        layout.setAlignContent(AlignContent.Stretch);
        layout.setFlexWrap(FlexWrap.Wrap);

        filterTextBezeichnung.setPlaceholder("Filter für Bezeichnung");
        filterTextBezeichnung.addValueChangeListener(e -> crud.getGrid().setItems(getItems()));
        filterTextBezeichnung.setValueChangeMode(ValueChangeMode.LAZY);

        filterTemplateBuchhaltung.setPlaceholder("Filter für Template Buchhaltung");
        filterTemplateBuchhaltung.setItems(templateBuchhaltungDeltaspikeFacade.findAll());
        filterTemplateBuchhaltung.setItemCaptionGenerator(item -> item.getBezeichnung() + " id:" + item.getId());
        filterTemplateBuchhaltung.addValueChangeListener(e -> crud.getGrid().setItems(getItems()));

        Button clearFilterTextBtn = new Button(VaadinIcons.RECYCLE);
        clearFilterTextBtn.setDescription("Entferne Filter");
        clearFilterTextBtn.addClickListener(e -> {
            filterTextBezeichnung.clear();
            filterTemplateBuchhaltung.clear();
        });

        filterToolbar.addComponents(filterTextBezeichnung, filterTemplateBuchhaltung, clearFilterTextBtn);
        filterToolbar.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        layout.addComponents(filterToolbar, createCrud());
        layout.setSizeFull();
        return layout;

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        addComponent(createContent());
        setSizeFull();
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
                crud.getGrid().select(templateMehrwertsteuercodeDeltaspikeFacade.findBy(id));
            }
        }

    }

    @Override
    public Collection<TemplateMehrwertsteuercode> findAll() {
        return getItems();
    }

    @Override
    public TemplateMehrwertsteuercode add(TemplateMehrwertsteuercode templateMehrwertsteuercode) {
        return templateMehrwertsteuercodeDeltaspikeFacade.save(templateMehrwertsteuercode);
    }

    @Override
    public TemplateMehrwertsteuercode update(TemplateMehrwertsteuercode templateMehrwertsteuercode) {
        return templateMehrwertsteuercodeDeltaspikeFacade.save(templateMehrwertsteuercode);
    }

    @Override
    public void delete(TemplateMehrwertsteuercode templateMehrwertsteuercode) {
        templateMehrwertsteuercodeDeltaspikeFacade.delete(templateMehrwertsteuercode);
    }
}
