package ch.internettechnik.anouman.presentation.ui.rechnung;

import ch.internettechnik.anouman.backend.entity.Rechnung;
import ch.internettechnik.anouman.backend.entity.ReportTemplate;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.AufwandFacade;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.RechnungFacade;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ReportTemplateFacade;
import ch.internettechnik.anouman.presentation.reports.rechnung.RechnungReportTool;
import ch.internettechnik.anouman.presentation.ui.Menu;
import com.vaadin.cdi.CDIView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.apache.commons.io.IOUtils;
import org.vaadin.viritin.button.DownloadButton;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@CDIView("RechnungPrint")
public class RechnungPrintView extends VerticalLayout implements View {
    @Inject
    RechnungFacade rechnungFacade;
    @Inject
    ReportTemplateFacade reportTemplateFacade;
    @Inject
    AufwandFacade aufwandFacade;

    Long idRechnung = new Long(0);
    TextField fieldId = new TextField("id");
    TextField fieldAdresseFirma = new TextField("Adresse Firma");
    TextField fieldAdresseNachname = new TextField("Adresse Nachname");
    TextField fieldAdresseOrt = new TextField("Adresse Ort");
    TextField fieldBezeichnung = new TextField("Bezeichnung");
    TextField fieldRechnungsdatum = new TextField("Rechnungsdatum");
    TextField fieldFaelligInTagen = new TextField("Fällig in Tagen");
    TextField fieldFaelligkeitsdatum = new TextField("Fälligkeitsdatum");
    TextField fieldMehrwertsteuer = new TextField("Mehrwertsteuer");
    TextField fieldZwischentotal = new TextField("Zwischentotal");
    TextField fieldRechnungstotal = new TextField("Rechnungstotal");
    NativeSelect<ReportTemplate> selectReport = new NativeSelect<>();
    DownloadButton btnPrint = new DownloadButton();
    @Inject
    private Menu menu;

    protected DownloadButton getPrintButton() {
        return new DownloadButton(
                stream -> {
                    try {
                        ByteArrayInputStream in = new ByteArrayInputStream(
                                RechnungReportTool.getPdf(rechnungFacade.findBy(this.idRechnung), selectReport.getValue()));
                        IOUtils.copy(
                                new ByteArrayInputStream(RechnungReportTool.getPdf(rechnungFacade.findBy(this.idRechnung), selectReport.getValue())),
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
        Rechnung val = rechnungFacade.findBy(this.idRechnung);
        fieldId.setValue(val.getId().toString());
        fieldAdresseFirma.setValue(val.getAdresse().getFirma());
        fieldAdresseNachname.setValue(val.getAdresse().getNachname());
        fieldAdresseOrt.setValue(val.getAdresse().getOrt());
        fieldBezeichnung.setValue(val.getBezeichnung());
        fieldRechnungsdatum.setValue(val.getRechnungsdatum().toLocaleString());
        fieldFaelligInTagen.setValue(new Integer(val.getFaelligInTagen()).toString());
        fieldFaelligkeitsdatum.setValue(val.getFaelligkeitsdatum().toLocaleString());
        fieldMehrwertsteuer.setValue(val.getMehrwertsteuer().toString());
        fieldZwischentotal.setValue(val.getZwischentotal().toString());
        fieldRechnungstotal.setValue(val.getRechnungstotal().toString());

        selectReport.setItems(reportTemplateFacade.findAll());
        selectReport.setItemCaptionGenerator(reportTemplate -> reportTemplate.getBezeichnung());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

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
        selectReport.setSelectedItem(reportTemplateFacade.findAll().get(0));

        btnPrint = getPrintButton();

        addComponent(menu);
        addComponents(new HorizontalLayout(
                new FormLayout(fieldId, fieldBezeichnung, fieldRechnungsdatum),
                new FormLayout(fieldAdresseFirma, fieldAdresseNachname, fieldAdresseOrt),
                new FormLayout(fieldFaelligkeitsdatum, fieldFaelligInTagen),
                new FormLayout(fieldZwischentotal, fieldMehrwertsteuer, fieldRechnungstotal)
        ), selectReport, btnPrint);

    }
}
