package com.gmail.michzuerch.anouman.presentation.ui.rechnung;

import com.gmail.michzuerch.anouman.backend.entity.Rechnung;
import com.gmail.michzuerch.anouman.backend.entity.report.jasper.ReportJasper;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.AufwandDeltaspikeFacade;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.RechnungDeltaspikeFacade;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.ReportJasperDeltaspikeFacade;
import com.gmail.michzuerch.anouman.presentation.reports.rechnung.RechnungReportTool;
import com.vaadin.cdi.CDIView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.viritin.button.DownloadButton;
import org.vaadin.viritin.fields.IntegerField;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@CDIView("RechnungPrintView")
public class RechnungPrintView extends VerticalLayout implements View {
    private static Logger logger = LoggerFactory.getLogger(RechnungPrintView.class.getName());

    @Inject
    RechnungDeltaspikeFacade rechnungDeltaspikeFacade;
    @Inject
    ReportJasperDeltaspikeFacade reportJasperDeltaspikeFacade;
    @Inject
    AufwandDeltaspikeFacade aufwandDeltaspikeFacade;

    Long idRechnung = new Long(0);
    TextField fieldId = new TextField("id");
    TextField fieldAdresseFirma = new TextField("Adresse Firma");
    TextField fieldAdresseNachname = new TextField("Adresse Nachname");
    TextField fieldAdresseOrt = new TextField("Adresse Ort");
    TextField fieldBezeichnung = new TextField("Bezeichnung");
    DateField fieldRechnungsdatum = new DateField("Rechnungsdatum");
    IntegerField fieldFaelligInTagen = new IntegerField("Fällig in Tagen");
    DateField fieldFaelligkeitsdatum = new DateField("Fälligkeitsdatum");
    TextField fieldMehrwertsteuer = new TextField("Mehrwertsteuer");
    TextField fieldZwischentotal = new TextField("Zwischentotal");
    TextField fieldRechnungstotal = new TextField("Rechnungstotal");
    NativeSelect<ReportJasper> selectReport = new NativeSelect<>();
    DownloadButton btnPrint = new DownloadButton();

    protected DownloadButton getPrintButton() {
        return new DownloadButton(
                stream -> {
                    try {
                        ByteArrayInputStream in = new ByteArrayInputStream(
                                RechnungReportTool.getPdf(rechnungDeltaspikeFacade.findBy(this.idRechnung), selectReport.getValue()));
                        IOUtils.copy(
                                new ByteArrayInputStream(RechnungReportTool.getPdf(rechnungDeltaspikeFacade.findBy(this.idRechnung), selectReport.getValue())),
                                stream);
                        IOUtils.closeQuietly(in);
                        IOUtils.closeQuietly(stream);
                    } catch (IOException ex) {
                        Notification.show(ex.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);
                    }
                }).setFileName("Rechnung.pdf")
                .withCaption("Print").withIcon(VaadinIcons.FILE_PROCESS);
    }


    private void update() {
        Rechnung val = rechnungDeltaspikeFacade.findBy(this.idRechnung);
        fieldId.setValue(val.getId().toString());
        fieldAdresseFirma.setValue(val.getAdresse().getFirma());
        fieldAdresseNachname.setValue(val.getAdresse().getNachname());
        fieldAdresseOrt.setValue(val.getAdresse().getOrt());
        fieldBezeichnung.setValue(val.getBezeichnung());
        fieldRechnungsdatum.setValue(val.getRechnungsdatum());
        fieldFaelligInTagen.setValue(val.getFaelligInTagen());
        fieldFaelligkeitsdatum.setValue(val.getFaelligkeitsdatum());
        fieldMehrwertsteuer.setValue(val.getMehrwertsteuer().toString());
        fieldZwischentotal.setValue(val.getZwischentotal().toString());
        fieldRechnungstotal.setValue(val.getRechnungstotal().toString());

        selectReport.setItems(reportJasperDeltaspikeFacade.findAll());
        selectReport.setItemCaptionGenerator(reportTemplate -> reportTemplate.getBezeichnung());
    }

    private Component createContent() {
        HorizontalLayout layout = new HorizontalLayout();

        fieldId.setEnabled(false);
        fieldAdresseFirma.setEnabled(false);
        fieldAdresseNachname.setEnabled(false);
        fieldAdresseOrt.setEnabled(false);
        fieldBezeichnung.setEnabled(false);
        fieldRechnungsdatum.setEnabled(false);
        fieldFaelligInTagen.setEnabled(false);
        fieldFaelligkeitsdatum.setEnabled(false);
        fieldMehrwertsteuer.setEnabled(false);
        fieldZwischentotal.setEnabled(false);
        fieldRechnungstotal.setEnabled(false);

        update();
        selectReport.setEmptySelectionAllowed(false);
        selectReport.setSelectedItem(reportJasperDeltaspikeFacade.findAll().get(0));

        btnPrint = getPrintButton();

        layout.addComponents(new HorizontalLayout(
                new FormLayout(fieldId, fieldBezeichnung, fieldRechnungsdatum),
                new FormLayout(fieldAdresseFirma, fieldAdresseNachname, fieldAdresseOrt),
                new FormLayout(fieldFaelligkeitsdatum, fieldFaelligInTagen),
                new FormLayout(fieldZwischentotal, fieldMehrwertsteuer, fieldRechnungstotal)
        ), selectReport, btnPrint);
        layout.setSizeFull();
        return layout;
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        addComponent(createContent());
        setSizeFull();

        if (viewChangeEvent.getParameters() != null) {
            String[] msgs = viewChangeEvent.getParameters().split("/");
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
                this.idRechnung = id;
            }
        }
    }
}
