package com.gmail.michzuerch.anouman.presentation.ui.report.jasper;

import com.gmail.michzuerch.anouman.backend.entity.report.jasper.ReportJasper;
import com.gmail.michzuerch.anouman.presentation.ui.util.field.JasperXmlField;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.viritin.form.AbstractForm;

public class ReportJasperForm extends AbstractForm<ReportJasper> {
    private static Logger logger = LoggerFactory.getLogger(ReportJasperForm.class.getName());

    TextField bezeichnung = new TextField("Bezeichnung");
    JasperXmlField templateSource = new JasperXmlField();

    private String filename;

    public ReportJasperForm() {
        super(ReportJasper.class);
    }

    @Override
    public Window openInModalPopup() {
        final Window openInModalPopup = super.openInModalPopup();
        openInModalPopup.setCaption("Report Jasper");
        openInModalPopup.setWidth(800, Unit.PIXELS);
        return openInModalPopup;
    }

    @Override
    protected Component createContent() {
        //getBinder().forField(templateSource).withConverter(new ByteToStringConverter()).withValidator(new ReportJasperValidator()).bind("templateSource");
        //getBinder().forField(templateCompiled).withConverter(new ByteToStringConverter()).bind("templateCompiled");

        //StreamResource templateResource = createStreamResource();
        //FileDownloader fileDownloader = new FileDownloader(templateResource);
        //fileDownloader.extend(downloadButton);

//        validateAndCompileButton.addClickListener(event -> {
//            if (compileJRXML(getEntity().getTemplateSource())) {
//                Notification.show("Compiling verfolgreich", Notification.Type.TRAY_NOTIFICATION);
//            } else {
//                Notification.show("Fehler beim Compilieren", Notification.Type.ERROR_MESSAGE);
//            }
//
//        });

        return new VerticalLayout(new FormLayout(
                bezeichnung, templateSource), getToolbar());
    }


//    private boolean compileJRXML(byte[] val) {
//        JasperReport jasperReport = null;
//        try {
//            jasperReport = JasperCompileManager
//                    .compileReport(new ByteArrayInputStream(val));
//            templateCompiled.setValue(new String(SerializationUtils.serialize(jasperReport), "UTF-8"));
//            //templateCompiled.setValue(jasperReport.toString());
//            Notification.show("Report erfolgreich compiliert: " + jasperReport.getCompilerClass(), Notification.Type.TRAY_NOTIFICATION);
//        } catch (Exception e) {
//            e.printStackTrace();
//            templateSource.setComponentError(new UserError(e.getMessage()));
//            Notification.show("Fehler beim Compilieren: " + e.getLocalizedMessage(), Notification.Type.TRAY_NOTIFICATION);
//            return false;
//        }
//        return true;
//    }

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
//            alidator validator = schema.newValidator();
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


}
