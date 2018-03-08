package com.gmail.michzuerch.anouman.presentation.ui.report.jasper.reporttemplate;

import com.gmail.michzuerch.anouman.backend.entity.report.jasper.ReportJasper;
import com.gmail.michzuerch.anouman.backend.entity.report.jasper.ReportJasperImage;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.ReportJasperDeltaspikeFacade;
import com.gmail.michzuerch.anouman.presentation.ui.field.ImageField;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.viritin.form.AbstractForm;

import javax.inject.Inject;

public class ReportJasperImageForm extends AbstractForm<ReportJasperImage> {
    private static Logger logger = LoggerFactory.getLogger(ReportJasperImageForm.class.getName());

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
        openInModalPopup.setWidth(600, Unit.PIXELS);
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
