package com.gmail.michzuerch.anouman.presentation.ui.testviews.editortest;

import com.gmail.michzuerch.anouman.backend.jpa.domain.EditorTest;
import com.gmail.michzuerch.anouman.presentation.ui.util.field.TestTextField;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.viritin.form.AbstractForm;

/**
 * Created by michzuerch on 09.08.15.
 */
public class EditorTestForm extends AbstractForm<EditorTest> {
    private static Logger logger = LoggerFactory.getLogger(EditorTestForm.class.getName());

    TestTextField erster = new TestTextField("Erster");
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
