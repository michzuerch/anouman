package ch.internettechnik.anouman.presentation.ui.report.jasper.reporttemplate;

import ch.internettechnik.anouman.backend.entity.report.jasper.ReportJasper;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ReportJasperDeltaspikeFacade;
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

@CDIView("ReportJasperView")
public class ReportJasperView extends VerticalLayout implements View, CrudListener<ReportJasper> {
    private static Logger logger = LoggerFactory.getLogger(ReportJasperView.class.getName());

    @Inject
    ReportJasperDeltaspikeFacade facade;

    GridCrud<ReportJasper> crud;
    CssLayout filterToolbar = new CssLayout();
    TextField filterTextBezeichnung = new TextField();

    private Collection<ReportJasper> getItems() {
        if (!filterTextBezeichnung.isEmpty()) {
            //Suche mit Bezeichnung
            logger.debug("Suche mit Bezeichnung:" + filterTextBezeichnung.getValue());
            return facade.findByBezeichnungLikeIgnoreCase(filterTextBezeichnung.getValue() + "%");
        }
        return facade.findAll();
    }

    private Crud createCrud() {
        crud = new GridCrud<ReportJasper>(ReportJasper.class, new WindowBasedCrudLayout());
        crud.setCrudListener(this);

        VerticalCrudFormFactory<ReportJasper> formFactory = new VerticalCrudFormFactory<>(ReportJasper.class);

        crud.setCrudFormFactory(formFactory);

        formFactory.setUseBeanValidation(true);

        formFactory.setErrorListener(e -> Notification.show("Custom error message (simulated error)", Notification.Type.ERROR_MESSAGE));

        formFactory.setVisibleProperties(CrudOperation.READ, "id", "bezeichnung", "filename");
        formFactory.setVisibleProperties(CrudOperation.ADD, "id", "bezeichnung", "filename");
        formFactory.setVisibleProperties(CrudOperation.UPDATE, "id", "bezeichnung", "filename");
        formFactory.setVisibleProperties(CrudOperation.DELETE, "id", "bezeichnung");

        formFactory.setDisabledProperties("id");

        crud.getGrid().setColumns("id", "bezeichnung", "filename");

        crud.getGrid().addColumn(reportJasper -> reportJasper.getAnzahlReportJasperImages(), new ButtonRenderer(event -> {
            ReportJasper reportJasper = (ReportJasper) event.getItem();
            if (reportJasper.getAnzahlReportJasperImages() > 0) {
                UI.getCurrent().getNavigator().navigateTo("ReportJasperImageOldView/adresseId/" + reportJasper.getId().toString());
            }
        })).setCaption("Anzahl Images").setStyleGenerator(item -> "v-align-center");

        formFactory.setButtonCaption(CrudOperation.ADD, "Neuen Report Jasper erstellen");
        formFactory.setButtonCaption(CrudOperation.DELETE, "Report Jasper löschen");

        crud.setRowCountCaption("%d Report Jasper gefunden");

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
                crud.getGrid().select(facade.findBy(id));
            }
        }
    }

    @Override
    public Collection<ReportJasper> findAll() {
        return getItems();
    }

    @Override
    public ReportJasper add(ReportJasper reportJasper) {
        return facade.save(reportJasper);
    }

    @Override
    public ReportJasper update(ReportJasper reportJasper) {
        return facade.save(reportJasper);
    }

    @Override
    public void delete(ReportJasper reportJasper) {
        facade.delete(reportJasper);
    }
}
