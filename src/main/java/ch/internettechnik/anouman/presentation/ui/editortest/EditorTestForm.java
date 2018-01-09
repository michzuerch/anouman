package ch.internettechnik.anouman.presentation.ui.editortest;

import ch.internettechnik.anouman.backend.entity.EditorTest;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.viritin.form.AbstractForm;

/**
 * Created by michzuerch on 09.08.15.
 */
@UIScope
public class EditorTestForm extends AbstractForm<EditorTest> {
    private static Logger logger = LoggerFactory.getLogger(EditorTestForm.class.getName());

    //@Inject
    //TemplateKontoplanSelect kontoplan;

    TextField erster = new TextField("Erster");
    TextField zweiter = new TextField("Zweiter");

    public EditorTestForm() {
        super(EditorTest.class);
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setWidth("400px");
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {
        return new VerticalLayout(new FormLayout(erster, zweiter), getToolbar());
    }


}
