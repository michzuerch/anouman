package ch.internettechnik.anouman.presentation.ui.report.jasper.reporttemplate;

import ch.internettechnik.anouman.backend.entity.report.jasper.ReportJasper;
import ch.internettechnik.anouman.presentation.ui.converter.ByteToStringConverter;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.*;
import org.vaadin.viritin.form.AbstractForm;
import server.droporchoose.UploadComponent;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@ViewScoped
public class ReportJasperForm extends AbstractForm<ReportJasper> {
    TextField bezeichnung = new TextField("Bezeichnung");
    UploadComponent upload = new UploadComponent();
    TextArea template = new TextArea("Template JRXML");
    Button downloadButton = new Button("Download");
    //Image image = new Image("Bild");

    public ReportJasperForm() {
        super(ReportJasper.class);
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setCaption("Report Jasper");
        //openInModalPopup.setWidth("500px");
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {
        getBinder().forField(template).withConverter(new ByteToStringConverter()).bind("template");

        StreamResource templateResource = createResource();
        FileDownloader fileDownloader = new FileDownloader(templateResource);
        fileDownloader.extend(downloadButton);
        downloadButton.setEnabled(false);
        if (getEntity().getTemplate() == null) {
            if (getEntity().getTemplate().length > 1) downloadButton.setEnabled(true);
        }

        template.addValueChangeListener(event -> {
            if (getEntity().getTemplate() != null) {
                if (getEntity().getTemplate().length > 1) downloadButton.setEnabled(true);
            }
        });

        template.setWidth(500, Unit.PIXELS);
        template.setRows(20);
        upload.setCaption("Upload");
        upload.setReceivedCallback(this::uploadReceived);
        return new VerticalLayout(new FormLayout(
                bezeichnung, template, upload, downloadButton
        ), getToolbar());
    }

    private void uploadReceived(String fileName, Path path) {
        try {
            byte[] data = Files.readAllBytes(Paths.get(path.toUri()));
            getEntity().setTemplate(data);
            getEntity().setFilename(fileName);
            template.setValue(new String(data, "UTF-8"));

/*
            StreamResource.StreamSource streamSource = new StreamResource.StreamSource() {
                public InputStream getStream() {
                    return (data == null) ? null : new ByteArrayInputStream(data);
                }
            };
            StreamResource imageResource = new StreamResource(streamSource, fileName);
*/

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private StreamResource createResource() {
        return new StreamResource(new StreamResource.StreamSource() {
            @Override
            public InputStream getStream() {
                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bos.write(getEntity().getTemplate());
                    return new ByteArrayInputStream(bos.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }

            }
        }, getEntity().getFilename());
    }
}
