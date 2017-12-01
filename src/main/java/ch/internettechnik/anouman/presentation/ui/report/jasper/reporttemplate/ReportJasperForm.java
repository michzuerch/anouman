package ch.internettechnik.anouman.presentation.ui.report.jasper.reporttemplate;

import ch.internettechnik.anouman.backend.entity.report.jasper.ReportJasper;
import ch.internettechnik.anouman.presentation.ui.converter.ByteToStringConverter;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.*;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import org.vaadin.viritin.fields.LabelField;
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
    Button compileButton = new Button("Compile JRXML to .jasper");
    TextArea template = new TextArea("Template JRXML");
    LabelField<String> filename = new LabelField<>("Filename");
    Button downloadButton = new Button("Download");


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

        compileButton.addClickListener(event -> {
            JasperReport jasperReport = null;
            //JasperCompileManager.compileReport(getResourceAsStream("Sample_Report.jrxml"));
            try {
                jasperReport = JasperCompileManager
                        .compileReport(new ByteArrayInputStream(getEntity().getTemplate()));
                System.out.println("jasper created " + jasperReport);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


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
                bezeichnung, compileButton, template, upload, filename, downloadButton
        ), getToolbar());
    }

    private void uploadReceived(String fileName, Path path) {
        try {
            byte[] data = Files.readAllBytes(Paths.get(path.toUri()));
            getEntity().setTemplate(data);
            getEntity().setFilename(fileName);
            template.setValue(new String(data, "UTF-8"));
            filename.setValue(fileName);

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
