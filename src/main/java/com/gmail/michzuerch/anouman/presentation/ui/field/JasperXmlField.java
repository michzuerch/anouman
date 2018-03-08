package com.gmail.michzuerch.anouman.presentation.ui.field;

import com.gmail.michzuerch.anouman.presentation.ui.validator.JRXMLValidator;
import com.vaadin.data.Validator;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import server.droporchoose.UploadComponent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JasperXmlField extends CustomField<byte[]> {
    private byte[] fielddata;
    private UploadComponent upload = new UploadComponent();
    private TextArea textArea = new TextArea("JXRML");

    public JasperXmlField(String caption) {
        setCaption(caption);
    }

    @Override
    public Validator<byte[]> getDefaultValidator() {
        return new JRXMLValidator();
    }

    @Override
    protected Component initContent() {
        upload.setReceivedCallback(this::upload);
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(false);
        layout.setMargin(false);

        textArea.setWidth(600, Unit.PIXELS);
        textArea.setRows(12);
        textArea.setWordWrap(false);

        layout.addComponent(upload);
        layout.addComponent(textArea);
        textArea.addValueChangeListener(
                event -> fireEvent(new ValueChangeEvent<byte[]>(this,
                        event.getOldValue().getBytes(), event.isUserOriginated())));
        return layout;
    }

    @Override
    public byte[] getEmptyValue() {
        return new byte[0];
    }

    private void upload(String s, Path path) {
        try {
            doSetValue(Files.readAllBytes(Paths.get(path.toUri())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doSetValue(byte[] s) {
        this.fielddata = s;
        textArea.setValue(new String(s));
    }

    @Override
    public byte[] getValue() {
        return fielddata;
    }
}
