package ch.internettechnik.anouman.presentation.ui.report.fop;

import ch.internettechnik.anouman.backend.entity.report.fop.ReportFOP;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ReportFOPFacade;
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

@CDIView(value = "ReportFOP")
public class ReportFOPView extends VerticalLayout implements View, Upload.Receiver, Upload.SucceededListener {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ReportFOPView.class.getName());

    File tempFile;
    String filename;

    Grid<ReportFOP> grid = new Grid<>();
    TextField filterTextBezeichnung = new TextField();

    TextField newReportBezeichnung = new TextField();
    Upload upload = new Upload();

    @Inject
    private Menu menu;

    @Inject
    private ReportFOPFacade facade;

    @Inject
    private ReportFOPForm form;

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

        Button addBtn = new Button(VaadinIcons.PLUS);
        addBtn.addClickListener(event -> {
            grid.asSingleSelect().clear();
            ReportFOP reportFOP = new ReportFOP();
            form.setEntity(reportFOP);
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

        grid.setCaption("Report FOP");
        grid.setCaptionAsHtml(true);
        grid.addColumn(ReportFOP::getId).setCaption("id");
        grid.addColumn(ReportFOP::getBezeichnung).setCaption("Bezeichnung");
        grid.addColumn(ReportFOP::getSize).setCaption("Report Grösse");

        // Render a button that deletes the data row (item)
        grid.addColumn(report -> "löschen",
                new ButtonRenderer(event -> {
                    Notification.show("Lösche id:" + event.getItem(), Notification.Type.HUMANIZED_MESSAGE);
                    facade.delete((ReportFOP) event.getItem());
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
            ReportFOP reportFOP = new ReportFOP();
            byte[] bytes = FileUtils.readFileToByteArray(tempFile);
            reportFOP.setBezeichnung(newReportBezeichnung.getValue());
            reportFOP.setTemplate(bytes);
            facade.save(reportFOP);

            updateList();
            Notification.show("Report FOP erstellt: " + reportFOP.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        tempFile.deleteOnExit();
    }

}
