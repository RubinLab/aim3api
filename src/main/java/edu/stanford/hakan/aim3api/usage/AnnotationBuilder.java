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

import edu.stanford.hakan.aim3api.base.AimException;
import edu.stanford.hakan.aim3api.base.Annotation;
import edu.stanford.hakan.aim3api.utility.GenerateId;
import edu.stanford.hakan.aim3api.utility.Utility;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
//import org.xml.sax.SAXException;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.Node;




//import org.apache.commons.httpclient.methods.PostMethod;


import java.io.File;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Locale;

/**
 *
 * @author Hakan BULU
 */
public class AnnotationBuilder {

    //private static String validationResult;
    private static String aimXMLsaveResult = "";

    public static String getAimXMLsaveResult() {
        return aimXMLsaveResult.trim();
    }

    private static void setAimXMLsaveResult(String str) {
        aimXMLsaveResult = aimXMLsaveResult + str.trim() + "\r\n";
    }

    private static void clearAimXMLsaveResult() {
        aimXMLsaveResult = "";
    }

    public static void saveToFile(Annotation Anno, String PathXML, String PathXSD) throws AimException {
        try {
            clearAimXMLsaveResult();
            Document doc = createDocument();
            Element root = (Element) Anno.getXMLNode(doc);
            root.setAttribute("xmlns", "gme://caCORE.caCORE/3.2/edu.northwestern.radiology.AIM");
            root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            root.setAttribute("xsi:schemaLocation", "gme://caCORE.caCORE/3.2/edu.northwestern.radiology.AIM AIM_v3_rv11_XML.xsd");
            root.setAttribute("xmlns:rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
            doc.appendChild(root);
            //*** Validation doc
            boolean valRes = AnnotationValidator.ValidateXML(doc, PathXSD);
            setAimXMLsaveResult(AnnotationValidator.getValidationResult());
            //*** Validation End
            if (valRes) {
                SaveDocucument(doc, PathXML);
            } else {
                setAimXMLsaveResult("XML Saving operation is Unsuccessful (Method Name; saveToFile): " + getAimXMLsaveResult());
                throw new AimException("XML Saving operation is Unsuccessful (Method Name; saveToFile): " + getAimXMLsaveResult());
            }
        } catch (Exception ex) {
            setAimXMLsaveResult("XML Saving operation is Unsuccessful (Method Name; saveToFile): " + ex.getMessage());
            throw new AimException("XML Saving operation is Unsuccessful (Method Name; saveToFile): " + ex.getMessage());
        }
    }

    public static String convertToString(Annotation Anno) throws AimException {
        try {
            Document doc = createDocument();
            Element root = (Element) Anno.getXMLNode(doc);
            root.setAttribute("xmlns", "gme://caCORE.caCORE/3.2/edu.northwestern.radiology.AIM");
            root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            root.setAttribute("xsi:schemaLocation", "gme://caCORE.caCORE/3.2/edu.northwestern.radiology.AIM AIM_v3_rv11_XML.xsd");
            root.setAttribute("xmlns:rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
            doc.appendChild(root);

            //set up a transformer 
            TransformerFactory transfac = TransformerFactory.newInstance();
            Transformer trans = transfac.newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");

            //create string from xml tree 
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(doc);
            trans.transform(source, result);
            String xmlString = sw.toString();
            return xmlString;
        } catch (Exception ex) {
            setAimXMLsaveResult("XML Convertion operation is Unsuccessful (Method Name; convertToString): " + ex.getMessage());
            throw new AimException("XML Convertion operation is Unsuccessful (Method Name; convertToString): " + ex.getMessage());
        }
    }

    public static void saveToRDF(Annotation Anno, String Path) throws AimException {
        try {
            Document doc = createDocument();
            Element root = (Element) Anno.getRDFNode(doc, Anno.getUniqueIdentifier(), Anno.getOntologyPrefix());// doc.createElement("ImageAnnotation");
            root.setAttribute("xmlns:xsp", "http://www.owl-ontologies.com/2005/08/07/xsp.owl#");
            root.setAttribute("xmlns:swrlb", "http://www.w3.org/2003/11/swrlb#");
            root.setAttribute("xmlns", "http://www.owl-ontologies.com/Ontology1311106921.owl#");
            root.setAttribute("xmlns:swrl", "http://www.w3.org/2003/11/swrl#");
            root.setAttribute("xmlns:protege", "http://protege.stanford.edu/plugins/owl/protege#");
            root.setAttribute("xmlns:rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
            root.setAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema#");
            root.setAttribute("xmlns:rdfs", "http://www.w3.org/2000/01/rdf-schema#");
            root.setAttribute("xmlns:owl", "http://www.w3.org/2002/07/owl#");
            root.setAttribute("xmlns:base", "http://www.owl-ontologies.com/Ontology1311106921.owl");
            doc.appendChild(root);
            setAimXMLsaveResult("XML Saving operation is Successful.");
            SaveDocucument(doc, Path);
        } catch (Exception ex) {
            setAimXMLsaveResult("XML Saving operation is Unsuccessful (Method Name; saveToRDF): " + ex.getMessage());
            throw new AimException("XML Saving operation is Unsuccessful (Method Name; saveToRDF): " + ex.getMessage());
        }
    }

    public static void saveToOwl(Annotation Anno, String owlFilePath) throws AimException {
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new File(owlFilePath));
            doc.getDocumentElement().normalize();
            doc.getDocumentElement().appendChild(Anno.getRDFNode(doc, Anno.getUniqueIdentifier(), Anno.getOntologyPrefix()));
            setAimXMLsaveResult("XML Saving operation is Successful.");
            SaveDocucument(doc, owlFilePath);
        } catch (Exception ex) {
            setAimXMLsaveResult("XML Saving operation is Unsuccessful (Method Name; saveToOwl): " + ex.getMessage());
            throw new AimException("XML Saving operation is Unsuccessful (Method Name; saveToOwl): " + ex.getMessage());
        }
    }

    public static void removeFromOwl(Annotation Anno, String owlFilePath) throws AimException {
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new File(owlFilePath));
            doc.getDocumentElement().normalize();
            Element rootOwl = doc.getDocumentElement();

            NodeList listAnnotations = rootOwl.getElementsByTagName(Anno.getXsiType());
            for (int i = 0; i < listAnnotations.getLength(); i++) {

                Node nodeAnnotation = listAnnotations.item(i);
                NamedNodeMap map = nodeAnnotation.getAttributes();
                String rdfID = map.getNamedItem("rdf:ID").getNodeValue();
                String thisRdfID = Anno.getXsiType() + "_".concat(Anno.getUniqueIdentifier());
                if (rdfID.equals(thisRdfID)) {
                    rootOwl.removeChild(nodeAnnotation);
                    break;
                }
            }
            setAimXMLsaveResult("XML Saving operation is Successful.");
            SaveDocucument(doc, owlFilePath);
        } catch (Exception ex) {
            setAimXMLsaveResult("XML Saving operation is Unsuccessful (Method Name; removeFromOwl): " + ex.getMessage());
            throw new AimException("XML Saving operation is Unsuccessful (Method Name; removeFromOwl): " + ex.getMessage());
        }
    }

    public static void saveToServer(Annotation Anno, String serverUrl, String nameSpace, String collection, String PathXSD, String dbUserName, String dbUserPassword) throws AimException {
        
            String operation = "Saving";
            try {
            //*** here we need to manage versioning.
            String uniqueIdentifier = "";
            String previosUniqueIdentifier = "null";
            String groupID = "";
            String currentVersionNumber = "";
            String nextVersionNumber = "";
        
            if (Anno.intitalState == null) {
                //*** the annotation is just created
                groupID = "G:" + GenerateId.getUUID();
                Anno.setAimVersion("AIM.3.0^1^Current^" + Utility.getNowAtGMT() + "^null^" + groupID, "al536anhb55555");
            } else if (Anno.getAimVersion().indexOf("Deleted") >= 0) {
                //*** which means that, this is a delete operation
                operation = "Delete";
            } else if (Anno.isEdited()) {
                Annotation initialState = Anno.intitalState;
                String aimVersion = initialState.getAimVersion();
                uniqueIdentifier = GenerateId.getUUID();
                if (aimVersion.indexOf("^") < 0) {
                    //*** which means that, the annotation was created before the versioning mechanism.
                    //*** following 2 variables is neccessary for me to connect initial state with current state
                    groupID = "G:" + GenerateId.getUUID();
                    currentVersionNumber = "1";
                    nextVersionNumber = "2";
                } else {
                    String[] aimVersionFields = initialState.getAimVersion().split("\\^");
                    groupID = aimVersionFields[5];
                    currentVersionNumber = aimVersionFields[1];
                    nextVersionNumber = Integer.toString(Integer.parseInt(currentVersionNumber) + 1);
                    previosUniqueIdentifier = aimVersionFields[4];
                }
                //*** id and aimVersion of the initial state are updated and it is saved to the server.                 
                initialState.setUniqueIdentifier(uniqueIdentifier, "al536anhb55555");
                initialState.setAimVersion("AIM.3.0^" + currentVersionNumber + "^Edited^" + Utility.getNowAtGMT() + "^" + previosUniqueIdentifier + "^" + groupID, "al536anhb55555");
                performUploadExist(initialState, serverUrl, collection, "AIM_" + initialState.getUniqueIdentifier() + ".xml", PathXSD, dbUserName, dbUserPassword);

                previosUniqueIdentifier = initialState.getUniqueIdentifier();
                //*** updating aimVersion of current annotation
                Anno.setAimVersion("AIM.3.0^" + nextVersionNumber + "^Current^" + Utility.getNowAtGMT() + "^" + previosUniqueIdentifier + "^" + groupID, "al536anhb55555");
            }

            performUploadExist(Anno, serverUrl, collection, "AIM_" + Anno.getUniqueIdentifier() + ".xml", PathXSD, dbUserName, dbUserPassword);
            if (AnnotationGetter.isExistInTheServer(serverUrl, nameSpace, collection, dbUserName, dbUserPassword, Anno.getUniqueIdentifier())) {
                setAimXMLsaveResult("XML " + operation + " operation is Successful.");
            } else {
                setAimXMLsaveResult("XML " + operation + " operation is Unsuccessful (Method Name; saveToServer)");
                throw new AimException("XML " + operation + " operation is Unsuccessful (Method Name; saveToServer)");
            }
        } catch (Exception ex) {
            setAimXMLsaveResult("XML " + operation + " operation is Unsuccessful (Method Name; saveToServer): " + ex.getMessage());
            throw new AimException("XML " + operation + " operation is Unsuccessful (Method Name; saveToServer): " + ex.getMessage());
        }
    }

//    public static void saveToServer(Annotation Anno, String serverUrl, String nameSpace, String collection, String PathXSD) throws AimException {
//        try {
//            performUploadExist(Anno, serverUrl, collection, "AIM_" + Anno.getUniqueIdentifier() + ".xml", PathXSD, "", "");
//            if (AnnotationGetter.isExistInTheServer(serverUrl, nameSpace, collection, "", "", Anno.getUniqueIdentifier())) {
//                setAimXMLsaveResult("XML Saving operation is Successful.");
//            } else {
//                setAimXMLsaveResult("XML Saving operation is Unsuccessful (Method Name; saveToServer)");
//                throw new AimException("XML Saving operation is Unsuccessful (Method Name; saveToServer): ");
//            }
//        } catch (Exception ex) {
//            setAimXMLsaveResult("XML Saving operation is Unsuccessful (Method Name; saveToServer): " + ex.getMessage());
//            throw new AimException("XML Saving operation is Unsuccessful (Method Name; saveToServer): " + ex.getMessage());
//        }
//    }
    private static void SaveDocucument(Document doc, String Path) throws AimException {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(Path));
            transformer.transform(source, result);
            setAimXMLsaveResult("XML Saving operation is Successful.");
        } catch (Exception ex) {
            setAimXMLsaveResult("XML Saving operation is Unsuccessful (Method Name; SaveDocucument): " + ex.getMessage());
            throw new AimException("XML Saving operation is Unsuccessful (Method Name; SaveDocucument): " + ex.getMessage());
        }
    }

    public static void saveNode(Node node, String Path) throws AimException {
        try {
            Document doc = createDocument();
            Node nodeCopy = doc.importNode(node, true);
            doc.appendChild(nodeCopy);
            SaveDocucument(doc, Path);
        } catch (Exception ex) {
            setAimXMLsaveResult(ex.getMessage());
            setAimXMLsaveResult("XML Saving operation is Unsuccessful (Method Name; saveNode): " + getAimXMLsaveResult());
            throw new AimException("XML Saving operation is Unsuccessful (Method Name; saveNode): " + getAimXMLsaveResult());
        }
    }

    private static Document createDocument() {
        try {
            DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            return doc;
        } catch (Exception ex) {
            return null;
        }
    }

    private static void performUploadExist(Annotation Anno, String Url, String Collection, String FileName, String PathXSD, String userName, String password) throws AimException {
        try {
            Url = correctToUrl(Url);
            URL url = new URL(Url + "rest/db/" + Collection + "/" + FileName);
            URLConnection conn = url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);

            if (conn instanceof HttpURLConnection) {
                ((HttpURLConnection) conn).setRequestMethod("PUT");
                ((HttpURLConnection) conn).setRequestProperty("Content-Type", "application/xml");
                if (!"".equals(userName.trim()) || !"".equals(password.trim())) {
                    String userPassword = userName + ":" + password;
                    String encoding = new sun.misc.BASE64Encoder().encode(userPassword.getBytes());
                    ((HttpURLConnection) conn).setRequestProperty("Authorization", "Basic " + encoding);
                }
                ((HttpURLConnection) conn).connect();
            }

            BufferedOutputStream bos = new BufferedOutputStream(conn.getOutputStream());

            Document doc = createDocument();
            Element root = (Element) Anno.getXMLNode(doc);
            root.setAttribute("xmlns", "gme://caCORE.caCORE/3.2/edu.northwestern.radiology.AIM");
            root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            root.setAttribute("xsi:schemaLocation", "gme://caCORE.caCORE/3.2/edu.northwestern.radiology.AIM AIM_v3_rv11_XML.xsd");
            root.setAttribute("xmlns:rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
            doc.appendChild(root);
            boolean valRes = AnnotationValidator.ValidateXML(doc, PathXSD);
            setAimXMLsaveResult(AnnotationValidator.getValidationResult());
            if (!valRes) {
                throw new AimException(AnnotationValidator.getValidationResult());
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Source xmlSource = new DOMSource(doc);
            Result outputTarget = new StreamResult(outputStream);
            TransformerFactory.newInstance().newTransformer().transform(xmlSource, outputTarget);
            InputStream is = new ByteArrayInputStream(outputStream.toByteArray());

            BufferedInputStream bis = new BufferedInputStream(is);

            int i;
            while ((i = bis.read()) >= 0) {
                bos.write(i);
            }

            bos.flush();
            bos.close();
            bis.close();

            ((HttpURLConnection) conn).getResponseCode();
            ((HttpURLConnection) conn).disconnect();

        } catch (Exception ex) {
            setAimXMLsaveResult(ex.getMessage());
            setAimXMLsaveResult("XML Saving operation is Unsuccessful (Method Name; performUploadExist): " + getAimXMLsaveResult());
            throw new AimException("XML Saving operation is Unsuccessful (Method Name; performUploadExist): " + getAimXMLsaveResult());
        }
    }

//    private static void performUploadExist(String xmlFilePath, String Url, String Collection, String FileName, String userName, String password) throws AimException {
//        try {
//            Url = correctToUrl(Url);
//            URL url = new URL(Url + "rest/db/" + Collection + "/" + FileName);
//            //URL url = new URL(Url + "/rest/db/" + Collection + "/" + FileName);
//            File file = new File(xmlFilePath);
//            URLConnection conn = url.openConnection();
//            conn.setDoInput(true);
//            conn.setDoOutput(true);
//
//            if (conn instanceof HttpURLConnection) {
//                ((HttpURLConnection) conn).setRequestMethod("PUT");
//                ((HttpURLConnection) conn).setRequestProperty("Content-Type", "application/xml");
//                ((HttpURLConnection) conn).setRequestProperty("Content-Length", Long.toString(file.length()));
//                if (!"".equals(userName.trim()) || !"".equals(password.trim())) {
//                    String userPassword = userName + ":" + password;
//                    String encoding = new sun.misc.BASE64Encoder().encode(userPassword.getBytes());
//                    ((HttpURLConnection) conn).setRequestProperty("Authorization", "Basic " + encoding);
//                }
//                ((HttpURLConnection) conn).connect();
//            }
//
//            BufferedOutputStream bos = new BufferedOutputStream(conn.getOutputStream());
//            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
//
//            int i;
//            while ((i = bis.read()) >= 0) {
//                bos.write(i);
//            }
//
//            bos.flush();
//            bos.close();
//            bis.close();
//
////        InputStream inputStream;
//       /*int responseCode = */ ((HttpURLConnection) conn).getResponseCode();
////        if ((responseCode >= 200) && (responseCode <= 202)) {
////            inputStream = ((HttpURLConnection) conn).getInputStream();
////            int j;
////            while ((j = inputStream.read()) > 0) {
////                System.out.println(j);
////            }
////
////        } else {
////            inputStream = ((HttpURLConnection) conn).getErrorStream();
////        }
//            ((HttpURLConnection) conn).disconnect();
//
//        } catch (Exception ex) {
//            setAimXMLsaveResult(ex.getMessage());
//            setAimXMLsaveResult("XML Saving operation is Unsuccessful (Method Name; performUploadExist): " + getAimXMLsaveResult());
//            throw new AimException("XML Saving operation is Unsuccessful (Method Name; performUploadExist): " + getAimXMLsaveResult());
//        }
//    }
    private static String correctToUrl(String Url) {
        String[] tempArray = Url.split("/");
        String res = "";
        for (int i = 0; i < tempArray.length; i++) {
            if (!"".equals(tempArray[i].trim()) && !"http:".equals(tempArray[i].trim().toLowerCase(new Locale("\\u0131")))) {
                res += tempArray[i] + "/";
            }
        }
        return "http://" + res;
    }

//    private static void performUploadExist(Annotation Anno, String Url, String Collection, String FileName, String PathXSD) throws AimException {
//        performUploadExist(Anno, Url, Collection, FileName, PathXSD, "", "");
//    }
//    private static void performUploadExist(String xmlFilePath, String Url, String Collection, String FileName) throws AimException {
//        performUploadExist(xmlFilePath, Url, Collection, FileName, "", "");
//
//    }
    //*** Version Managing
    private static String getAimVersionValue(boolean isNew) {
        String GroupID;
        if (isNew) {
            GroupID = "G:" + GenerateId.getUUID();
            return "AIM.3.0^1^Current^" + Utility.getNowAtGMT() + "^null^" + GroupID;
        }
        return "";
    }
}
