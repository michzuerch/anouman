package ch.internettechnik.anouman.presentation.ui.report.jasper.reporttemplate;

import ch.internettechnik.anouman.backend.entity.report.jasper.ReportJasper;
import ch.internettechnik.anouman.backend.entity.report.jasper.ReportJasperImage;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ReportJasperDeltaspikeFacade;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ReportJasperImageDeltaspikeFacade;
import ch.internettechnik.anouman.presentation.ui.Menu;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

@UIScope
@SpringView(name = "ReportJasperImageView")
public class ReportJasperImageView extends VerticalLayout implements View {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ReportJasperImageView.class.getName());

    Grid<ReportJasperImage> grid = new Grid<>();
    TextField filterTextBezeichnung = new TextField();

    @Inject
    private Menu menu;

    @Inject
    private ReportJasperDeltaspikeFacade reportJasperDeltaspikeFacade;

    @Inject
    private ReportJasperImageDeltaspikeFacade reportJasperImageDeltaspikeFacade;

    @Inject
    private ReportJasperImageForm form;

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
            ReportJasperImage reportJasperImage = new ReportJasperImage();
            form.setEntity(reportJasperImage);
            form.openInModalPopup();
            form.setSavedHandler(val -> {
                System.err.println("Save:" + val);
                //reportJasperImageDeltaspikeFacade.save(val);
                updateList();
                grid.select(val);
                form.closePopup();
            });
        });


        CssLayout tools = new CssLayout();
        tools.addComponents(filterTextBezeichnung, clearFilterTextBtn, addBtn);
        tools.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        grid.setCaption("Report Jasper Bild");
        grid.setCaptionAsHtml(true);
        grid.addColumn(ReportJasperImage::getId).setCaption("id");
        grid.addColumn(ReportJasperImage::getBezeichnung).setCaption("Bezeichnung");
        grid.addColumn(ReportJasperImage::getSize).setCaption("Grösse");

        // Render a button that deletes the data row (item)
        grid.addColumn(report -> "löschen",
                new ButtonRenderer(event -> {
                    ReportJasperImage reportJasperImage = (ReportJasperImage) event.getItem();
                    ReportJasper reportJasper = reportJasperDeltaspikeFacade.findBy(reportJasperImage.getReportJasper().getId());
                    reportJasper.getReportJasperImages().remove(reportJasperImage);
                    reportJasperDeltaspikeFacade.save(reportJasper);
                    reportJasperImageDeltaspikeFacade.delete((ReportJasperImage) event.getItem());
                    updateList();
                    Notification.show("Lösche id:" + reportJasperImage.getId(), Notification.Type.HUMANIZED_MESSAGE);
                })
        );
        grid.addColumn(report -> "ändern",
                new ButtonRenderer(event -> {
                    form.setEntity((ReportJasperImage) event.getItem());
                    form.openInModalPopup();
                    form.setSavedHandler(val -> {
                        //val.setTemplateCompiled(form.getCompiledReport());
                        reportJasperImageDeltaspikeFacade.save(val);
                        updateList();
                        grid.select(val);
                        form.closePopup();
                    });
                    form.setResetHandler(val -> {
                        updateList();
                        grid.select(val);
                        form.closePopup();
                    });
                }));

        //@todo Downloadbutton für Report
        grid.setSizeFull();
        updateList();
        addComponents(menu, tools);
        addComponentsAndExpand(grid);
    }

    private void updateList() {
        if (!filterTextBezeichnung.isEmpty()) {
            //Suche mit Bezeichnung
            logger.debug("Suche mit Bezeichnung:" + filterTextBezeichnung.getValue());
            grid.setItems(reportJasperImageDeltaspikeFacade.findByBezeichnungLikeIgnoreCase(filterTextBezeichnung.getValue() + "%"));
            return;
        }
        grid.setItems(reportJasperImageDeltaspikeFacade.findAll());
    }
}
