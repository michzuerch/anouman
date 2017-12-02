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
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import server.droporchoose.UploadComponent;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@ViewScoped
public class ReportJasperForm extends AbstractForm<ReportJasper> {
    TextField bezeichnung = new TextField("Bezeichnung");
    UploadComponent upload = new UploadComponent();
    Button compileButton = new Button("Compile JRXML to .jasper");
    Button validateButton = new Button("Validate XML to Schema");
    TextArea template = new TextArea("Template JRXML");
    TextArea validationresult = new TextArea("Compile Result");
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
        getBinder().forField(template).withConverter(new ByteToStringConverter()).withValidator(new ReportJasperValidator()).bind("template");

        StreamResource templateResource = createStreamResource();
        FileDownloader fileDownloader = new FileDownloader(templateResource);
        fileDownloader.extend(downloadButton);

        validateButton.addClickListener(event -> {
            if (verifyValidatesInternalXsd(getEntity().getTemplate())) {
                System.err.println("Valid");
                Notification.show("XML valid", Notification.Type.ERROR_MESSAGE);
            } else {
                System.err.println("Not Valid");
                Notification.show("XML NOT valid", Notification.Type.ERROR_MESSAGE);
            }

        });

        compileButton.addClickListener(event -> {
            JasperReport jasperReport = null;
            try {
                jasperReport = JasperCompileManager
                        .compileReport(new ByteArrayInputStream(getEntity().getTemplate()));
                validationresult.setValue("Report erfolgreich compiliert: " + jasperReport.getCompilerClass());
            } catch (Exception e) {
                validationresult.setValue(e.getLocalizedMessage());
            }
        });

        validationresult.setWidth(500, Unit.PIXELS);
        validationresult.setRows(3);

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
                bezeichnung, compileButton, validateButton, validationresult, template, upload, filename, downloadButton
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

    private StreamResource createStreamResource() {
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

    private boolean isJRXMLValid(byte[] val) {
        Source valSource = new StreamSource(new ByteArrayInputStream(getEntity().getTemplate()));
        SchemaFactory schemaFactory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        Source schemaSource = new StreamSource(
                getClass().getResourceAsStream("/schema/" + "jasperreport.xsd"),
                getClass().getResource("/schema/" + "jasperreport.xsd").toString());


        Schema schema;
        try {
            schema = schemaFactory.newSchema(schemaSource);
            Validator validator = schema.newValidator();
            validator.validate(valSource);
            //System.out.println(valSource.getSystemId() + " is valid");
        } catch (SAXException e) {
            //System.out.println(valSource.getSystemId() + " is NOT valid reason:" + e);
            return false;
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    private boolean verifyValidatesInternalXsd(byte[] val) {
        InputStream xmlStream = null;
        try {
            //xmlStream = new FileInputStream(filename);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            factory.setNamespaceAware(true);
            factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
                    "http://www.w3.org/2001/XMLSchema");
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new RaiseOnErrorHandler());
            builder.parse(new ByteArrayInputStream(val));
            //xmlStream.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (SAXException e) {
            e.printStackTrace();
            return false;
        }
    }

    public class RaiseOnErrorHandler implements ErrorHandler {
        public void warning(SAXParseException e) {
            throw new RuntimeException(e);
        }

        public void error(SAXParseException e) {
            throw new RuntimeException(e);
        }

        public void fatalError(SAXParseException e) {
            throw new RuntimeException(e);
        }
    }
}
