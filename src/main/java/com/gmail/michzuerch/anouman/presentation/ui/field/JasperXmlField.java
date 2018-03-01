package com.gmail.michzuerch.anouman.presentation.ui.field;

import com.vaadin.ui.*;
import server.droporchoose.UploadComponent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JasperXmlField extends CustomField<byte[]> {
    private byte[] fielddata;

    private Button validateButton = new Button("Validate");
    private UploadComponent upload = new UploadComponent();
    private TextArea textArea = new TextArea("JXRML");


    @Override
    protected Component initContent() {
        upload.setReceivedCallback(this::upload);
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(false);
        layout.setMargin(false);
        layout.addComponent(upload);
        layout.addComponent(validateButton);
        layout.addComponent(textArea);
        textArea.addValueChangeListener(
                event -> fireEvent(new ValueChangeEvent<byte[]>(this,
                        event.getOldValue().getBytes(), event.isUserOriginated())));


        //        textArea.addValueChangeListener(
//                event -> {
//                    fireEvent(new ValueChangeEvent<>(this,
//                            event.getValue(), event.isUserOriginated()));
//                    System.err.println("VcE:" + event.getValue());
////                   textValue=event.getValue();
//                    this.textArea = event.getValue();
//                }
//        );


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
