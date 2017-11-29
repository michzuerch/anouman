package ch.internettechnik.anouman.presentation.ui.report.fop;

import ch.internettechnik.anouman.backend.entity.report.fop.ReportFOPXsl;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ReportFOPXslFacade;
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

@CDIView(value = "ReportFOP")
public class ReportFOPView extends VerticalLayout implements View, Upload.Receiver, Upload.SucceededListener {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ReportFOPView.class.getName());

    File tempFile;
    String filename;

    Grid<ReportFOPXsl> grid = new Grid<>();
    TextField filterTextBezeichnung = new TextField();

    TextField newReportBezeichnung = new TextField();
    Upload upload = new Upload();

    @Inject
    private Menu menu;

    @Inject
    private ReportFOPXslFacade facade;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        setStyleName("anouman-background");

        upload.setEnabled(false);
        upload.setReceiver(this);
        upload.addSucceededListener(this::uploadSucceeded);

        newReportBezeichnung.setPlaceholder("Bezeichnung für neuen FOP Report");
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
        grid.addColumn(ReportFOPXsl::getId).setCaption("id");
        grid.addColumn(ReportFOPXsl::getBezeichnung).setCaption("Bezeichnung");
        grid.addColumn(ReportFOPXsl::getSize).setCaption("Report Grösse");

        // Render a button that deletes the data row (item)
        grid.addColumn(report -> "löschen",
                new ButtonRenderer(event -> {
                    Notification.show("Lösche id:" + event.getItem(), Notification.Type.HUMANIZED_MESSAGE);
                    facade.delete((ReportFOPXsl) event.getItem());
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
            ReportFOPXsl reportFOP = new ReportFOPXsl();
            byte[] bytes = FileUtils.readFileToByteArray(tempFile);
            reportFOP.setBezeichnung(newReportBezeichnung.getValue());
            reportFOP.setXslfile(bytes);
            facade.save(reportFOP);

            updateList();
            Notification.show("Report FOP erstellt: " + reportFOP.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        tempFile.deleteOnExit();
    }

}
