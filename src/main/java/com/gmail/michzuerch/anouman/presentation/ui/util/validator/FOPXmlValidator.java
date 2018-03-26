
package com.gmail.michzuerch.anouman.presentation.ui.util.validator;

import com.gmail.michzuerch.anouman.presentation.ui.util.validator.xmlvalidation.ResourceResolver;
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

public class FOPXmlValidator implements Validator<byte[]> {
    private String errormessage = new String();

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
        System.err.println("Validate Compile erfolgreich");
        return true;
    }

    public boolean verifyValidatesInternalXsd(byte[] val) {
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
                    .getResourceAsStream("/XmlLocalValidationSchema/jasperreport.xsd"));

            Schema schema = factory.newSchema(schemaFile);

            javax.xml.validation.Validator validator = schema.newValidator();
            validator.validate(new DOMSource(document));

            System.err.println("Validate Xsd erfolgreich");
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (ParserConfigurationException e) {
            return false;
        } catch (IOException e) {
            return false;
        } catch (SAXException e) {
            return false;
        }
    }

    public String getErrormessage() {
        return errormessage;
    }

    public void setErrormessage(String errormessage) {
        this.errormessage = errormessage;
    }

    @Override
    public ValidationResult apply(byte[] value, ValueContext context) {
        System.err.println("Validator apply len: " + value.length);
        if (value.length == 0) return ValidationResult.error("Länge 0");
        if (compileJRXML(value) == false) return ValidationResult.error("Compiler-Fehler: " + errormessage);
        if (verifyValidatesInternalXsd(value) == false)
            return ValidationResult.error("Validierung XML-Schema fehlgeschlagen" + errormessage);
        return ValidationResult.ok();
    }
}
