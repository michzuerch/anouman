package ch.internettechnik.anouman.presentation.ui.report.jasper.reporttemplate;

import ch.internettechnik.anouman.backend.entity.report.jasper.ReportJasper;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ReportJasperFacade;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.ui.*;
import org.vaadin.viritin.form.AbstractForm;
import server.droporchoose.UploadComponent;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@ViewScoped
public class ReportJasperForm extends AbstractForm<ReportJasper> {
    @Inject
    ReportJasperFacade facade;

    TextField titel = new TextField("Titel");
    UploadComponent report = new UploadComponent();
    //Image image = new Image("Bild");

    public ReportJasperForm() {
        super(ReportJasper.class);
    }

/*
    public void lockSelect() {
        artikel.setEnabled(false);
    }
*/

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setCaption("Report Jasper");
        openInModalPopup.setWidth("500px");
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {
        report.setWidth(100, Unit.PIXELS);
        report.setHeight(60, Unit.PIXELS);
        report.setCaption("File upload");
        report.setReceivedCallback(this::uploadReceived);

        return new VerticalLayout(new FormLayout(
                titel, report
        ), getToolbar());
    }

    private void uploadReceived(String fileName, Path path) {
        try {
            byte[] data = Files.readAllBytes(Paths.get(path.toUri()));
            getEntity().setTemplate(data);
            //getEntity().setMimetype(Files.probeContentType(path));

/*
            StreamResource.StreamSource streamSource = new StreamResource.StreamSource() {
                public InputStream getStream() {
                    return (data == null) ? null : new ByteArrayInputStream(data);
                }
            };
            StreamResource imageResource = new StreamResource(streamSource, fileName);
*/

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
