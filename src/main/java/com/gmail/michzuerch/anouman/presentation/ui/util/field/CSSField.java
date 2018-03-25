package com.gmail.michzuerch.anouman.presentation.ui.util.field;

import com.gmail.michzuerch.anouman.presentation.ui.util.validator.JRXMLValidator;
import com.vaadin.data.Validator;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.*;
import server.droporchoose.UploadComponent;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class CSSField extends CustomField<byte[]> {
    private byte[] fieldValue;
    private String filename = new String();
    private StreamResource streamResource = new StreamResource(new CssSource(), "report.css");
    private UploadComponent upload = new UploadComponent();
    private TextArea textArea = new TextArea("CSS");
    private Button btnDownloadCssSource = new Button("Download");

    @Override
    protected Component initContent() {
        upload.setReceivedCallback(this::upload);
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(false);
        layout.setMargin(false);

        textArea.setWidth(600, Unit.PIXELS);
        textArea.setRows(12);
        textArea.setWordWrap(false);

        FileDownloader fileDownloader = new FileDownloader(streamResource);
        fileDownloader.extend(btnDownloadCssSource);

        layout.addComponent(new HorizontalLayout(upload, btnDownloadCssSource));
        layout.addComponent(textArea);
        textArea.addValueChangeListener(new ValueChangeListener<String>() {
            @Override
            public void valueChange(ValueChangeEvent<String> event) {
                doSetValue(textArea.getValue().getBytes());
            }
        });
        return layout;
    }

    @Override
    public byte[] getEmptyValue() {
        btnDownloadCssSource.setEnabled(false);
        return new byte[0];
    }

    @Override
    public Validator<byte[]> getDefaultValidator() {
        return new JRXMLValidator();
    }

    private void upload(String filename, Path path) {
        try {
            byte[] uploaded = Files.readAllBytes(path);
            setFilename(filename);
            streamResource.setFilename(getFilename());
            System.err.println("Uploaded Bytes: " + uploaded.length);
            doSetValue(uploaded);
            btnDownloadCssSource.setEnabled(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doSetValue(byte[] value) {
        streamResource = new StreamResource(new CssSource(), getFilename());
        byte[] oldValue = getValue();
        this.fieldValue = value;
        textArea.setValue(new String(value));
        if (fieldValue.length == 0) {
            btnDownloadCssSource.setEnabled(false);
        } else {
            btnDownloadCssSource.setEnabled(true);
        }
        fireEvent(new ValueChangeEvent<byte[]>(this, oldValue, true));
    }

    @Override
    public byte[] getValue() {
        return fieldValue;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public class CssSource implements StreamResource.StreamSource {
        @Override
        public InputStream getStream() {
            return new ByteArrayInputStream(fieldValue);
        }
    }
}
