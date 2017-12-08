package ch.internettechnik.anouman.presentation.ui.report.jasper.reporttemplate;

import ch.internettechnik.anouman.backend.entity.report.jasper.ReportJasper;
import ch.internettechnik.anouman.presentation.ui.converter.ByteToStringConverter;
import ch.internettechnik.anouman.presentation.ui.report.jasper.reporttemplate.validation.ResourceResolver;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import org.apache.commons.lang3.SerializationUtils;
import org.vaadin.viritin.form.AbstractForm;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import server.droporchoose.UploadComponent;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@ViewScoped
public class ReportJasperForm extends AbstractForm<ReportJasper> {
    TextField bezeichnung = new TextField("Bezeichnung");
    UploadComponent upload = new UploadComponent();
    Button validateAndCompileButton = new Button("Validate and Compile");
    TextArea templateSource = new TextArea("Template JRXML");

    private String filename;
    private byte[] compiledReport;

    public ReportJasperForm() {
        super(ReportJasper.class);
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setCaption("Report Jasper");
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {
        getBinder().forField(templateSource).withConverter(new ByteToStringConverter()).withValidator(new ReportJasperValidator()).bind("templateSource");

        //StreamResource templateResource = createStreamResource();
        //FileDownloader fileDownloader = new FileDownloader(templateResource);
        //fileDownloader.extend(downloadButton);

        validateAndCompileButton.addClickListener(event -> {
            if (verifyValidatesInternalXsd(getEntity().getTemplateSource())) {
                Notification.show("XML valid", Notification.Type.TRAY_NOTIFICATION);
            } else {
                Notification.show("XML NOT valid", Notification.Type.TRAY_NOTIFICATION);
            }
        });

        validateAndCompileButton.setEnabled(false);

        templateSource.addValueChangeListener(event -> {
            if (getEntity().getTemplateSource() != null) {
                if (getEntity().getTemplateSource().length > 1) {
                    validateAndCompileButton.setEnabled(true);
                }
            }
        });

        templateSource.setWidth(700, Unit.PIXELS);
        templateSource.setRows(15);

        upload.setCaption("Upload");
        upload.setReceivedCallback(this::uploadReceived);

        return new VerticalLayout(new FormLayout(
                bezeichnung, validateAndCompileButton, templateSource, upload), getToolbar());
    }

    private void uploadReceived(String fileName, Path path) {
        try {
            byte[] data = Files.readAllBytes(Paths.get(path.toUri()));

            if (verifyValidatesInternalXsd(data)) {
                getEntity().setTemplateSource(data);
                if (compileJRXML(data)) {
                    getEntity().setTemplateSource(data);
                    setFilename(fileName);
                    templateSource.setValue(new String(data, "UTF-8"));
                }
            }
        } catch (IOException e) {
            Notification.show(e.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private boolean compileJRXML(byte[] val) {
        JasperReport jasperReport = null;
        try {
            jasperReport = JasperCompileManager
                    .compileReport(new ByteArrayInputStream(getEntity().getTemplateSource()));
            setCompiledReport(SerializationUtils.serialize(jasperReport));
            Notification.show("Report erfolgreich compiliert: " + jasperReport.getCompilerClass(), Notification.Type.TRAY_NOTIFICATION);
        } catch (Exception e) {
            e.printStackTrace();
            templateSource.setComponentError(new UserError(e.getMessage()));
            return false;
        }
        return true;
    }

//    private StreamResource createStreamResource() {
//        return new StreamResource(new StreamResource.StreamSource() {
//            @Override
//            public InputStream getStream() {
//                try {
//                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                    bos.write(getEntity().getTemplateSource());
//                    return new ByteArrayInputStream(bos.toByteArray());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return null;
//                }
//
//            }
//        }, getEntity().getFilename());
//    }

    private boolean verifyValidatesInternalXsd(byte[] val) {
        InputStream xmlStream = null;
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setNamespaceAware(true);

            DocumentBuilder parser = builderFactory
                    .newDocumentBuilder();

            // parse the XML into a document object
            Document document = parser.parse(new ByteArrayInputStream(val));

            SchemaFactory factory = SchemaFactory
                    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            // associate the schema factory with the resource resolver, which is responsible for resolving the imported XSD's
            factory.setResourceResolver(new ResourceResolver());

            // note that if your XML already declares the XSD to which it has to conform, then there's no need to create a validator from a Schema object
            Source schemaFile = new StreamSource(getClass().getClassLoader()
                    .getResourceAsStream("/schema/jasperreport.xsd"));

            Schema schema = factory.newSchema(schemaFile);

            Validator validator = schema.newValidator();
            validator.validate(new DOMSource(document));

            return true;
        } catch (FileNotFoundException e) {
            templateSource.setComponentError(new UserError(e.getLocalizedMessage()));
            Notification.show(e.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        } catch (ParserConfigurationException e) {
            templateSource.setComponentError(new UserError(e.getLocalizedMessage()));
            Notification.show(e.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            templateSource.setComponentError(new UserError(e.getLocalizedMessage()));
            Notification.show(e.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        } catch (SAXException e) {
            templateSource.setComponentError(new UserError(e.getLocalizedMessage()));
            Notification.show(e.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }

    public byte[] getCompiledReport() {
        return compiledReport;
    }

    public void setCompiledReport(byte[] compiledReport) {
        this.compiledReport = compiledReport;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }


}
