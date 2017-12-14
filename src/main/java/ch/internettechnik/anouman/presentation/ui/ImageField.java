package ch.internettechnik.anouman.presentation.ui;

import com.vaadin.server.ClassResource;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;
import server.droporchoose.UploadComponent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageField extends CustomField<byte[]> {
    private byte[] data;
    private UploadComponent upload = new UploadComponent();
    private Image image = new Image();

    @Override
    protected void doSetValue(byte[] value) {
        this.data = value;
        StreamResource imageResource =
                new StreamResource(new ImageStreamSource(getValue()), "initial-filename.jpg");
        imageResource.setCacheTime(0);
        image.setSource(imageResource);
    }

    @Override
    public byte[] getValue() {
        return data;
    }

    @Override
    protected Component initContent() {
        upload.setReceivedCallback(this::uploadReceived);
        Resource res = new ClassResource("/header.jpg");
        if (getValue() == null) {
            image = new Image(null, res);
        } else {
            image.setSource(new StreamResource(new ImageStreamSource(getValue()), "image.jpg"));
        }

        image.setWidth(400, Unit.PIXELS);

        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setSpacing(false);
        layout.addComponents(image, upload);
        return layout;
    }

    private void uploadReceived(String s, Path path) {
        try {
            byte[] data = Files.readAllBytes(Paths.get(path.toUri()));
            setValue(data);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
