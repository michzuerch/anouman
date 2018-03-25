package com.gmail.michzuerch.anouman.presentation.ui.util.field;

import com.gmail.michzuerch.anouman.presentation.ui.util.validator.FOPXmlValidator;
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

public class FOPXmlField extends CustomField<byte[]> {
    private byte[] fieldValue;
    private String filename = new String();
    private StreamResource streamResource = new StreamResource(new FOPXmlSource(), "fop.xml");
    private UploadComponent upload = new UploadComponent();
    private TextArea textArea = new TextArea("FOP Xml");
    private Button btnDownloadFOPXmlSource = new Button("Download");

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
        fileDownloader.extend(btnDownloadFOPXmlSource);

        layout.addComponent(new HorizontalLayout(upload, btnDownloadFOPXmlSource));
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
        btnDownloadFOPXmlSource.setEnabled(false);
        return new byte[0];
    }

    @Override
    public Validator<byte[]> getDefaultValidator() {
        return new FOPXmlValidator();
    }

    private void upload(String filename, Path path) {
        try {
            byte[] uploaded = Files.readAllBytes(path);
            setFilename(filename);
            streamResource.setFilename(getFilename());
            System.err.println("Uploaded Bytes: " + uploaded.length);
            doSetValue(uploaded);
            btnDownloadFOPXmlSource.setEnabled(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doSetValue(byte[] value) {
        streamResource = new StreamResource(new FOPXmlSource(), getFilename());
        byte[] oldValue = getValue();
        this.fieldValue = value;
        textArea.setValue(new String(value));
        fireEvent(new ValueChangeEvent<byte[]>(this, oldValue, true));
    }

    @Override
    public byte[] getValue() {
        if (fieldValue.length == 0) {
            btnDownloadFOPXmlSource.setEnabled(false);
        } else {
            btnDownloadFOPXmlSource.setEnabled(true);
        }
        return fieldValue;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public class FOPXmlSource implements StreamResource.StreamSource {
        @Override
        public InputStream getStream() {
            return new ByteArrayInputStream(fieldValue);
        }
    }
}
