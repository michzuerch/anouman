package ch.internettechnik.anouman.presentation.ui;

import com.vaadin.server.StreamResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import server.droporchoose.UploadComponent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageField extends CustomField<byte[]> {
    private byte[] imageData;
    private UploadComponent upload = new UploadComponent();
    private Image image = new Image();

    private String filename;
    private String mimeType;


//    @Override
//    public byte[] getEmptyValue() {
//        ThemeResource themeResource = new ThemeResource("img/EmptyImage.jpg");
//        themeResource.
//        return new byte[0];
//    }

    @Override
    protected void doSetValue(byte[] value) {
        this.imageData = value;
        if (value == null) {
            image.setSource(new ThemeResource("img/EmptyImage.jpg"));
            image.markAsDirty();
            System.err.println("Empty Image");
        } else {
            StreamResource imageResource = new StreamResource(new ImageStreamSource(getValue()), "image.jpg");
            imageResource.setCacheTime(0);
            image.setSource(imageResource);
            image.markAsDirty();
            System.err.println("Image from DB:" + getValue().length);
        }
    }

    @Override
    public byte[] getValue() {
        return imageData;
    }

    @Override
    protected Component initContent() {
        upload.setReceivedCallback(this::uploadReceived);
        image.setWidth(400, Unit.PIXELS);
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setSpacing(false);
        layout.addComponents(image, upload);
        return layout;
    }

    private void uploadReceived(String s, Path path) {
        try {
            byte[] uploadData = Files.readAllBytes(Paths.get(path.toUri()));
            setFilename(s);
            setMimeType(Files.probeContentType(path));
            if (getMimeType().equals("image/jpeg") || (getMimeType().equals("image/png"))) {
                doSetValue(uploadData);
            } else {
                Notification.show("Nur Bilder als JPG oder PNG erlaubt (MIME-Type)", Notification.Type.ERROR_MESSAGE);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
