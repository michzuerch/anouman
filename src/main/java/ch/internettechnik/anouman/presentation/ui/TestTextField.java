package ch.internettechnik.anouman.presentation.ui;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.VerticalLayout;

public class TestTextField extends CustomField<String> {

    private com.vaadin.ui.TextField textField = new com.vaadin.ui.TextField("TextField");
    private String textValue = new String();


    @Override
    protected Component initContent() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setSpacing(false);
        layout.addComponents(textField);
        return layout;
    }

    @Override
    protected void doSetValue(String value) {
        this.textValue = value;
        textField.setValue(this.textValue);
    }

    @Override
    public String getValue() {
        return this.textValue;
    }
}
