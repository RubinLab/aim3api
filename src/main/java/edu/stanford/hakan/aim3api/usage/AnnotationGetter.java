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

import edu.stanford.hakan.aim3api.aimquery.AimQuery;
import edu.stanford.hakan.aim3api.base.AimException;
import edu.stanford.hakan.aim3api.base.ImageAnnotation;
import edu.stanford.hakan.aim3api.utility.GenerateId;
import edu.stanford.hakan.aim3api.utility.Utility;
import java.io.BufferedReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.URLConnection;
import javax.xml.parsers.*;
import org.xml.sax.InputSource;

import java.net.URL;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 *
 * @author Hakan BULU
 */
public class AnnotationGetter {

    private static String validationResult;

    public static String getValidationResult() {
        return validationResult;
    }

    public static void setValidationResult(String valResult) {
        validationResult = valResult;
    }

    private static String getXMLStringFromExist(String Url, String XQuery, String dbUserName, String dbUserPassword) throws AimException {
        try {
            Url = Utility.correctToUrl(Url);
            String requestURL = Url + "rest/";
            String data = "";

            data += "<?xml version='1.0' encoding='UTF-8'?>";
            data += "<query xmlns='http://exist.sourceforge.net/NS/exist' start='1' max='10000'>";
            data += "<text>";
            data += XQuery;
            data += "</text>";
            data += "<properties>";
            data += "<property name='indent' value='yes'/>";
            data += "</properties>";
            data += "</query>";

            URL url = new URL(requestURL);
            URLConnection conn = url.openConnection();

            conn.setRequestProperty("Content-Type", "application/xml");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            if (conn instanceof HttpURLConnection) {
                ((HttpURLConnection) conn).setRequestMethod("POST");
                ((HttpURLConnection) conn).setRequestProperty("Content-Type", "application/xml");
                if (!"".equals(dbUserName.trim()) || !"".equals(dbUserPassword.trim())) {
                    String userPassword = dbUserName + ":" + dbUserPassword;
                    String encoding = new sun.misc.BASE64Encoder().encode(userPassword.getBytes());
                    ((HttpURLConnection) conn).setRequestProperty("Authorization", "Basic " + encoding);
                }
                ((HttpURLConnection) conn).connect();
            }

            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

            //write parameters 
            writer.write(data);
            writer.flush();

            // Get the response 
            StringBuilder answer = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                answer.append(line);
            }
            writer.close();
            reader.close();

            //Output the response 
            return (answer.toString());
        } catch (Exception ex) {
            throw new AimException("AimException: " + ex.getMessage());
        }
    }

    public static String removeImageAnnotationFromServer(String Url, String nameSpace, String collection, String dbUserName, String dbUserPassword, String uniqueIdentifier) throws AimException {
        try {
            if (!AnnotationGetter.isExistInTheServer(Url, nameSpace, collection, dbUserName, dbUserPassword, uniqueIdentifier)) {
                throw new AimException("AimException: The Image Annotation which you want to remove is not exist. Please check your parameters.");
            }

            String requestURL = Utility.correctToUrl(Url) + "rest/" + collection + "/AIM_" + uniqueIdentifier + ".xml";
//            String requestURL = "http://localhost:8080/exist/rest/testCollection/AIM_6da79acc-2817-4f53-92b8-506304a590f4.xml";

            URL url = new URL(requestURL);
            URLConnection conn = url.openConnection();

            conn.setRequestProperty("Content-Type", "application/xml");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            if (conn instanceof HttpURLConnection) {
                ((HttpURLConnection) conn).setRequestMethod("DELETE");
                ((HttpURLConnection) conn).setRequestProperty("Content-Type", "application/xml");
                if (!"".equals(dbUserName.trim()) || !"".equals(dbUserPassword.trim())) {
                    String userPassword = dbUserName + ":" + dbUserPassword;
                    String encoding = new sun.misc.BASE64Encoder().encode(userPassword.getBytes());
                    ((HttpURLConnection) conn).setRequestProperty("Authorization", "Basic " + encoding);
                }
                ((HttpURLConnection) conn).connect();
            }

            // Get the response 
            StringBuilder answer = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                answer.append(line);
            }
            reader.close();

            //Output the response 
            return "XML Saving operation is Successful.";
        } catch (Exception ex) {
            throw new AimException("AimException: " + ex.getMessage());
        }
    }

    public static void deleteImageAnnotationFromServer(String serverURL, String namespace, String collection, String PathXSD, String dbUserName, String dbUserPassword, String uniqueIdentifier) throws AimException {
        try {
            if (!AnnotationGetter.isExistInTheServer(serverURL, namespace, collection, dbUserName, dbUserPassword, uniqueIdentifier)) {
                throw new AimException("AimException: The Image Annotation which you want to delete is not exist. Please check your parameters.");
            }
            ImageAnnotation iAnno = getImageAnnotationFromServerByUniqueIdentifier(serverURL, namespace, collection, dbUserName, dbUserPassword, uniqueIdentifier);
            String aimVersion = iAnno.getAimVersion();
            String groupID = "";
            if (aimVersion.indexOf("^") < 0) {
                //*** which means that, the annotation was created before the versioning mechanism.
                groupID = "G:" + GenerateId.getUUID();
                iAnno.setAimVersion("AIM.3.0^1^Deleted^" + Utility.getNowAtGMT() + "^null^" + groupID, "al536anhb55555");
            } else if (aimVersion.indexOf("Current") < 0) {
                throw new AimException("AimException: You can just delete, current version of the image annotation. Please check your parameters.");
            } else {
                String[] aimVersionFields = iAnno.getAimVersion().split("\\^");
                String versionNumber = aimVersionFields[1];
                String status = aimVersionFields[2];
                String previosUID = aimVersionFields[4];
                groupID = aimVersionFields[5];
                iAnno.setAimVersion("AIM.3.0^" + versionNumber + "^Deleted^" + Utility.getNowAtGMT() + "^" + previosUID + "^" + groupID, "al536anhb55555");
                AnnotationBuilder.saveToServer(iAnno, serverURL, namespace, collection, PathXSD, dbUserName, dbUserPassword);
            }
        } catch (Exception ex) {
            throw new AimException("AimException: " + ex.getMessage());
        }
    }

    private static Document getDocumentFromString(String xml) throws AimException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(new StringReader(xml)));
        } catch (Exception ex) {
            throw new AimException("AimException: " + ex.getMessage());
        }
    }

    private static List<Node> getNodesFromNodeByName(Node node, String nodeName) {
        List<Node> res = new ArrayList<Node>();
        if (node.getNodeName() == null ? nodeName == null : node.getNodeName().equals(nodeName)) {
            res.add(node);
        }
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            res.addAll(getNodesFromNodeByName(nodeList.item(i), nodeName));
        }
        return res;
    }

    private static List<ImageAnnotation> getImageAnnotationListFromDocument(Document doc, String PathXSD) throws AimException {
        List<ImageAnnotation> res = new ArrayList<ImageAnnotation>();
        try {
            Node firstNode = doc.getFirstChild();
            List<Node> listNodeImageAnnotations = getNodesFromNodeByName(firstNode, "ImageAnnotation");
            for (int i = 0; i < listNodeImageAnnotations.size(); i++) {
                ImageAnnotation imageAnnotation = new ImageAnnotation();
                Node nodeImageAnnotation = listNodeImageAnnotations.get(i);
                imageAnnotation.setXMLNode(nodeImageAnnotation);
                if (imageAnnotation.intitalState == null) {
                    imageAnnotation.intitalState = new ImageAnnotation();
                    imageAnnotation.intitalState.setXMLNode(nodeImageAnnotation);
                }
                if (!"".equals(PathXSD.trim())) {
                    //*** Validation Step
                    DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
                    DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
                    Document docValidation = docBuilder.newDocument();
                    Element root = (Element) imageAnnotation.getXMLNode(docValidation);
                    root.setAttribute("xmlns", "gme://caCORE.caCORE/3.2/edu.northwestern.radiology.AIM");
                    root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
                    root.setAttribute("xsi:schemaLocation", "gme://caCORE.caCORE/3.2/edu.northwestern.radiology.AIM AIM_v3_rv11_XML.xsd");
                    root.setAttribute("xmlns:rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
                    docValidation.appendChild(root);
                    boolean valRes = AnnotationValidator.ValidateXML(docValidation, PathXSD);
                    setValidationResult(AnnotationValidator.getValidationResult());
                    if (!valRes) {
                        throw new AimException(getValidationResult());
                    }
                }
                res.add(imageAnnotation);
            }
        } catch (Exception ex) {
            throw new AimException(ex.getMessage());
        }
        return res; //*** hakan
    }

    private static List<ImageAnnotation> getImageAnnotationListFromServer(String Url, String XQuery, String dbUserName, String dbUserPassword, String PathXSD) throws AimException {
        try {
            String serverResponse = getXMLStringFromExist(Url, XQuery, dbUserName, dbUserPassword);
            Document serverDoc = getDocumentFromString(serverResponse);
            List<ImageAnnotation> res = getImageAnnotationListFromDocument(serverDoc, PathXSD);
            return res;
        } catch (AimException ex) {
            throw new AimException("AimException: " + ex.getMessage());
        }
    }

    private static int CountStringOccurrences(String text, String pattern) {
        int count = 0;
        int i = 0;
        while ((i = text.indexOf(pattern, i)) != -1) {
            i += pattern.length();
            count++;
        }
        return count;
    }

    private static void control(String serverURL, String namespace, String collection, String PathXSD) throws AimException {
        serverURL = Utility.correctToUrl(serverURL);
        control(serverURL, namespace, collection);
        if (PathXSD == null || "".equals(PathXSD.trim())) {
            throw new AimException("AimException: PathXSD must be defined");
        }
    }

    private static void control(String serverURL, String namespace, String collection) throws AimException {
        if (collection == null || "".equals(collection.trim())) {
            throw new AimException("AimException: Collection must be defined");
        }
        if (namespace == null || "".equals(namespace.trim())) {
            throw new AimException("AimException: Namespace must be defined");
        }
        if (serverURL == null || "".equals(serverURL.trim())) {
            throw new AimException("AimException: ServerURL must be defined");
        }
    }

    public static ImageAnnotation getImageAnnotationFromFile(String PathXML, String PathXSD) throws AimException {
        try {
            if (!"".equals(PathXSD.trim())) {
                //*** Validation
                boolean valRes = AnnotationValidator.ValidateXML(PathXML, PathXSD);
                setValidationResult(AnnotationValidator.getValidationResult());
                if (!valRes) {
                    throw new AimException(getValidationResult());
                }
            }
            ImageAnnotation res = new ImageAnnotation();
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new File(PathXML));
            doc.getDocumentElement().normalize();
            res.setXMLNode(doc.getDocumentElement());
            if (res.intitalState == null) {
                res.intitalState = new ImageAnnotation();
                res.intitalState.setXMLNode(doc.getDocumentElement());
            }
            return res; //*** hakan
        } catch (SAXException ex) {
            throw new AimException(ex.getMessage());
        } catch (IOException ex) {
            throw new AimException(ex.getMessage());
        } catch (ParserConfigurationException ex) {
            throw new AimException(ex.getMessage());
        }
    }

    public static ImageAnnotation getImageAnnotationFromFile(String PathXML) throws AimException {
        try {
            return getImageAnnotationFromFile(PathXML, "");
        } catch (AimException ex) {
            throw new AimException(ex.getMessage());
        }
    }
    
    public static List<ImageAnnotation> getImageAnnotationsFromString(String text, String PathXSD) throws AimException
    {        
        try {
            Document serverDoc = getDocumentFromString(text);
            List<ImageAnnotation> res = getImageAnnotationListFromDocument(serverDoc, PathXSD);
            return res;
        } catch (AimException ex) {
            throw new AimException("AimException: " + ex.getMessage());
        }
    }

    /**
     * *********************************************
     */
    //*** SORGULAR ***//
    /**
     * *********************************************
     */
    //*** ImageAnnotation.uniqueIdentifier Equal
    public static ImageAnnotation getImageAnnotationFromServerByUniqueIdentifier(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String uniqueIdentifier, String PathXSD) throws AimException {
        serverURL = Utility.correctToUrl(serverURL);
        control(serverURL, namespace, collection);
        if (uniqueIdentifier == null || "".equals(uniqueIdentifier.trim())) {
            throw new AimException("AimException: UniqueIdentifier must be defined");
        }

        String aimQL = "SELECT FROM " + collection + " WHERE ImageAnnotation.uniqueIdentifier = '" + uniqueIdentifier + "'";
        List<ImageAnnotation> listAnno = getImageAnnotationsFromServerWithAimQuery(serverURL, namespace, dbUserName, dbUserPassword, aimQL, PathXSD);
        if (listAnno.size() <= 0) {
            return null;
        }
        return listAnno.get(0);
    }

    //*** ImageAnnotation.uniqueIdentifier Equal
    public static ImageAnnotation getImageAnnotationFromServerByUniqueIdentifier(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String uniqueIdentifier) throws AimException {
        return getImageAnnotationFromServerByUniqueIdentifier(serverURL, namespace, collection, dbUserName, dbUserPassword, uniqueIdentifier, "");
    }

    //*** Person.Name Equal
    public static List<ImageAnnotation> getImageAnnotationsFromServerByPersonNameEqual(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String PersonName, String PathXSD) throws AimException {
        serverURL = Utility.correctToUrl(serverURL);
        control(serverURL, namespace, collection);
        if (PersonName == null || "".equals(PersonName.trim())) {
            throw new AimException("AimException: PersonName must be defined");
        }
        String aimQL = "SELECT FROM " + collection + " WHERE Person.Name = '" + PersonName + "'";
        return getImageAnnotationsFromServerWithAimQuery(serverURL, namespace, dbUserName, dbUserPassword, aimQL, PathXSD);
    }

    //*** Person.Name Equal
    public static List<ImageAnnotation> getImageAnnotationsFromServerByPersonNameEqual(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String PersonName) throws AimException {
        return getImageAnnotationsFromServerByPersonNameEqual(serverURL, namespace, collection, dbUserName, dbUserPassword, PersonName, "");
    }

    //*** Person.Name Contains
    public static List<ImageAnnotation> getImageAnnotationsFromServerByPersonNameContains(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String PersonName, String PathXSD) throws AimException {
        serverURL = Utility.correctToUrl(serverURL);
        control(serverURL, namespace, collection);
        if (PersonName == null || "".equals(PersonName.trim())) {
            throw new AimException("AimException: PersonName must be defined");
        }
        String aimQL = "SELECT FROM " + collection + " WHERE Person.Name LIKE '%" + PersonName + "%'";
        return getImageAnnotationsFromServerWithAimQuery(serverURL, namespace, dbUserName, dbUserPassword, aimQL, PathXSD);
    }

    //*** Person.Name Contains
    public static List<ImageAnnotation> getImageAnnotationsFromServerByPersonNameContains(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String PersonName) throws AimException {
        return getImageAnnotationsFromServerByPersonNameContains(serverURL, namespace, collection, dbUserName, dbUserPassword, PersonName, "");
    }

    //*** Person.Id Equal
    public static List<ImageAnnotation> getImageAnnotationsFromServerByPersonIdEqual(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String PersonId, String PathXSD) throws AimException {
        serverURL = Utility.correctToUrl(serverURL);
        control(serverURL, namespace, collection);
        if (PersonId == null || "".equals(PersonId.trim())) {
            throw new AimException("AimException: PersonId must be defined");
        }
        String aimQL = "SELECT FROM " + collection + " WHERE Person.Id = '" + PersonId + "'";
        return getImageAnnotationsFromServerWithAimQuery(serverURL, namespace, dbUserName, dbUserPassword, aimQL, PathXSD);
    }

    //*** Person.Id Equal
    public static List<ImageAnnotation> getImageAnnotationsFromServerByPersonIdEqual(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String PersonId) throws AimException {
        return getImageAnnotationsFromServerByPersonIdEqual(serverURL, namespace, collection, dbUserName, dbUserPassword, PersonId, "");
    }

    //*** Person.Id Contains
    public static List<ImageAnnotation> getImageAnnotationsFromServerByPersonIdContains(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String PersonId, String PathXSD) throws AimException {
        serverURL = Utility.correctToUrl(serverURL);
        control(serverURL, namespace, collection);
        if (PersonId == null || "".equals(PersonId.trim())) {
            throw new AimException("AimException: PersonId must be defined");
        }
        String aimQL = "SELECT FROM " + collection + " WHERE Person.Id LIKE '%" + PersonId + "%'";
        return getImageAnnotationsFromServerWithAimQuery(serverURL, namespace, dbUserName, dbUserPassword, aimQL, PathXSD);
    }

    //*** Person.Id Contains
    public static List<ImageAnnotation> getImageAnnotationsFromServerByPersonIdContains(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String PersonId) throws AimException {
        return getImageAnnotationsFromServerByPersonIdContains(serverURL, namespace, collection, dbUserName, dbUserPassword, PersonId, "");
    }

    //*** ImageAnnotation.dateTime Equal
    public static List<ImageAnnotation> getImageAnnotationsFromServerByDateTimeEqual(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String dateTime, String PathXSD) throws AimException {
        serverURL = Utility.correctToUrl(serverURL);
        control(serverURL, namespace, collection);
        if (dateTime == null || "".equals(dateTime.trim())) {
            throw new AimException("AimException: DateTime must be defined");
        }

        String aimQL = "SELECT FROM " + collection + " WHERE ImageAnnotation.dateTime = '" + dateTime + "'";
        List<ImageAnnotation> listAnno = getImageAnnotationsFromServerWithAimQuery(serverURL, namespace, dbUserName, dbUserPassword, aimQL, PathXSD);
        return listAnno;
    }

    //*** ImageAnnotation.dateTime Equal
    public static List<ImageAnnotation> getImageAnnotationsFromServerByDateTimeEqual(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String dateTime) throws AimException {
        return getImageAnnotationsFromServerByDateTimeEqual(serverURL, namespace, collection, dbUserName, dbUserPassword, dateTime, "");
    }

    //*** ImageAnnotation.dateTime Contains
    public static List<ImageAnnotation> getImageAnnotationsFromServerByDateTimeContains(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String dateTime, String PathXSD) throws AimException {
        serverURL = Utility.correctToUrl(serverURL);
        control(serverURL, namespace, collection);
        if (dateTime == null || "".equals(dateTime.trim())) {
            throw new AimException("AimException: DateTime must be defined");
        }

        String aimQL = "SELECT FROM " + collection + " WHERE ImageAnnotation.dateTime LIKE '%" + dateTime + "%'";
        List<ImageAnnotation> listAnno = getImageAnnotationsFromServerWithAimQuery(serverURL, namespace, dbUserName, dbUserPassword, aimQL, PathXSD);
        return listAnno;
    }

    //*** ImageAnnotation.dateTime Contains
    public static List<ImageAnnotation> getImageAnnotationsFromServerByDateTimeContains(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String dateTime) throws AimException {
        return getImageAnnotationsFromServerByDateTimeContains(serverURL, namespace, collection, dbUserName, dbUserPassword, dateTime, "");
    }

    //*** ImageAnnotation.cagridId Equal
    public static List<ImageAnnotation> getImageAnnotationsFromServerByCagridIdEqual(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String cagridId, String PathXSD) throws AimException {
        serverURL = Utility.correctToUrl(serverURL);
        control(serverURL, namespace, collection);
        if (cagridId == null || "".equals(cagridId.trim())) {
            throw new AimException("AimException: CagridId must be defined");
        }

        String aimQL = "SELECT FROM " + collection + " WHERE ImageAnnotation.cagridId = '" + cagridId + "'";
        List<ImageAnnotation> listAnno = getImageAnnotationsFromServerWithAimQuery(serverURL, namespace, dbUserName, dbUserPassword, aimQL, PathXSD);
        return listAnno;
    }

    //*** ImageAnnotation.cagridId Equal
    public static List<ImageAnnotation> getImageAnnotationsFromServerByCagridIdEqual(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String cagridId) throws AimException {
        return getImageAnnotationsFromServerByCagridIdEqual(serverURL, namespace, collection, dbUserName, dbUserPassword, cagridId, "");
    }

    //*** ImageAnnotation.cagridId Contains
    public static List<ImageAnnotation> getImageAnnotationsFromServerByCagridIdContains(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String cagridId, String PathXSD) throws AimException {
        serverURL = Utility.correctToUrl(serverURL);
        control(serverURL, namespace, collection);
        if (cagridId == null || "".equals(cagridId.trim())) {
            throw new AimException("AimException: CagridId must be defined");
        }

        String aimQL = "SELECT FROM " + collection + " WHERE ImageAnnotation.cagridId LIKE '%" + cagridId + "%'";
        List<ImageAnnotation> listAnno = getImageAnnotationsFromServerWithAimQuery(serverURL, namespace, dbUserName, dbUserPassword, aimQL, PathXSD);
        return listAnno;
    }

    //*** ImageAnnotation.cagridId Contains
    public static List<ImageAnnotation> getImageAnnotationsFromServerByCagridIdContains(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String cagridId) throws AimException {
        return getImageAnnotationsFromServerByCagridIdContains(serverURL, namespace, collection, dbUserName, dbUserPassword, cagridId, "");
    }

    //*** ImageAnnotation.name Equal
    public static List<ImageAnnotation> getImageAnnotationsFromServerByNameEqual(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String name, String PathXSD) throws AimException {
        serverURL = Utility.correctToUrl(serverURL);
        control(serverURL, namespace, collection);
        if (name == null || "".equals(name.trim())) {
            throw new AimException("AimException: Name must be defined");
        }

        String aimQL = "SELECT FROM " + collection + " WHERE ImageAnnotation.name = '" + name + "'";
        List<ImageAnnotation> listAnno = getImageAnnotationsFromServerWithAimQuery(serverURL, namespace, dbUserName, dbUserPassword, aimQL, PathXSD);
        return listAnno;
    }

    //*** ImageAnnotation.name Equal
    public static List<ImageAnnotation> getImageAnnotationsFromServerByNameEqual(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String name) throws AimException {
        return getImageAnnotationsFromServerByNameEqual(serverURL, namespace, collection, dbUserName, dbUserPassword, name, "");
    }

    //*** ImageAnnotation.name Contains
    public static List<ImageAnnotation> getImageAnnotationsFromServerByNameContains(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String name, String PathXSD) throws AimException {
        serverURL = Utility.correctToUrl(serverURL);
        control(serverURL, namespace, collection);
        if (name == null || "".equals(name.trim())) {
            throw new AimException("AimException: Name must be defined");
        }

        String aimQL = "SELECT FROM " + collection + " WHERE ImageAnnotation.name LIKE '%" + name + "%'";
        List<ImageAnnotation> listAnno = getImageAnnotationsFromServerWithAimQuery(serverURL, namespace, dbUserName, dbUserPassword, aimQL, PathXSD);
        return listAnno;
    }

    //*** ImageAnnotation.name Contains
    public static List<ImageAnnotation> getImageAnnotationsFromServerByNameContains(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String name) throws AimException {
        return getImageAnnotationsFromServerByNameContains(serverURL, namespace, collection, dbUserName, dbUserPassword, name, "");
    }

    //*** ImageAnnotation.codeMeaning Equal
    public static List<ImageAnnotation> getImageAnnotationsFromServerByCodeMeaningEqual(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String codeMeaning, String PathXSD) throws AimException {
        serverURL = Utility.correctToUrl(serverURL);
        control(serverURL, namespace, collection);
        if (codeMeaning == null || "".equals(codeMeaning.trim())) {
            throw new AimException("AimException: CodeMeaning must be defined");
        }

        String aimQL = "SELECT FROM " + collection + " WHERE ImageAnnotation.codeMeaning = '" + codeMeaning + "'";
        List<ImageAnnotation> listAnno = getImageAnnotationsFromServerWithAimQuery(serverURL, namespace, dbUserName, dbUserPassword, aimQL, PathXSD);
        return listAnno;
    }

    //*** ImageAnnotation.codeMeaning Equal
    public static List<ImageAnnotation> getImageAnnotationsFromServerByCodeMeaningEqual(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String codeMeaning) throws AimException {
        return getImageAnnotationsFromServerByCodeMeaningEqual(serverURL, namespace, collection, dbUserName, dbUserPassword, codeMeaning, "");
    }

    //*** ImageAnnotation.codeMeaning Contains
    public static List<ImageAnnotation> getImageAnnotationsFromServerByCodeMeaningContains(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String codeMeaning, String PathXSD) throws AimException {
        serverURL = Utility.correctToUrl(serverURL);
        control(serverURL, namespace, collection);
        if (codeMeaning == null || "".equals(codeMeaning.trim())) {
            throw new AimException("AimException: CodeMeaning must be defined");
        }

        String aimQL = "SELECT FROM " + collection + " WHERE ImageAnnotation.codeMeaning LIKE '%" + codeMeaning + "%'";
        List<ImageAnnotation> listAnno = getImageAnnotationsFromServerWithAimQuery(serverURL, namespace, dbUserName, dbUserPassword, aimQL, PathXSD);
        return listAnno;
    }

    //*** ImageAnnotation.codeMeaning Contains
    public static List<ImageAnnotation> getImageAnnotationsFromServerByCodeMeaningContains(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String codeMeaning) throws AimException {
        return getImageAnnotationsFromServerByCodeMeaningContains(serverURL, namespace, collection, dbUserName, dbUserPassword, codeMeaning, "");
    }

    //*** ImageAnnotation.codeValue Equal
    public static List<ImageAnnotation> getImageAnnotationsFromServerByCodeValueEqual(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String codeValue, String PathXSD) throws AimException {
        serverURL = Utility.correctToUrl(serverURL);
        control(serverURL, namespace, collection);
        if (codeValue == null || "".equals(codeValue.trim())) {
            throw new AimException("AimException: CodeValue must be defined");
        }

        String aimQL = "SELECT FROM " + collection + " WHERE ImageAnnotation.codeValue = '" + codeValue + "'";
        List<ImageAnnotation> listAnno = getImageAnnotationsFromServerWithAimQuery(serverURL, namespace, dbUserName, dbUserPassword, aimQL, PathXSD);
        return listAnno;
    }

    //*** ImageAnnotation.codeValue Equal
    public static List<ImageAnnotation> getImageAnnotationsFromServerByCodeValueEqual(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String codeValue) throws AimException {
        return getImageAnnotationsFromServerByCodeValueEqual(serverURL, namespace, collection, dbUserName, dbUserPassword, codeValue, "");
    }

    //*** ImageAnnotation.codeValue Contains
    public static List<ImageAnnotation> getImageAnnotationsFromServerByCodeValueContains(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String codeValue, String PathXSD) throws AimException {
        serverURL = Utility.correctToUrl(serverURL);
        control(serverURL, namespace, collection);
        if (codeValue == null || "".equals(codeValue.trim())) {
            throw new AimException("AimException: CodeValue must be defined");
        }

        String aimQL = "SELECT FROM " + collection + " WHERE ImageAnnotation.codeValue LIKE '%" + codeValue + "%'";
        List<ImageAnnotation> listAnno = getImageAnnotationsFromServerWithAimQuery(serverURL, namespace, dbUserName, dbUserPassword, aimQL, PathXSD);
        return listAnno;
    }

    //*** ImageAnnotation.codeValue Contains
    public static List<ImageAnnotation> getImageAnnotationsFromServerByCodeValueContains(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String codeValue) throws AimException {
        return getImageAnnotationsFromServerByCodeValueContains(serverURL, namespace, collection, dbUserName, dbUserPassword, codeValue, "");
    }

    //*** ImageAnnotation.codingSchemeDesignator Equal
    public static List<ImageAnnotation> getImageAnnotationsFromServerByCodingSchemeDesignatorEqual(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String codingSchemeDesignator, String PathXSD) throws AimException {
        serverURL = Utility.correctToUrl(serverURL);
        control(serverURL, namespace, collection);
        if (codingSchemeDesignator == null || "".equals(codingSchemeDesignator.trim())) {
            throw new AimException("AimException: CodingSchemeDesignator must be defined");
        }

        String aimQL = "SELECT FROM " + collection + " WHERE ImageAnnotation.codingSchemeDesignator = '" + codingSchemeDesignator + "'";
        List<ImageAnnotation> listAnno = getImageAnnotationsFromServerWithAimQuery(serverURL, namespace, dbUserName, dbUserPassword, aimQL, PathXSD);
        return listAnno;
    }

    //*** ImageAnnotation.codingSchemeDesignator Equal
    public static List<ImageAnnotation> getImageAnnotationsFromServerByCodingSchemeDesignatorEqual(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String codingSchemeDesignator) throws AimException {
        return getImageAnnotationsFromServerByCodingSchemeDesignatorEqual(serverURL, namespace, collection, dbUserName, dbUserPassword, codingSchemeDesignator, "");
    }

    //*** ImageAnnotation.codingSchemeDesignator Contains
    public static List<ImageAnnotation> getImageAnnotationsFromServerByCodingSchemeDesignatorContains(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String codingSchemeDesignator, String PathXSD) throws AimException {
        serverURL = Utility.correctToUrl(serverURL);
        control(serverURL, namespace, collection);
        if (codingSchemeDesignator == null || "".equals(codingSchemeDesignator.trim())) {
            throw new AimException("AimException: CodingSchemeDesignator must be defined");
        }

        String aimQL = "SELECT FROM " + collection + " WHERE ImageAnnotation.codingSchemeDesignator LIKE '%" + codingSchemeDesignator + "%'";
        List<ImageAnnotation> listAnno = getImageAnnotationsFromServerWithAimQuery(serverURL, namespace, dbUserName, dbUserPassword, aimQL, PathXSD);
        return listAnno;
    }

    //*** ImageAnnotation.codingSchemeDesignator Contains
    public static List<ImageAnnotation> getImageAnnotationsFromServerByCodingSchemeDesignatorContains(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String codingSchemeDesignator) throws AimException {
        return getImageAnnotationsFromServerByCodingSchemeDesignatorContains(serverURL, namespace, collection, dbUserName, dbUserPassword, codingSchemeDesignator, "");
    }

    //*** ImageSeries.instanceUID Equal
    public static List<ImageAnnotation> getImageAnnotationsFromServerByImageSeriesInstanceUIDEqual(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String instanceUID, String PathXSD) throws AimException {
        serverURL = Utility.correctToUrl(serverURL);
        control(serverURL, namespace, collection);
        if (instanceUID == null || "".equals(instanceUID.trim())) {
            throw new AimException("AimException: InstanceUID must be defined");
        }

        String aimQL = "SELECT FROM " + collection + " WHERE ImageSeries.instanceUID = '" + instanceUID + "'";
        List<ImageAnnotation> listAnno = getImageAnnotationsFromServerWithAimQuery(serverURL, namespace, dbUserName, dbUserPassword, aimQL, PathXSD);
        return listAnno;
    }

    //*** ImageSeries.instanceUID Equal
    public static List<ImageAnnotation> getImageAnnotationsFromServerByImageSeriesInstanceUIDEqual(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String instanceUID) throws AimException {
        return getImageAnnotationsFromServerByImageSeriesInstanceUIDEqual(serverURL, namespace, collection, dbUserName, dbUserPassword, instanceUID, "");
    }

    //*** ImageSeries.instanceUID Contains
    public static List<ImageAnnotation> getImageAnnotationsFromServerByImageSeriesInstanceUIDContains(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String instanceUID, String PathXSD) throws AimException {
        serverURL = Utility.correctToUrl(serverURL);
        control(serverURL, namespace, collection);
        if (instanceUID == null || "".equals(instanceUID.trim())) {
            throw new AimException("AimException: InstanceUID must be defined");
        }

        String aimQL = "SELECT FROM " + collection + " WHERE ImageSeries.instanceUID LIKE '%" + instanceUID + "%'";
        List<ImageAnnotation> listAnno = getImageAnnotationsFromServerWithAimQuery(serverURL, namespace, dbUserName, dbUserPassword, aimQL, PathXSD);
        return listAnno;
    }

    //*** ImageSeries.instanceUID Contains
    public static List<ImageAnnotation> getImageAnnotationsFromServerByImageSeriesInstanceUIDContains(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String instanceUID) throws AimException {
        return getImageAnnotationsFromServerByImageSeriesInstanceUIDContains(serverURL, namespace, collection, dbUserName, dbUserPassword, instanceUID, "");
    }

    //*** User.name Equal
    public static List<ImageAnnotation> getImageAnnotationsFromServerByUserNameEqual(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String userName, String PathXSD) throws AimException {
        serverURL = Utility.correctToUrl(serverURL);
        control(serverURL, namespace, collection);
        if (userName == null || "".equals(userName.trim())) {
            throw new AimException("AimException: UserName must be defined");
        }

        String aimQL = "SELECT FROM " + collection + " WHERE User.name = '" + userName + "'";
        List<ImageAnnotation> listAnno = getImageAnnotationsFromServerWithAimQuery(serverURL, namespace, dbUserName, dbUserPassword, aimQL, PathXSD);
        return listAnno;
    }

    //*** User.name Equal
    public static List<ImageAnnotation> getImageAnnotationsFromServerByUserNameEqual(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String instanceUID) throws AimException {
        return getImageAnnotationsFromServerByUserNameEqual(serverURL, namespace, collection, dbUserName, dbUserPassword, instanceUID, "");
    }

    //*** User.name Contains
    public static List<ImageAnnotation> getImageAnnotationsFromServerByUserNameContains(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String userName, String PathXSD) throws AimException {
        serverURL = Utility.correctToUrl(serverURL);
        control(serverURL, namespace, collection);
        if (userName == null || "".equals(userName.trim())) {
            throw new AimException("AimException: UserName must be defined");
        }

        String aimQL = "SELECT FROM " + collection + " WHERE User.name LIKE '%" + userName + "%'";
        List<ImageAnnotation> listAnno = getImageAnnotationsFromServerWithAimQuery(serverURL, namespace, dbUserName, dbUserPassword, aimQL, PathXSD);
        return listAnno;
    }

    //*** User.name Contains
    public static List<ImageAnnotation> getImageAnnotationsFromServerByUserNameContains(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String instanceUID) throws AimException {
        return getImageAnnotationsFromServerByUserNameContains(serverURL, namespace, collection, dbUserName, dbUserPassword, instanceUID, "");
    }

    //*** User.cagridId Equal
    public static List<ImageAnnotation> getImageAnnotationsFromServerByUserCagridIdEqual(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String userCagridId, String PathXSD) throws AimException {
        serverURL = Utility.correctToUrl(serverURL);
        control(serverURL, namespace, collection);
        if (userCagridId == null || "".equals(userCagridId.trim())) {
            throw new AimException("AimException: UserCagridId must be defined");
        }

        String aimQL = "SELECT FROM " + collection + " WHERE User.cagridId = '" + userCagridId + "'";
        List<ImageAnnotation> listAnno = getImageAnnotationsFromServerWithAimQuery(serverURL, namespace, dbUserName, dbUserPassword, aimQL, PathXSD);
        return listAnno;
    }

    //*** User.cagridId Equal
    public static List<ImageAnnotation> getImageAnnotationsFromServerByUserCagridIdEqual(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String userCagridId) throws AimException {
        return getImageAnnotationsFromServerByUserCagridIdEqual(serverURL, namespace, collection, dbUserName, dbUserPassword, userCagridId, "");
    }

    //*** User.cagridId Contains
    public static List<ImageAnnotation> getImageAnnotationsFromServerByUserCagridIdContains(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String userCagridId, String PathXSD) throws AimException {
        serverURL = Utility.correctToUrl(serverURL);
        control(serverURL, namespace, collection);
        if (userCagridId == null || "".equals(userCagridId.trim())) {
            throw new AimException("AimException: UserCagridId must be defined");
        }

        String aimQL = "SELECT FROM " + collection + " WHERE User.cagridId LIKE '%" + userCagridId + "%'";
        List<ImageAnnotation> listAnno = getImageAnnotationsFromServerWithAimQuery(serverURL, namespace, dbUserName, dbUserPassword, aimQL, PathXSD);
        return listAnno;
    }

    //*** User.cagridId Contains
    public static List<ImageAnnotation> getImageAnnotationsFromServerByUserCagridIdContains(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String userCagridId) throws AimException {
        return getImageAnnotationsFromServerByUserCagridIdContains(serverURL, namespace, collection, dbUserName, dbUserPassword, userCagridId, "");
    }

    //*** User.loginName Equal
    public static List<ImageAnnotation> getImageAnnotationsFromServerByUserLoginNameEqual(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String userLoginName, String PathXSD) throws AimException {
        serverURL = Utility.correctToUrl(serverURL);
        control(serverURL, namespace, collection);
        if (userLoginName == null || "".equals(userLoginName.trim())) {
            throw new AimException("AimException: UserLoginName must be defined");
        }

        String aimQL = "SELECT FROM " + collection + " WHERE User.loginName = '" + userLoginName + "'";
        List<ImageAnnotation> listAnno = getImageAnnotationsFromServerWithAimQuery(serverURL, namespace, dbUserName, dbUserPassword, aimQL, PathXSD);
        return listAnno;
    }

    //*** User.loginName Equal
    public static List<ImageAnnotation> getImageAnnotationsFromServerByUserLoginNameEqual(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String userLoginName) throws AimException {
        return getImageAnnotationsFromServerByUserLoginNameEqual(serverURL, namespace, collection, dbUserName, dbUserPassword, userLoginName, "");
    }

    //*** User.loginName Contains
    public static List<ImageAnnotation> getImageAnnotationsFromServerByUserLoginNameContains(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String userLoginName, String PathXSD) throws AimException {
        serverURL = Utility.correctToUrl(serverURL);
        control(serverURL, namespace, collection);
        if (userLoginName == null || "".equals(userLoginName.trim())) {
            throw new AimException("AimException: UserLoginName must be defined");
        }

        String aimQL = "SELECT FROM " + collection + " WHERE User.loginName LIKE '%" + userLoginName + "%'";
        List<ImageAnnotation> listAnno = getImageAnnotationsFromServerWithAimQuery(serverURL, namespace, dbUserName, dbUserPassword, aimQL, PathXSD);
        return listAnno;
    }

    //*** User.loginName Contains
    public static List<ImageAnnotation> getImageAnnotationsFromServerByUserLoginNameContains(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String userLoginName) throws AimException {
        return getImageAnnotationsFromServerByUserLoginNameContains(serverURL, namespace, collection, dbUserName, dbUserPassword, userLoginName, "");
    }

    //*** User.numberWithinRoleOfClinicalTrial Equal
    public static List<ImageAnnotation> getImageAnnotationsFromServerByUserNumberWithinRoleOfClinicalTrialEqual(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String userNumberWithinRoleOfClinicalTrial, String PathXSD) throws AimException {
        serverURL = Utility.correctToUrl(serverURL);
        control(serverURL, namespace, collection);
        if (userNumberWithinRoleOfClinicalTrial == null || "".equals(userNumberWithinRoleOfClinicalTrial.trim())) {
            throw new AimException("AimException: UserNumberWithinRoleOfClinicalTrial must be defined");
        }

        String aimQL = "SELECT FROM " + collection + " WHERE User.numberWithinRoleOfClinicalTrial = '" + userNumberWithinRoleOfClinicalTrial + "'";
        List<ImageAnnotation> listAnno = getImageAnnotationsFromServerWithAimQuery(serverURL, namespace, dbUserName, dbUserPassword, aimQL, PathXSD);
        return listAnno;
    }

    //*** User.numberWithinRoleOfClinicalTrial Equal
    public static List<ImageAnnotation> getImageAnnotationsFromServerByUserNumberWithinRoleOfClinicalTrialEqual(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String userNumberWithinRoleOfClinicalTrial) throws AimException {
        return getImageAnnotationsFromServerByUserNumberWithinRoleOfClinicalTrialEqual(serverURL, namespace, collection, dbUserName, dbUserPassword, userNumberWithinRoleOfClinicalTrial, "");
    }

    //*** User.numberWithinRoleOfClinicalTrial Contains
    public static List<ImageAnnotation> getImageAnnotationsFromServerByUserNumberWithinRoleOfClinicalTrialContains(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String userNumberWithinRoleOfClinicalTrial, String PathXSD) throws AimException {
        serverURL = Utility.correctToUrl(serverURL);
        control(serverURL, namespace, collection);
        if (userNumberWithinRoleOfClinicalTrial == null || "".equals(userNumberWithinRoleOfClinicalTrial.trim())) {
            throw new AimException("AimException: UserNumberWithinRoleOfClinicalTrial must be defined");
        }

        String aimQL = "SELECT FROM " + collection + " WHERE User.numberWithinRoleOfClinicalTrial LIKE '%" + userNumberWithinRoleOfClinicalTrial + "%'";
        List<ImageAnnotation> listAnno = getImageAnnotationsFromServerWithAimQuery(serverURL, namespace, dbUserName, dbUserPassword, aimQL, PathXSD);
        return listAnno;
    }

    //*** User.numberWithinRoleOfClinicalTrial Contains
    public static List<ImageAnnotation> getImageAnnotationsFromServerByUserNumberWithinRoleOfClinicalTrialContains(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String userNumberWithinRoleOfClinicalTrial) throws AimException {
        return getImageAnnotationsFromServerByUserNumberWithinRoleOfClinicalTrialContains(serverURL, namespace, collection, dbUserName, dbUserPassword, userNumberWithinRoleOfClinicalTrial, "");
    }

    //*** User.roleInTrial Equal
    public static List<ImageAnnotation> getImageAnnotationsFromServerByUserRoleInTrialEqual(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String userRoleInTrial, String PathXSD) throws AimException {
        serverURL = Utility.correctToUrl(serverURL);
        control(serverURL, namespace, collection);
        if (userRoleInTrial == null || "".equals(userRoleInTrial.trim())) {
            throw new AimException("AimException: UserRoleInTrial must be defined");
        }

        String aimQL = "SELECT FROM " + collection + " WHERE User.roleInTrial = '" + userRoleInTrial + "'";
        List<ImageAnnotation> listAnno = getImageAnnotationsFromServerWithAimQuery(serverURL, namespace, dbUserName, dbUserPassword, aimQL, PathXSD);
        return listAnno;
    }

    //*** User.roleInTrial Equal
    public static List<ImageAnnotation> getImageAnnotationsFromServerByUserRoleInTrialEqual(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String userRoleInTrial) throws AimException {
        return getImageAnnotationsFromServerByUserRoleInTrialEqual(serverURL, namespace, collection, dbUserName, dbUserPassword, userRoleInTrial, "");
    }

    //*** User.roleInTrial Contains
    public static List<ImageAnnotation> getImageAnnotationsFromServerByUserRoleInTrialContains(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String userRoleInTrial, String PathXSD) throws AimException {
        serverURL = Utility.correctToUrl(serverURL);
        control(serverURL, namespace, collection);
        if (userRoleInTrial == null || "".equals(userRoleInTrial.trim())) {
            throw new AimException("AimException: UserRoleInTrial must be defined");
        }

        String aimQL = "SELECT FROM " + collection + " WHERE User.roleInTrial LIKE '%" + userRoleInTrial + "%'";
        List<ImageAnnotation> listAnno = getImageAnnotationsFromServerWithAimQuery(serverURL, namespace, dbUserName, dbUserPassword, aimQL, PathXSD);
        return listAnno;
    }

    //*** User.roleInTrial Contains
    public static List<ImageAnnotation> getImageAnnotationsFromServerByUserRoleInTrialContains(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String userRoleInTrial) throws AimException {
        return getImageAnnotationsFromServerByUserRoleInTrialContains(serverURL, namespace, collection, dbUserName, dbUserPassword, userRoleInTrial, "");
    }

    //*** Person.id AND User.name Equal
    public static List<ImageAnnotation> getImageAnnotationsFromServerByPersonIDAndUserNameEqual(String serverURL, String namespace, String collection, String loginUserName, String password, String personID, String userName, String PathXSD) throws AimException {
        serverURL = Utility.correctToUrl(serverURL);
        control(serverURL, namespace, collection);
        if (personID == null || "".equals(personID.trim()) || userName == null || "".equals(userName.trim())) {
            throw new AimException("AimException: personID and userName must be defined");
        }

        String aimQL = "SELECT FROM " + collection + " WHERE Person.id = '" + personID + "' AND User.name = '" + userName + "'";
        List<ImageAnnotation> listAnno = getImageAnnotationsFromServerWithAimQuery(serverURL, namespace, loginUserName, password, aimQL, PathXSD);
        return listAnno;
    }

    //*** Person.id AND User.name Equal
    public static List<ImageAnnotation> getImageAnnotationsFromServerByPersonIDAndUserNameEqual(String serverURL, String namespace, String collection, String loginUserName, String password, String personID, String userName) throws AimException {
        return getImageAnnotationsFromServerByPersonIDAndUserNameEqual(serverURL, namespace, collection, loginUserName, password, personID, userName, "");
    }

    public static String getXMLFromServerWithAimQuery(String serverURL, String namespace, String aimQuery) throws AimException {
        if (namespace == null || "".equals(namespace.trim())) {
            throw new AimException("AimException: Namespace must be defined");
        }
        if (serverURL == null || "".equals(serverURL.trim())) {
            throw new AimException("AimException: ServerURL must be defined");
        }
        if (aimQuery == null || "".equals(aimQuery.trim())) {
            throw new AimException("AimException: AimQuery must be defined");
        }
        //String XQuery = AimQuery.convertToXQuery(aimQuery, namespace);
        return getXMLFromServerWithAimQuery(serverURL, namespace, aimQuery);// getXMLStrFromServer(serverURL, namespace, XQuery);
    }

    public static List<ImageAnnotation> getImageAnnotationsFromServerWithAimQuery(String serverURL, String namespace, String dbUserName, String dbUserPassword, String aimQuery, String PathXSD) throws AimException {
        /*if (PathXSD == null || "".equals(PathXSD.trim())) {
         throw new AimException("AimException: PathXSD must be defined");
         }*/
        if (namespace == null || "".equals(namespace.trim())) {
            throw new AimException("AimException: Namespace must be defined");
        }
        if (serverURL == null || "".equals(serverURL.trim())) {
            throw new AimException("AimException: ServerURL must be defined");
        }
        if (aimQuery == null || "".equals(aimQuery.trim())) {
            throw new AimException("AimException: AimQuery must be defined");
        }
        String XQuery = AimQuery.convertToXQuery(aimQuery, namespace);
        return getImageAnnotationListFromServer(serverURL, XQuery, dbUserName, dbUserPassword, PathXSD);//  getDocumentFromServer(serverURL, namespace, XQuery);       
    }

    public static boolean isExistInTheServer(String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword, String uniqueIdentifier) throws AimException {
        ImageAnnotation temp = getImageAnnotationFromServerByUniqueIdentifier(serverURL, namespace, collection, dbUserName, dbUserPassword, uniqueIdentifier);
        if (temp != null) {
            return true;
        }
        return false;
    }

    public static List<ImageAnnotation> getAllVersionsOfTheAnnotation(ImageAnnotation Anno, String serverURL, String namespace, String collection, String dbUserName, String dbUserPassword) throws AimException {
        List<ImageAnnotation> res = new ArrayList<ImageAnnotation>();
        serverURL = Utility.correctToUrl(serverURL);
        control(serverURL, namespace, collection);
        String aimVersion = Anno.getAimVersion();
        if (aimVersion.indexOf("^") < 0) {
            res.add(Anno);
            return res;
        }
        String[] aimVersionFields = Anno.getAimVersion().split("\\^");
        String qroupID = aimVersionFields[5];
        String aimQL = "SELECT FROM " + collection + " WHERE ImageAnnotation.aimVersion LIKE '%" + qroupID + "%'";
        res = getImageAnnotationsFromServerWithAimQuery(serverURL, namespace, dbUserName, dbUserPassword, aimQL, "");
        return res;
    }

    // *** count by personName
    public static int getCountImageAnnotationByPersonNameEqual(String serverURL,
            String nameSpace, String collection, String dbUserName, String dbUserPassword,
            String personName) throws AimException {
        String XQuery = "declare default element namespace '" + nameSpace + "'; count(/collection('" + collection + "')/ImageAnnotation/person/Person[lower-case(@name)=lower-case('" + personName + "')])";
        String serverResponse = getXMLStringFromExist(serverURL, XQuery, dbUserName, dbUserPassword);
        Document serverDoc = getDocumentFromString(serverResponse);
        String res = getExistResultValueFromDocument(serverDoc);
        return Integer.parseInt(res);
    }

    // *** count by uniqueIdentifier
    public static int getCountImageAnnotationByUniqueIdentifierEqual(String serverURL,
            String nameSpace, String collection, String dbUserName, String dbUserPassword,
            String uniqueIdentifier) throws AimException {
        String XQuery = "declare default element namespace '" + nameSpace + "'; count(/collection('" + collection + "')/ImageAnnotation[lower-case(@uniqueIdentifier)=lower-case('" + uniqueIdentifier + "')])";
        String serverResponse = getXMLStringFromExist(serverURL, XQuery, dbUserName, dbUserPassword);
        Document serverDoc = getDocumentFromString(serverResponse);
        String res = getExistResultValueFromDocument(serverDoc);
        return Integer.parseInt(res);
    }

    // *** count by userLoginName
    public static int getCountImageAnnotationByUserLoginNameContains(String serverURL,
            String nameSpace, String collection, String dbUserName, String dbUserPassword,
            String loginName) throws AimException {
        String XQuery = "declare default element namespace '" + nameSpace + "'; count(/collection('" + collection + "')/ImageAnnotation/user/User[contains(lower-case(@loginName),lower-case('" + loginName + "'))])";
        String serverResponse = getXMLStringFromExist(serverURL, XQuery, dbUserName, dbUserPassword);
        Document serverDoc = getDocumentFromString(serverResponse);
        String res = getExistResultValueFromDocument(serverDoc);
        return Integer.parseInt(res);
    }    
    
    // *** count by personID and userName
    public static int getCountImageAnnotationByPersonIdAndUserNameEqual(String serverURL,
            String nameSpace, String collection, String dbUserName, String dbUserPassword,
            String personID, String userName) throws AimException {
        String XQuery = "declare default element namespace '" + nameSpace + "'; count(/collection('" + collection + "')/ImageAnnotation/person/Person[lower-case(@id)=lower-case('" + personID + "')] AND /collection('" + collection + "')/ImageAnnotation/user/User[lower-case(@name)=lower-case('" + userName + "')] )";
        String serverResponse = getXMLStringFromExist(serverURL, XQuery, dbUserName, dbUserPassword);
        Document serverDoc = getDocumentFromString(serverResponse);
        String res = getExistResultValueFromDocument(serverDoc);
        return Integer.parseInt(res);
    }    
    
    // *** count by ImageSeries InstanceUid
    public static int getCountImageAnnotationByImageSeriesInstanceUidEqual(String serverURL,
            String nameSpace, String collection, String dbUserName, String dbUserPassword,
            String InstanceUid) throws AimException {
        String XQuery = "declare default element namespace '" + nameSpace + "'; count(/collection('" + collection + "')/ImageAnnotation/imageReferenceCollection/ImageReference/imageStudy/ImageStudy/imageSeries/ImageSeries[lower-case(@instanceUID)=lower-case('" + InstanceUid + "')])";
        String serverResponse = getXMLStringFromExist(serverURL, XQuery, dbUserName, dbUserPassword);
        Document serverDoc = getDocumentFromString(serverResponse);
        String res = getExistResultValueFromDocument(serverDoc);
        return Integer.parseInt(res);
    }

    public static String getExistResultValueFromDocument(Document doc) {
        Node firstNode = doc.getFirstChild();
        List<Node> listResult = getNodesFromNodeByName(firstNode, "exist:result");
        if (listResult.size() > 0) {
            return listResult.get(0).getTextContent().trim();
        }
        return "";
    }

}
