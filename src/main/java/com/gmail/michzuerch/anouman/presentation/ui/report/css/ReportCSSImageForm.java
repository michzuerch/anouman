package com.gmail.michzuerch.anouman.presentation.ui.report.css;

import com.gmail.michzuerch.anouman.backend.jpa.domain.report.css.ReportCSS;
import com.gmail.michzuerch.anouman.backend.jpa.domain.report.css.ReportCSSImage;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade.ReportCSSDeltaspikeFacade;
import com.gmail.michzuerch.anouman.presentation.ui.util.field.ImageField;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.viritin.form.AbstractForm;

import javax.inject.Inject;

public class ReportCSSImageForm extends AbstractForm<ReportCSSImage> {
    private static Logger logger = LoggerFactory.getLogger(ReportCSSImageForm.class.getName());

    @Inject
    ReportCSSDeltaspikeFacade reportCSSDeltaspikeFacade;

    NativeSelect<ReportCSS> reportCSS = new NativeSelect<>("Report CSS");
    TextField bezeichnung = new TextField("Bezeichnung");
    ImageField image = new ImageField();

    private String filename;

    public ReportCSSImageForm() {
        super(ReportCSSImage.class);
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setCaption("Report CSS Image");
        openInModalPopup.setWidth(600, Unit.PIXELS);
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {
        reportCSS.setItems(reportCSSDeltaspikeFacade.findAll());
        reportCSS.setItemCaptionGenerator(item -> item.getBezeichnung() + " " + item.getId());

        image.setCaption("Bild");
        image.setHeight(300, Unit.PIXELS);
        image.setWidth(400, Unit.PIXELS);

        return new VerticalLayout(new FormLayout(
                reportCSS, bezeichnung, image), getToolbar());
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }


}
