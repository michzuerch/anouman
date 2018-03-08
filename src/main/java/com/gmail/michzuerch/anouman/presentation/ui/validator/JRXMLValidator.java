package com.gmail.michzuerch.anouman.presentation.ui.validator;

import com.gmail.michzuerch.anouman.presentation.ui.report.jasper.reporttemplate.xmlvalidation.ResourceResolver;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class JRXMLValidator implements Validator<byte[]> {
    String errormessage = new String();

    private boolean compileJRXML(byte[] val) {
        JasperReport jasperReport = null;
        try {
            jasperReport = JasperCompileManager
                    .compileReport(new ByteArrayInputStream(val));
            //templateCompiled.setValue(new String(SerializationUtils.serialize(jasperReport), "UTF-8"));
            //templateCompiled.setValue(jasperReport.toString());
            //Notification.show("Report erfolgreich compiliert: " + jasperReport.getCompilerClass(), Notification.Type.TRAY_NOTIFICATION);
        } catch (Exception e) {
            e.printStackTrace();
            errormessage = e.getMessage();
            //Notification.show("Fehler beim Compilieren: " + e.getLocalizedMessage(), Notification.Type.TRAY_NOTIFICATION);
            return false;
        }
        return true;
    }

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

            javax.xml.validation.Validator validator = schema.newValidator();
            validator.validate(new DOMSource(document));

            return true;
        } catch (FileNotFoundException e) {
            errormessage = e.getLocalizedMessage();
            return false;
        } catch (ParserConfigurationException e) {
            errormessage = e.getLocalizedMessage();
            return false;
        } catch (IOException e) {
            errormessage = e.getLocalizedMessage();
            return false;
        } catch (SAXException e) {
            errormessage = e.getLocalizedMessage();
            return false;
        }
    }

    @Override
    public ValidationResult apply(byte[] value, ValueContext context) {
        if (compileJRXML(value) == false) return ValidationResult.error("Compiler-Fehler: " + errormessage);
        return ValidationResult.ok();
    }
}
