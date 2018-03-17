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

public class ImageAndMimetypeField extends CustomField<ImageAndMimetype> {
    private ImageAndMimetype fieldValue = new ImageAndMimetype();
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
    public ImageAndMimetype getEmptyValue() {
        byte[] emptyImage = new byte[0];
        try {
            emptyImage = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("/images/EmptyImage.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.err.println("Call getEmptyValue() len:" + emptyImage.length);
        fieldValue.setImage(emptyImage);
        fieldValue.setMimetype("image/jpeg");
        return fieldValue;
    }

    //
//    //@todo Validator für Bilder (NPE)
//    @Override
//    public Validator<byte[]> getDefaultValidator() {
//        return new ImageValidator();
//    }

    protected String getFilename() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        StringBuffer filename = new StringBuffer(df.format(new Date()) + "-image");
        if (fieldValue.getMimetype().equals("image/jpeg")) {
            filename.append(".jpeg");
        }
        if (fieldValue.getMimetype().equals("image/png")) {
            filename.append(".png");
        }
        System.err.println("ImageAndMimetypeField getFilename(): " + filename);
        return filename.toString();
    }

    @Override
    protected void doSetValue(ImageAndMimetype value) {
        ImageAndMimetype oldValue = fieldValue;
        System.err.println("ImageAndMimetypeField doSetValue len:" + value.getImage().length + " Mimetype: " + value.getMimetype());
        fieldValue = value;
        streamResource = new StreamResource(new ImageSource(), getFilename());
        streamResource.setCacheTime(0);
        image.setSource(streamResource);
        image.markAsDirty();
        fireEvent(new ValueChangeEvent<ImageAndMimetype>(this, oldValue, true));
    }

    @Override
    public ImageAndMimetype getValue() {
        System.err.println("ImageAndMimetypeField getValue: " + fieldValue.getImage().length);
        return fieldValue;
    }

    private void uploadReceived(String s, Path path) {
        try {
            byte[] uploaded = Files.readAllBytes(Paths.get(path.toUri()));
            String mimeType = (Files.probeContentType(path));
            if (mimeType.equals("image/jpeg") || (mimeType.equals("image/png"))) {
                doSetValue(new ImageAndMimetype(uploaded, mimeType));
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
            return new ByteArrayInputStream(fieldValue.getImage());
        }
    }
}
