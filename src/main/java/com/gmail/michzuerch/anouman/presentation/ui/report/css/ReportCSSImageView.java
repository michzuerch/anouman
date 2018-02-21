package com.gmail.michzuerch.anouman.presentation.ui.report.css;

import com.gmail.michzuerch.anouman.backend.entity.report.css.ReportCSSImage;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.ReportCSSImageDeltaspikeFacade;
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
import org.vaadin.teemusa.flexlayout.*;

import javax.inject.Inject;
import java.util.Collection;


//@todo Umstellung auf Form
@CDIView("ReportCSSImageView")
public class ReportCSSImageView extends VerticalLayout implements View, CrudListener<ReportCSSImage> {
    private static Logger logger = LoggerFactory.getLogger(ReportCSSImageView.class.getName());

    @Inject
    ReportCSSImageDeltaspikeFacade facade;

    GridCrud<ReportCSSImage> crud;
    CssLayout filterToolbar = new CssLayout();
    TextField filterTextBezeichnung = new TextField();

    private Collection<ReportCSSImage> getItems() {
        if (!filterTextBezeichnung.isEmpty()) {
            //Suche mit Bezeichnung
            logger.debug("Suche mit Bezeichnung:" + filterTextBezeichnung.getValue());
            return facade.findByBezeichnungLikeIgnoreCase(filterTextBezeichnung.getValue() + "%");
        }
        return facade.findAll();
    }

    private Crud createCrud() {
        crud = new GridCrud<ReportCSSImage>(ReportCSSImage.class, new WindowBasedCrudLayout());
        crud.setCrudListener(this);

        VerticalCrudFormFactory<ReportCSSImage> formFactory = new VerticalCrudFormFactory<>(ReportCSSImage.class);

        crud.setCrudFormFactory(formFactory);

        formFactory.setUseBeanValidation(true);

        formFactory.setErrorListener(e -> Notification.show("Custom error message (simulated error)", Notification.Type.ERROR_MESSAGE));

        formFactory.setVisibleProperties(CrudOperation.READ, "id", "bezeichnung");
        formFactory.setVisibleProperties(CrudOperation.ADD, "id", "bezeichnung");
        formFactory.setVisibleProperties(CrudOperation.UPDATE, "id", "bezeichnung");
        formFactory.setVisibleProperties(CrudOperation.DELETE, "id", "bezeichnung");

        formFactory.setDisabledProperties("id");

        crud.getGrid().setColumns("id", "bezeichnung");

        crud.getGrid().addColumn(reportCSSImage -> reportCSSImage.getReportCSS().getId(), new ButtonRenderer(event -> {
            ReportCSSImage reportCSSImage = (ReportCSSImage) event.getItem();
            UI.getCurrent().getNavigator().navigateTo("ReportCSSView/adresseId/" + reportCSSImage.getId().toString());
        })).setCaption("Report CSS").setStyleGenerator(item -> "v-align-center");

        formFactory.setButtonCaption(CrudOperation.ADD, "Neues Report CSS Image erstellen");
        formFactory.setButtonCaption(CrudOperation.DELETE, "Report CSS Image löschen");

        crud.setRowCountCaption("%d Report CSS Images gefunden");

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

        Button clearFilterTextBtn = new Button(VaadinIcons.RECYCLE);
        clearFilterTextBtn.setDescription("Entferne Filter");
        clearFilterTextBtn.addClickListener(e -> {
            filterTextBezeichnung.clear();
        });

        filterToolbar.addComponents(filterTextBezeichnung, clearFilterTextBtn);
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
                crud.getGrid().select(facade.findBy(id));
            }
        }
    }

    @Override
    public Collection<ReportCSSImage> findAll() {
        return getItems();
    }

    @Override
    public ReportCSSImage add(ReportCSSImage reportCSSImage) {
        return facade.save(reportCSSImage);
    }

    @Override
    public ReportCSSImage update(ReportCSSImage reportCSSImage) {
        return facade.save(reportCSSImage);
    }

    @Override
    public void delete(ReportCSSImage reportCSSImage) {
        facade.delete(reportCSSImage);
    }
}
