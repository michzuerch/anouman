package com.gmail.michzuerch.anouman.presentation.ui.util.field;

import com.vaadin.server.StreamResource;
import com.vaadin.ui.*;
import org.apache.commons.io.IOUtils;
import server.droporchoose.UploadComponent;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageField extends CustomField<byte[]> {
    private byte[] fieldData;
    private UploadComponent upload = new UploadComponent();
    private Image image = new Image();

    public ImageField(String caption) {
        super();
        clear();
        setCaption(caption);
    }


    @Override
    public byte[] getEmptyValue() {
        byte[] emptyImage = new byte[0];
        try {
            emptyImage = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("/images/EmptyImage.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.err.println("Call getEmptyValue() len:" + emptyImage.length);
        return emptyImage;
    }
//
//    //@todo Validator für Bilder (NPE)
//    @Override
//    public Validator<byte[]> getDefaultValidator() {
//        return new ImageValidator();
//    }

    @Override
    protected void doSetValue(byte[] value) {
        byte[] oldValue = getValue();
        this.fieldData = value;
        System.err.println("doSetValue len:" + value.length);
        fireEvent(new ValueChangeEvent<byte[]>(this, oldValue, true));
    }

    @Override
    public byte[] getValue() {
        if (fieldData != null) System.err.println("getValue len:" + fieldData.length);
        return fieldData;
    }

    @Override
    protected Component initContent() {
        upload.setReceivedCallback(this::uploadReceived);
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setSpacing(true);
        image.setSource(new StreamResource(new ImageSource(), "EmptyImage.jpg"));
        image.setHeight(300, Unit.PIXELS);
//        addValueChangeListener(
//                event -> fireEvent(new ValueChangeEvent<byte[]>(this,
//                        event.getOldValue(), event.isUserOriginated())));
        layout.addComponents(image, upload);
        return layout;
    }

    private void uploadReceived(String s, Path path) {
        try {
            byte[] uploaded = Files.readAllBytes(Paths.get(path.toUri()));
            String mimeType = (Files.probeContentType(path));
            if (mimeType.equals("image/jpeg") || (mimeType.equals("image/png"))) {
                //fireEvent(new ValueChangeEvent<byte[]>(this, getValue(), true));
                doSetValue(uploaded);
                image.setSource(new StreamResource(new ImageSource(), "image.jpg"));
            } else {
                Notification.show("Nur Bilder als JPG oder PNG erlaubt (MIME-Type)", Notification.Type.ERROR_MESSAGE);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public class ImageSource implements StreamResource.StreamSource {
        @Override
        public InputStream getStream() {
            return new ByteArrayInputStream(getValue());
        }
    }
}
