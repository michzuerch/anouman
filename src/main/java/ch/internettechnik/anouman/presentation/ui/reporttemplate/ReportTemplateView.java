package ch.internettechnik.anouman.presentation.ui.reporttemplate;

import ch.internettechnik.anouman.backend.entity.ReportTemplate;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ReportTemplateFacade;
import ch.internettechnik.anouman.presentation.ui.Menu;
import com.vaadin.cdi.CDIView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

@CDIView(value = "ReportTemplate")
public class ReportTemplateView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ReportTemplateView.class.getName());

    Grid<ReportTemplate> grid = new Grid<>();
    TextField filterTextBezeichnung = new TextField();

    @Inject
    private Menu menu;

    @Inject
    private ReportTemplateFacade facade;

    @Inject
    private ReportTemplateForm form;

    @Inject
    private ReportTemplateEditForm formEdit;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        setStyleName("anouman-background");

        filterTextBezeichnung.setPlaceholder("Filter für Bezeichnung");
        filterTextBezeichnung.addValueChangeListener(e -> updateList());
        filterTextBezeichnung.setValueChangeMode(ValueChangeMode.LAZY);

        Button clearFilterTextBtn = new Button(VaadinIcons.RECYCLE);
        clearFilterTextBtn.setDescription("Entferne Filter");
        clearFilterTextBtn.addClickListener(e -> {
            filterTextBezeichnung.clear();
        });

        Button addBtn = new Button(VaadinIcons.PLUS);
        addBtn.addClickListener(event -> {
            grid.asSingleSelect().clear();
            form.setEntity(new ReportTemplate());
            form.openInModalPopup();
            form.setSavedHandler(report -> {
                if (report.getTemplate() == null) System.err.println("Report Template ist Null");
                System.err.println("report.template: " + report.getTemplate());
                facade.save(report);
                updateList();
                grid.select(report);
                form.closePopup();
            });
        });


        HorizontalLayout tools = new HorizontalLayout();
        tools.addComponents(filterTextBezeichnung, clearFilterTextBtn, addBtn);
        //tools.setWidth(50, Unit.PERCENTAGE);

        grid.setCaption("Report Template");
        grid.setCaptionAsHtml(true);
        grid.addColumn(ReportTemplate::getId).setCaption("id");
        grid.addColumn(ReportTemplate::getBezeichnung).setCaption("Bezeichnung");
        grid.addColumn(ReportTemplate::getFilename).setCaption("Dateiname");
        grid.addColumn(ReportTemplate::getSize).setCaption("Report Grösse");

        // Render a button that deletes the data row (item)
        grid.addColumn(report -> "löschen",
                new ButtonRenderer(event -> {
                    Notification.show("Lösche id:" + event.getItem(), Notification.Type.HUMANIZED_MESSAGE);
                    facade.delete((ReportTemplate) event.getItem());
                    updateList();
                })
        );

        grid.addColumn(report -> "ändern",
                new ButtonRenderer(event -> {
                    form.setEntity((ReportTemplate) event.getItem());
                    form.setCaption("Report Template");
                    form.openInModalPopup();
                    form.setSavedHandler(report -> {
                        facade.save(report);
                        updateList();
                        grid.select(report);
                        form.closePopup();
                    });
                    form.setResetHandler(report -> {
                        updateList();
                        grid.select(report);
                        form.closePopup();
                    });
                }));

        grid.addColumn(report -> "edit",
                new ButtonRenderer(event -> {
                    formEdit.setEntity((ReportTemplate) event.getItem());
                    formEdit.setCaption("Report Template Edit");
                    formEdit.openInModalPopup();
                    formEdit.setSavedHandler(report -> {
                        facade.save(report);
                        updateList();
                        grid.select(report);
                        formEdit.closePopup();
                    });
                    formEdit.setResetHandler(report -> {
                        updateList();
                        grid.select(report);
                        formEdit.closePopup();
                    });

                }));
        grid.setSizeFull();
        updateList();
        addComponents(menu, tools);
        addComponentsAndExpand(grid);
    }

    private void updateList() {
        if (!filterTextBezeichnung.isEmpty()) {
            //Suche mit Bezeichnung
            logger.debug("Suche mit Bezeichnung:" + filterTextBezeichnung.getValue());
            grid.setItems(facade.findByBezeichnungLikeIgnoreCase(filterTextBezeichnung.getValue() + "%"));
            return;
        }
        grid.setItems(facade.findAll());
    }
}
