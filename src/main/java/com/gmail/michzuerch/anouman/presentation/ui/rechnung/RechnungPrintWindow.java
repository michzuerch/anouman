package com.gmail.michzuerch.anouman.presentation.ui.rechnung;

import com.gmail.michzuerch.anouman.backend.jpa.domain.Rechnung;
import com.gmail.michzuerch.anouman.backend.jpa.domain.report.jasper.ReportJasper;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.ReportJasperDeltaspikeFacade;
import com.gmail.michzuerch.anouman.presentation.reports.rechnung.RechnungReportTool;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import org.apache.commons.io.IOUtils;
import org.vaadin.viritin.button.DownloadButton;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Logger;

public class RechnungPrintWindow extends CustomComponent {
    private static final Logger LOGGER = Logger.getLogger(RechnungPrintWindow.class.getName());

    @Inject
    ReportJasperDeltaspikeFacade reportJasperDeltaspikeFacade;

    private Rechnung rechnung;
    private ComboBox<ReportJasper> reportSelect = new ComboBox<>();
    private TextField id = new TextField("Id");
    private TextField bezeichnung = new TextField("Bezeichnung");
    private DateField rechnungsdatum = new DateField("Rechnungsdatum");
    private TextField firma = new TextField("Firma");
    private TextField name = new TextField("Name");
    private TextField ort = new TextField("Ort");
    private DownloadButton printButton;
    private Window popup;

    private String getFilename() {
        StringBuffer filename = new StringBuffer();
        filename.append(reportSelect.getValue().getBezeichnung());
        filename.append("_").append(getRechnung().getId()).append("_").append(getRechnung().getBezeichnung());
        filename.append(".pdf");
        filename = new StringBuffer(filename.toString().replaceAll("\\s+", ""));
        System.err.println("getPrintButton() Filename:[" + filename + "] ");
        return filename.toString();
    }

    protected DownloadButton getPrintButton() {
        return new DownloadButton(
                stream -> {
                    try {
                        ByteArrayInputStream in = new ByteArrayInputStream(
                                RechnungReportTool.getPdf(rechnung, reportSelect.getValue()));
                        IOUtils.copy(
                                new ByteArrayInputStream(RechnungReportTool.getPdf(rechnung, reportSelect.getValue())),
                                stream);
                        IOUtils.closeQuietly(in);
                        IOUtils.closeQuietly(stream);
                    } catch (IOException ex) {
                        Notification.show(ex.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);
                    }
                    closePopup();
                    popup.close();

                    System.err.println("DownloadButton stream, rechnung id:" + rechnung.getId() + " report id:" + reportSelect.getValue().getId());
                }).setFileName(getFilename())
                .withCaption("Print").withIcon(VaadinIcons.FILE_PROCESS);
    }

    private void update() {
        printButton.setFileNameProvider(() -> getFilename());
        printButton.setCaption("Print " + getFilename());
        id.setValue(rechnung.getId().toString());
        bezeichnung.setValue(rechnung.getBezeichnung());
        rechnungsdatum.setValue(rechnung.getRechnungsdatum());
        firma.setValue(rechnung.getAdresse().getFirma());
        name.setValue(rechnung.getAdresse().getNachname());

    }

    protected Component createContent() {
        reportSelect.setItems(reportJasperDeltaspikeFacade.findAll());
        reportSelect.setEmptySelectionAllowed(false);
        reportSelect.setSelectedItem(reportJasperDeltaspikeFacade.findAll().get(0));
        reportSelect.setItemCaptionGenerator(reportTemplate -> reportTemplate.getBezeichnung());
        reportSelect.setCaption("Report Template");

        reportSelect.addValueChangeListener(valueChangeEvent -> {
            update();
        });

        printButton = getPrintButton();

        id.setReadOnly(true);
        bezeichnung.setReadOnly(true);
        rechnungsdatum.setReadOnly(true);
        firma.setReadOnly(true);
        name.setReadOnly(true);

        FormLayout layout = new FormLayout();
        layout.setSpacing(true);
        layout.setMargin(true);
        layout.addComponents(reportSelect, id, bezeichnung, rechnungsdatum, firma, name, ort, printButton);
        return layout;
    }

    protected void lazyInit() {
        if (getCompositionRoot() == null) {
            setCompositionRoot(createContent());
        }
    }

    public Window getPopup() {
        return popup;
    }

    public Window openInModalPopup() {
        popup = new Window("Print Rechnung", this);
        popup.setWidth("700px");
        popup.setModal(true);
        UI.getCurrent().addWindow(popup);
        return popup;
    }

    public void closePopup() {
        if (getPopup() != null) {
            getPopup().close();
        }
    }

    public Rechnung getRechnung() {
        return rechnung;
    }

    public void setRechnung(Rechnung rechnung) {
        this.rechnung = rechnung;

        lazyInit();
    }
}
