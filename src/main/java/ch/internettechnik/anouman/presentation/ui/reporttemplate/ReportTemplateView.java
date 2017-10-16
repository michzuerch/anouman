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
import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@CDIView(value = "ReportTemplate")
public class ReportTemplateView extends VerticalLayout implements View, Upload.Receiver, Upload.SucceededListener {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ReportTemplateView.class.getName());

    File tempFile;
    String filename;

    Grid<ReportTemplate> grid = new Grid<>();
    TextField filterTextBezeichnung = new TextField();

    TextField newReportBezeichnung = new TextField();
    Upload upload = new Upload();

    @Inject
    private Menu menu;

    @Inject
    private ReportTemplateFacade facade;

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



        HorizontalLayout tools = new HorizontalLayout();
        tools.addComponents(filterTextBezeichnung, clearFilterTextBtn, newReportBezeichnung, upload);
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
            ReportTemplate reportTemplate = new ReportTemplate();
            byte[] bytes = FileUtils.readFileToByteArray(tempFile);
            reportTemplate.setBezeichnung(newReportBezeichnung.getValue());
            reportTemplate.setTemplate(bytes);
            reportTemplate.setFilename(this.filename);

            facade.save(reportTemplate);

            updateList();
            Notification.show("Report Template erstellt: " + reportTemplate.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        tempFile.deleteOnExit();
    }

}
