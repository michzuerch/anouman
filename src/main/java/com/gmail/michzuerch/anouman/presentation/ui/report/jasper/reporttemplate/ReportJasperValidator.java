package com.gmail.michzuerch.anouman.presentation.ui.report.jasper.reporttemplate;

import com.gmail.michzuerch.anouman.presentation.ui.report.jasper.reporttemplate.xmlvalidation.ResourceResolver;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;
import com.vaadin.ui.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

public class ReportJasperValidator implements Validator<byte[]> {
    private static Logger logger = LoggerFactory.getLogger(ReportJasperValidator.class.getName());

    @Override
    public ValidationResult apply(byte[] bytes, ValueContext valueContext) {
        if (bytes.length < 1) {
            return ValidationResult.error("Länge ist " + bytes.length);
        } else {
            if (verifyValidatesInternalXsd(bytes)) {
                return ValidationResult.ok();
            } else {
                return ValidationResult.error(
                        "XML-Validierung fehlgeschlagen");
            }
        }
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
            Notification.show(e.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        } catch (ParserConfigurationException e) {
            Notification.show(e.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            Notification.show(e.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        } catch (SAXException e) {
            Notification.show(e.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
}
