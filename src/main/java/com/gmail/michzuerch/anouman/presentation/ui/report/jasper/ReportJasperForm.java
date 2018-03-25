package com.gmail.michzuerch.anouman.presentation.ui.report.jasper;

import com.gmail.michzuerch.anouman.backend.entity.report.jasper.ReportJasper;
import com.gmail.michzuerch.anouman.presentation.ui.util.field.JasperXmlField;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.viritin.form.AbstractForm;

public class ReportJasperForm extends AbstractForm<ReportJasper> {
    private static Logger logger = LoggerFactory.getLogger(com.gmail.michzuerch.anouman.presentation.ui.report.jasper.ReportJasperForm.class.getName());

    TextField bezeichnung = new TextField("Bezeichnung");
    JasperXmlField templateSource = new JasperXmlField();

    public ReportJasperForm() {
        super(ReportJasper.class);
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setCaption("Report Jasper");
        openInModalPopup.setWidth(800, Unit.PIXELS);
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {
        return new VerticalLayout(new FormLayout(
                bezeichnung, templateSource), getToolbar());
    }



}
