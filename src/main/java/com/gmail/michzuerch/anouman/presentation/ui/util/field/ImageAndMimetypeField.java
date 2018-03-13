package com.gmail.michzuerch.anouman.presentation.ui.util.field;

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

//@todo JPG und PNG unterscheiden wegen Dateiendung
public class ImageAndMimetypeField extends CustomField<ImageAndMimetype> {
    private ImageAndMimetype fieldData = new ImageAndMimetype();
    private UploadComponent upload = new UploadComponent();
    private Image image = new Image();
    private Label size = new Label("Size:");
    private Button downloadButton = new Button("Download Image");

    public ImageAndMimetypeField(String caption) {
        super();
        setCaption(caption);
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
        fieldData.setImage(emptyImage);
        fieldData.setMimetype("image/jpeg");
        return fieldData;
    }
//
//    //@todo Validator für Bilder (NPE)
//    @Override
//    public Validator<byte[]> getDefaultValidator() {
//        return new ImageValidator();
//    }

    //@todo dateiendung
    protected String getFilename() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String filename = df.format(new Date()) + "-image";
        if (fieldData.getMimetype().equals("image/jpeg")) {
            filename.concat(".jpeg");
        }
        if (fieldData.getMimetype().equals("image/png")) {
            filename.concat(".png");
        }
        return filename;
    }


    @Override
    protected void doSetValue(ImageAndMimetype value) {
        ImageAndMimetype oldValue = getValue();
        this.fieldData = value;
        StreamResource streamResource = new StreamResource(new ImageSource(), getFilename());
        streamResource.setCacheTime(0);
        image = new Image();
        image.setSource(streamResource);
        image.markAsDirty();
        //System.err.println("doSetValue len:" + value.length);
        fireEvent(new ValueChangeEvent<ImageAndMimetype>(this, oldValue, true));
    }

    @Override
    public ImageAndMimetype getValue() {
        if (fieldData.getImage() != null) System.err.println("getValue len:" + fieldData.getImage().length);
        size.setValue(String.valueOf(fieldData.getImage().length));
        StreamResource streamResource = new StreamResource(new ImageSource(), getFilename());
        streamResource.setCacheTime(0);
        image.setSource(streamResource);
        image.markAsDirty();
        return new ImageAndMimetype(fieldData.getImage(), fieldData.getMimetype());
    }

    @Override
    protected Component initContent() {
        upload.setReceivedCallback(this::uploadReceived);
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setSpacing(true);

        StreamResource streamResource = new StreamResource(new ImageSource(), getFilename());
        streamResource.setCacheTime(0);

        FileDownloader fileDownloader = new FileDownloader(streamResource);
        fileDownloader.extend(downloadButton);


        image.setHeight(300, Unit.PIXELS);
        layout.addComponents(image, upload, size, downloadButton);
        return layout;
    }

    private void uploadReceived(String s, Path path) {
        try {
            byte[] uploaded = Files.readAllBytes(Paths.get(path.toUri()));
            String mimeType = (Files.probeContentType(path));
            if (mimeType.equals("image/jpeg") || (mimeType.equals("image/png"))) {
                doSetValue(new ImageAndMimetype(uploaded, mimeType));
            } else {
                doSetValue(getEmptyValue());
                Notification.show("Nur Bilder als JPG oder PNG erlaubt (MIME-Type)", Notification.Type.ERROR_MESSAGE);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public class ImageSource implements StreamResource.StreamSource {
        @Override
        public InputStream getStream() {
            return new ByteArrayInputStream(fieldData.getImage());
        }
    }
}
