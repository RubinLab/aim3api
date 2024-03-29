/*
 * Copyright (c) 2011, The Board of Trustees of the Leland Stanford Junior 
 * University, creator Daniel L. Rubin. 
 * 
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this 
 * list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, 
 * this list of conditions and the following disclaimer in the documentation 
 * and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 */
package edu.stanford.hakan.aim3api.usage;
import java.util.UUID;

import java.io.File;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.Calendar;
import javax.xml.XMLConstants;

import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

/**
 *
 * @author Hakan BULU
 */
public class AnnotationValidator {

    private static String validationResult;

    public static String getValidationResult() {
        return validationResult;
    }

    private static void setValidationResult(String valResult) {
        validationResult = valResult;
    }

    public static boolean ValidateXML(String PathXML, String PathXSD) {
        Source source = new StreamSource(PathXML);
        return isValid(source, PathXSD);
    }

    public static boolean ValidateXML(Document doc, String PathXSD) {
        //String tempXmlPath = "temp" + UUID.randomUUID() + ".xml";
        String tempXmlPath = "/tmp/temp" + UUID.randomUUID() + ".xml";
        //String tempXmlPath = "temp" + UUID.randomUUID() + ".xml";
        Source source = documentToSource(doc, tempXmlPath);
        if (source == null) {
            return false;
        }
        try {
            boolean res = isValid(source, PathXSD);
            File tempFile = new File(tempXmlPath);
            tempFile.delete();
            return res;
        } catch (Exception ex) {
            setValidationResult("XML Validation is Unsuccessful: " + ex.getMessage() + "\r\n");
            return false;
        }
    }

    private static boolean isValid(Source source, String PathXSD) {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File(PathXSD));
            Validator validator = schema.newValidator();
            validator.validate(source);
            setValidationResult("XML Validation is Successful." + "\r\n");
            return true;
        } catch (Exception ex) {
            setValidationResult("XML Validation is Unsuccessful: " + ex.getMessage() + "\r\n");
            return false;
        }
    }

    public static boolean ValidateXML(Node node, String PathXSD) {
        try {
            DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            doc.appendChild(node);
            return ValidateXML(doc, PathXSD);
        } catch (Exception ex) {
            setValidationResult("XML Validation is Unsuccessful: " + ex.getMessage() + "\r\n");
            return false;
        }
    }

    private static Source documentToSource(Document doc, String tempXmlPath) {
        try {
            Source source = new DOMSource(doc);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StreamResult result = new StreamResult(new File(tempXmlPath));
            transformer.transform(source, result);
            Source res = new StreamSource(tempXmlPath);
            return res;
        } catch (TransformerException ex) {
            setValidationResult("XML Validation is Unsuccessful: " + ex.getMessage() + "\r\n");
            return null;
        }
    }
}
