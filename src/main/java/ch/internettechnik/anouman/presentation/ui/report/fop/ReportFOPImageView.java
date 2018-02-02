package ch.internettechnik.anouman.presentation.ui.report.fop;

import ch.internettechnik.anouman.backend.entity.report.fop.ReportFOPImage;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ReportFOPImageDeltaspikeFacade;
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

@CDIView("ReportFOPImageView")
public class ReportFOPImageView extends VerticalLayout implements View, CrudListener<ReportFOPImage> {
    private static Logger logger = LoggerFactory.getLogger(ReportFOPImageView.class.getName());

    @Inject
    ReportFOPImageDeltaspikeFacade facade;

    GridCrud<ReportFOPImage> crud;
    CssLayout filterToolbar = new CssLayout();
    TextField filterTextBezeichnung = new TextField();

    private Collection<ReportFOPImage> getItems() {
        if (!filterTextBezeichnung.isEmpty()) {
            //Suche mit Bezeichnung
            logger.debug("Suche mit Bezeichnung:" + filterTextBezeichnung.getValue());
            return facade.findByBezeichnungLikeIgnoreCase(filterTextBezeichnung.getValue() + "%");
        }
        return facade.findAll();
    }

    private Crud createCrud() {
        crud = new GridCrud<ReportFOPImage>(ReportFOPImage.class, new WindowBasedCrudLayout());
        crud.setCrudListener(this);

        VerticalCrudFormFactory<ReportFOPImage> formFactory = new VerticalCrudFormFactory<>(ReportFOPImage.class);

        crud.setCrudFormFactory(formFactory);

        formFactory.setUseBeanValidation(true);

        formFactory.setErrorListener(e -> Notification.show("Custom error message (simulated error)", Notification.Type.ERROR_MESSAGE));

        formFactory.setVisibleProperties(CrudOperation.READ, "id", "bezeichnung");
        formFactory.setVisibleProperties(CrudOperation.ADD, "id", "bezeichnung");
        formFactory.setVisibleProperties(CrudOperation.UPDATE, "id", "bezeichnung");
        formFactory.setVisibleProperties(CrudOperation.DELETE, "id", "bezeichnung");

        formFactory.setDisabledProperties("id");

        crud.getGrid().setColumns("id", "bezeichnung");

        crud.getGrid().addColumn(reportFOPImage -> reportFOPImage.getReportFOP().getId(), new ButtonRenderer(event -> {
            ReportFOPImage reportFOPImage = (ReportFOPImage) event.getItem();
            UI.getCurrent().getNavigator().navigateTo("ReportFOPImageView/adresseId/" + reportFOPImage.getId().toString());
        })).setCaption("Anzahl Images").setStyleGenerator(item -> "v-align-center");

        formFactory.setButtonCaption(CrudOperation.ADD, "Neues Report FOP Image erstellen");
        formFactory.setButtonCaption(CrudOperation.DELETE, "Report FOP Image löschen");

        crud.setRowCountCaption("%d Report FOP Images gefunden");

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
    public Collection<ReportFOPImage> findAll() {
        return getItems();
    }

    @Override
    public ReportFOPImage add(ReportFOPImage reportFOPImage) {
        return facade.save(reportFOPImage);
    }

    @Override
    public ReportFOPImage update(ReportFOPImage reportFOPImage) {
        return facade.save(reportFOPImage);
    }

    @Override
    public void delete(ReportFOPImage reportFOPImage) {
        facade.delete(reportFOPImage);
    }
}
