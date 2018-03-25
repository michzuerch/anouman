package com.gmail.michzuerch.anouman.presentation.ui.report.css;

import com.gmail.michzuerch.anouman.backend.entity.report.css.ReportCSS;
import com.gmail.michzuerch.anouman.presentation.ui.util.field.CSSField;
import com.gmail.michzuerch.anouman.presentation.ui.util.field.HtmlField;
import com.gmail.michzuerch.anouman.presentation.ui.util.field.JavascriptField;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.viritin.form.AbstractForm;

public class ReportCSSForm extends AbstractForm<ReportCSS> {
    private static Logger logger = LoggerFactory.getLogger(ReportCSSForm.class.getName());

    TextField bezeichnung = new TextField("Bezeichnung");

    CSSField css = new CSSField();
    HtmlField html = new HtmlField();
    JavascriptField javascript = new JavascriptField();

    TabSheet tabSheet = new TabSheet();

    VerticalLayout tabCSS = new VerticalLayout();
    VerticalLayout tabHTML = new VerticalLayout();
    VerticalLayout tabJavascript = new VerticalLayout();

    public ReportCSSForm() {
        super(ReportCSS.class);
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setCaption("Report CSS");
        openInModalPopup.setWidth(900, Unit.PIXELS);
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {
        tabCSS.addComponent(css);
        tabHTML.addComponent(html);
        tabJavascript.addComponent(javascript);

        tabSheet.addTab(tabCSS, "CSS");
        tabSheet.addTab(tabHTML, "HTML");
        tabSheet.addTab(tabJavascript, "Javascript");

        return new VerticalLayout(new FormLayout(
                bezeichnung, tabSheet), getToolbar());
    }


}
