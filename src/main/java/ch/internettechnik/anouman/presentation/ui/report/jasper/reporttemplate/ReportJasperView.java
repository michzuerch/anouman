package ch.internettechnik.anouman.presentation.ui.report.jasper.reporttemplate;

import ch.internettechnik.anouman.backend.entity.report.jasper.ReportJasper;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ReportJasperFacade;
import ch.internettechnik.anouman.presentation.ui.Menu;
import com.vaadin.cdi.CDIView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@CDIView(value = "ReportJasper")
public class ReportJasperView extends VerticalLayout implements View, Upload.Receiver, Upload.SucceededListener {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ReportJasperView.class.getName());

    File tempFile;
    String filename;

    Grid<ReportJasper> grid = new Grid<>();
    TextField filterTextBezeichnung = new TextField();

    TextField newReportBezeichnung = new TextField();

    Upload upload = new Upload();

    @Inject
    private Menu menu;

    @Inject
    private ReportJasperFacade facade;

    @Inject
    private ReportJasperForm form;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        setStyleName("anouman-background");

        upload.setEnabled(false);
        upload.setReceiver(this);
        upload.addSucceededListener(this::uploadSucceeded);

        newReportBezeichnung.setPlaceholder("Bezeichnung für neuen Report");
        newReportBezeichnung.setWidth(350, Unit.PIXELS);
        newReportBezeichnung.addValueChangeListener(event -> {
            if (event.getValue().isEmpty()) {
                upload.setEnabled(false);
            } else {
                upload.setEnabled(true);
            }
        });

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
            ReportJasper reportJasper = new ReportJasper();
            form.setEntity(reportJasper);
            form.openInModalPopup();
            form.setSavedHandler(val -> {
                facade.save(val);
                updateList();
                grid.select(val);
                form.closePopup();
            });
        });


        CssLayout tools = new CssLayout();
        tools.addComponents(filterTextBezeichnung, clearFilterTextBtn, addBtn, newReportBezeichnung, upload);
        tools.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        grid.setCaption("Report Jasper");
        grid.setCaptionAsHtml(true);
        grid.addColumn(ReportJasper::getId).setCaption("id");
        grid.addColumn(ReportJasper::getBezeichnung).setCaption("Bezeichnung");
        grid.addColumn(ReportJasper::getFilename).setCaption("Dateiname");
        grid.addColumn(ReportJasper::getSizeSource).setCaption("Source Size");
        grid.addColumn(ReportJasper::getSizeCompiled).setCaption("Compiled Size");

        // Render a button that deletes the data row (item)
        grid.addColumn(report -> "löschen",
                new ButtonRenderer(event -> {
                    Notification.show("Lösche id:" + event.getItem(), Notification.Type.HUMANIZED_MESSAGE);
                    facade.delete((ReportJasper) event.getItem());
                    updateList();
                })
        );
        grid.addColumn(report -> "ändern",
                new ButtonRenderer(event -> {
                    form.setEntity((ReportJasper) event.getItem());
                    form.openInModalPopup();
                    form.setSavedHandler(val -> {
                        facade.save(val);
                        //service.saveOrPersist(val);
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
            grid.setItems(facade.findByBezeichnungLikeIgnoreCase(filterTextBezeichnung.getValue() + "%"));
            return;
        }
        grid.setItems(facade.findAll());
    }

    @Override
    public OutputStream receiveUpload(String filename, String s1) {
        OutputStream outputStream = null;
        this.filename = filename;
        try {
            tempFile = File.createTempFile(this.filename, ".tmp");
            outputStream = new FileOutputStream(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream;
    }

    @Override
    public void uploadSucceeded(Upload.SucceededEvent succeededEvent) {
        try {
            ReportJasper reportJasper = new ReportJasper();
            byte[] bytes = FileUtils.readFileToByteArray(tempFile);

            reportJasper.setBezeichnung(newReportBezeichnung.getValue());
            reportJasper.setTemplateSource(bytes);
            reportJasper.setFilename(this.filename);

            facade.save(reportJasper);

            updateList();
            Notification.show("Report Jasper erstellt: " + reportJasper.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        tempFile.deleteOnExit();
    }

}
