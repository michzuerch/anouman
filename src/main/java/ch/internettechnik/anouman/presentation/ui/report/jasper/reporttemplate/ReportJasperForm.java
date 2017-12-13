package ch.internettechnik.anouman.presentation.ui.report.jasper.reporttemplate;

import ch.internettechnik.anouman.backend.entity.report.jasper.ReportJasper;
import ch.internettechnik.anouman.presentation.ui.converter.ByteToStringConverter;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import org.apache.commons.lang3.SerializationUtils;
import org.vaadin.viritin.form.AbstractForm;
import server.droporchoose.UploadComponent;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@ViewScoped
public class ReportJasperForm extends AbstractForm<ReportJasper> {
    TextField bezeichnung = new TextField("Bezeichnung");
    UploadComponent upload = new UploadComponent();
    Button validateAndCompileButton = new Button("Validate and Compile");
    TextArea templateSource = new TextArea("Template JRXML");
    TextArea templateCompiled = new TextArea("Compiled Source");

    private String filename;

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
        getBinder().forField(templateCompiled).withConverter(new ByteToStringConverter()).bind("templateCompiled");

        //StreamResource templateResource = createStreamResource();
        //FileDownloader fileDownloader = new FileDownloader(templateResource);
        //fileDownloader.extend(downloadButton);

        validateAndCompileButton.addClickListener(event -> {
            if (compileJRXML(getEntity().getTemplateSource())) {
                Notification.show("Compiling verfolgreich", Notification.Type.TRAY_NOTIFICATION);
            } else {
                Notification.show("Fehler beim Compilieren", Notification.Type.ERROR_MESSAGE);
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
            setFilename(fileName);
            templateSource.setValue(new String(data, "UTF-8"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private boolean compileJRXML(byte[] val) {
        JasperReport jasperReport = null;
        try {
            jasperReport = JasperCompileManager
                    .compileReport(new ByteArrayInputStream(val));
            templateCompiled.setValue(new String(SerializationUtils.serialize(jasperReport), "UTF-8"));
            //templateCompiled.setValue(jasperReport.toString());
            Notification.show("Report erfolgreich compiliert: " + jasperReport.getCompilerClass(), Notification.Type.TRAY_NOTIFICATION);
        } catch (Exception e) {
            e.printStackTrace();
            templateSource.setComponentError(new UserError(e.getMessage()));
            Notification.show("Fehler beim Compilieren: " + e.getLocalizedMessage(), Notification.Type.TRAY_NOTIFICATION);
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

//    private boolean verifyValidatesInternalXsd(byte[] val) {
//        InputStream xmlStream = null;
//        try {
//            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
//            builderFactory.setNamespaceAware(true);
//
//            DocumentBuilder parser = builderFactory
//                    .newDocumentBuilder();
//
//            // parse the XML into a document object
//            Document document = parser.parse(new ByteArrayInputStream(val));
//
//            SchemaFactory factory = SchemaFactory
//                    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//
//            // associate the schema factory with the resource resolver, which is responsible for resolving the imported XSD's
//            factory.setResourceResolver(new ResourceResolver());
//
//            // note that if your XML already declares the XSD to which it has to conform, then there's no need to create a validator from a Schema object
//            Source schemaFile = new StreamSource(getClass().getClassLoader()
//                    .getResourceAsStream("/schema/jasperreport.xsd"));
//
//            Schema schema = factory.newSchema(schemaFile);
//
//            Validator validator = schema.newValidator();
//            validator.validate(new DOMSource(document));
//
//            return true;
//        } catch (FileNotFoundException e) {
//            templateSource.setComponentError(new UserError(e.getLocalizedMessage()));
//            Notification.show(e.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);
//            e.printStackTrace();
//            return false;
//        } catch (ParserConfigurationException e) {
//            templateSource.setComponentError(new UserError(e.getLocalizedMessage()));
//            Notification.show(e.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);
//            e.printStackTrace();
//            return false;
//        } catch (IOException e) {
//            templateSource.setComponentError(new UserError(e.getLocalizedMessage()));
//            Notification.show(e.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);
//            e.printStackTrace();
//            return false;
//        } catch (SAXException e) {
//            templateSource.setComponentError(new UserError(e.getLocalizedMessage()));
//            Notification.show(e.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);
//            e.printStackTrace();
//            return false;
//        }
//    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }


}
