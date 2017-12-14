package ch.internettechnik.anouman.presentation.ui.report.jasper.reporttemplate;

import ch.internettechnik.anouman.backend.entity.report.jasper.ReportJasperImage;
import ch.internettechnik.anouman.presentation.ui.ImageField;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.ui.*;
import org.vaadin.viritin.form.AbstractForm;

@ViewScoped
public class ReportJasperImageForm extends AbstractForm<ReportJasperImage> {
    TextField bezeichnung = new TextField("Bezeichnung");
    ImageField image = new ImageField();

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
        //getBinder().forField(templateSource).withConverter(new ByteToStringConverter()).withValidator(new ReportJasperValidator()).bind("templateSource");
        //getBinder().forField(templateCompiled).withConverter(new ByteToStringConverter()).bind("templateCompiled");

        //StreamResource templateResource = createStreamResource();
        //FileDownloader fileDownloader = new FileDownloader(templateResource);
        //fileDownloader.extend(downloadButton);
        image.setCaption("Bild");

        return new VerticalLayout(new FormLayout(
                bezeichnung, image), getToolbar());
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }


}
