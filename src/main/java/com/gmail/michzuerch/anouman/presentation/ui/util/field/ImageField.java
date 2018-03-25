package com.gmail.michzuerch.anouman.presentation.ui.util.field;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileDownloader;
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
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageField extends CustomField<byte[]> {
    private byte[] fieldValue = new byte[0];
    private String mimetype = new String();

    private StreamResource streamResource = new StreamResource(new ImageSource(), "image.jpg");

    private UploadComponent upload = new UploadComponent();
    private Image image = new Image();
    private Button downloadButton = new Button("Download Image");

    @Override
    protected Component initContent() {
        upload.setReceivedCallback(this::uploadReceived);
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setSpacing(true);
        image.setWidth(300, Unit.PIXELS);
        FileDownloader fileDownloader = new FileDownloader(streamResource);
        fileDownloader.extend(downloadButton);
        downloadButton.setIcon(VaadinIcons.DOWNLOAD);
        downloadButton.setWidth(200, Unit.PIXELS);
        upload.setWidth(200, Unit.PIXELS);

        layout.addComponents(image, new HorizontalLayout(upload, downloadButton));
        return layout;
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

    protected String getFilename() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        StringBuffer filename = new StringBuffer(df.format(new Date()) + "-image");
        filename.append(".jpeg");
        System.err.println("ImageField getFilename(): " + filename);
        return filename.toString();
    }


    @Override
    protected void doSetValue(byte[] value) {
        byte[] oldValue = fieldValue;
        System.err.println("ImageField doSetValue: " + value.length);
        fieldValue = value;
        streamResource = new StreamResource(new ImageSource(), getFilename());
        streamResource.setCacheTime(0);
        image.setSource(streamResource);
        image.markAsDirty();
        fireEvent(new ValueChangeEvent<byte[]>(this, oldValue, true));
    }

    @Override
    public byte[] getValue() {
        System.err.println("ImageField getValue: " + fieldValue.length);
        return fieldValue;
    }

    private void uploadReceived(String s, Path path) {
        try {
            byte[] uploaded = Files.readAllBytes(Paths.get(path.toUri()));
            String mimeType = (Files.probeContentType(path));
            if (mimeType.equals("image/jpeg") || (mimeType.equals("image/png"))) {
                setMimetype(mimeType);
                doSetValue(uploaded);
            } else {
                Notification.show("Nur Bilder als JPG oder PNG erlaubt (MIME-Type)", Notification.Type.ERROR_MESSAGE);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public class ImageSource implements StreamResource.StreamSource {
        @Override
        public InputStream getStream() {
            return new ByteArrayInputStream(fieldValue);
        }
    }
}
