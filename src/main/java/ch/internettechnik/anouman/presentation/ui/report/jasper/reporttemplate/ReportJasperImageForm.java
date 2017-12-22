package ch.internettechnik.anouman.presentation.ui.report.jasper.reporttemplate;

import ch.internettechnik.anouman.backend.entity.report.jasper.ReportJasper;
import ch.internettechnik.anouman.backend.entity.report.jasper.ReportJasperImage;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ReportJasperFacade;
import ch.internettechnik.anouman.presentation.ui.ImageField;
import ch.internettechnik.anouman.presentation.ui.ImageStreamSource;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.*;
import org.vaadin.viritin.form.AbstractForm;

import javax.inject.Inject;

@ViewScoped
public class ReportJasperImageForm extends AbstractForm<ReportJasperImage> {
    @Inject
    ReportJasperFacade reportJasperFacade;

    NativeSelect<ReportJasper> reportJasper = new NativeSelect<>("Report Jasper");
    TextField bezeichnung = new TextField("Bezeichnung");
    ImageField image = new ImageField();
    Button downloadButton = new Button("Download Image");

    private String filename;

    public ReportJasperImageForm() {
        super(ReportJasperImage.class);
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setCaption("Report Jasper Image");
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {
        //getBinder().forField(image).withValidator(new RrValidator()).bind("image");
        //getBinder().forField(templateCompiled).withConverter(new ByteToStringConverter()).bind("templateCompiled");

        StreamResource templateResource = new StreamResource(new ImageStreamSource(image.getValue()), "image.jpg");
        FileDownloader fileDownloader = new FileDownloader(templateResource);
        fileDownloader.extend(downloadButton);

        reportJasper.setItems(reportJasperFacade.findAll());
        reportJasper.setItemCaptionGenerator(item -> item.getBezeichnung() + " " + item.getId());

        image.setCaption("Bild");
        image.setHeight(100, Unit.PIXELS);
        image.setWidth(300, Unit.PIXELS);

        return new VerticalLayout(new FormLayout(
                reportJasper, bezeichnung, image, downloadButton), getToolbar());
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }


}
