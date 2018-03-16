package com.gmail.michzuerch.anouman.presentation.ui.util.field;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.VerticalLayout;

import java.util.logging.Logger;

public class TestTextField extends CustomField<String> {
    private static final Logger LOGGER = Logger.getLogger(TestTextField.class.getName());

    private com.vaadin.ui.TextField textField = new com.vaadin.ui.TextField();
    private String textValue = new String();

    public TestTextField(String caption) {
        super();
        setCaption(caption);
    }

    @Override
    protected Component initContent() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setSpacing(false);
        layout.addComponents(textField);
        textField.addValueChangeListener(
                event -> {
                    fireEvent(new ValueChangeEvent<>(this,
                            event.getOldValue(), event.isUserOriginated()));
                    System.err.println("TestTextField VcE:" + event.getValue());
                    this.textValue = event.getValue();
                }
        );
        return layout;
    }

    @Override
    public String getEmptyValue() {
        System.err.println("TestTextField getEmptyValue()");
        return new String();
    }

    @Override
    protected void doSetValue(String value) {
        System.err.println("TestTextField doSetValue:" + value);
        this.textValue = value;
        textField.setValue(value);
    }

    @Override
    public String getValue() {
        this.textValue = textField.getValue();
        System.err.println("TestTextField getValue:" + this.textValue);
        return this.textValue;
    }
}
