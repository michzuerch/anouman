package ch.internettechnik.anouman.presentation.ui.report.jasper.reporttemplate;

import ch.internettechnik.anouman.backend.entity.report.jasper.ReportJasper;
import ch.internettechnik.anouman.backend.entity.report.jasper.ReportJasperImage;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade.ReportJasperDeltaspikeFacade;
import ch.internettechnik.anouman.presentation.ui.ImageField;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.vaadin.viritin.form.AbstractForm;

import javax.inject.Inject;

@UIScope
public class ReportJasperImageForm extends AbstractForm<ReportJasperImage> {
    @Inject
    ReportJasperDeltaspikeFacade reportJasperDeltaspikeFacade;

    NativeSelect<ReportJasper> reportJasper = new NativeSelect<>("Report Jasper");
    TextField bezeichnung = new TextField("Bezeichnung");
    ImageField image = new ImageField();
    //Button downloadButton = new Button("Download Image");

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
        //getBinder().forField(image).bind("image");
        //StreamResource templateResource = new StreamResource(new ImageStreamSource(image.getValue()), "image.jpg");
        //FileDownloader fileDownloader = new FileDownloader(templateResource);
        //fileDownloader.extend(downloadButton);

        reportJasper.setItems(reportJasperDeltaspikeFacade.findAll());
        reportJasper.setItemCaptionGenerator(item -> item.getBezeichnung() + " " + item.getId());

        image.setCaption("Bild");
        image.setHeight(300, Unit.PIXELS);
        image.setWidth(400, Unit.PIXELS);

        return new VerticalLayout(new FormLayout(
                reportJasper, bezeichnung, image), getToolbar());
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }


}
