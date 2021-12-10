/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.factoryfloor.application;

import org.xml.sax.SAXException;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.StringReader;

public class XsdValidator {

    protected Validator validator;

    public XsdValidator(String xsd) throws XsdValidationException {
        try {
            SchemaFactory schemaFac = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFac.newSchema(XsdValidator.class.getResource(xsd));
            validator = schema.newValidator();
        } catch (SAXException e){
            throw new XsdValidationException(e);
        }
    }

    public void validate(Source source) throws XsdValidationException {
        try {
            validator.validate(source);
        } catch (SAXException e) {
            throw new XsdValidationException(e);
        } catch (IOException e) {
            throw new XsdValidationException(e);
        }
    }

    public void validate(String xml) throws XsdValidationException {
        validate(new StreamSource(new StringReader(xml)));
    }

}