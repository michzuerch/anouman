package com.gmail.michzuerch.anouman.presentation.ui.util.validator;

import com.gmail.michzuerch.anouman.presentation.ui.report.jasper.xmlvalidation.ResourceResolver;
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

public class JRXMLValidator implements Validator<byte[]> {
    String errormessage = new String();

    private boolean compileJRXML(byte[] val) {
        JasperReport jasperReport = null;
        try {
            jasperReport = JasperCompileManager
                    .compileReport(new ByteArrayInputStream(val));
        } catch (Exception e) {
            e.printStackTrace();
            errormessage = e.getMessage();
            return false;
        }
        return true;
    }

    private boolean verifyValidatesInternalXsd(byte[] val) {
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setNamespaceAware(true);

            DocumentBuilder parser = builderFactory
                    .newDocumentBuilder();
            Document document = parser.parse(new ByteArrayInputStream(val));

            SchemaFactory factory = SchemaFactory
                    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            factory.setResourceResolver(new ResourceResolver());
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
        //@todo Wildfly hängt nach Compilierung
        //if (compileJRXML(value) == false) return ValidationResult.error("Compiler-Fehler: " + errormessage);
        //if (verifyValidatesInternalXsd(value)==false) return ValidationResult.error("Validierung XML-Schema fehlgeschlagen" + errormessage);
        return ValidationResult.ok();
    }
}
