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
package edu.stanford.hakan.aim3api.base;

import edu.stanford.hakan.aim3api.usage.AnnotationConverter;
import edu.stanford.hakan.aim3api.utility.GenerateId;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 *
 * @author Hakan BULU
 */
@SuppressWarnings("serial")
public class Annotation implements IAimXMLOperations, IAnnotation {

    private CalculationCollection calculationCollection = new CalculationCollection();
    private InferenceCollection inferenceCollection = new InferenceCollection();
    private AnatomicEntityCollection anatomicEntityCollection = new AnatomicEntityCollection();
    private ImagingObservationCollection imagingObservationCollection = new ImagingObservationCollection();
    private List<User> listUser = new ArrayList<User>();
    private List<Equipment> listEquipment = new ArrayList<Equipment>();
    private List<AimStatus> listAimStatus = new ArrayList<AimStatus>();
    private Integer cagridId;
    private String aimVersion;
    private String comment;
    private String dateTime;
    private String name;
    private String uniqueIdentifier;
    private String codeValue;
    private String codeMeaning;
    private String codingSchemeDesignator;
    private String codingSchemeVersion;
    private String precedentReferencedAnnotationUID;
    private String xsiType;
    private String OntologyPrefix;

    public Annotation intitalState = null;
    private String accessKey = "al536anhb55555";

    public Annotation() {
        this.uniqueIdentifier = GenerateId.getUUID();
        this.aimVersion = "AIM.3.0";
    }

    public Annotation(Integer cagridId, String comment, String dateTime, String name, String codeValue, String codeMeaning, String codingSchemeDesignator, String codingSchemeVersion, String precedentReferencedAnnotationUID) {
        this.cagridId = cagridId;
        this.aimVersion = "AIM.3.0";
        this.comment = comment;
        this.dateTime = dateTime;
        this.name = name;
        this.uniqueIdentifier = GenerateId.getUUID();
        this.codeValue = codeValue;
        this.codeMeaning = codeMeaning;
        this.codingSchemeDesignator = codingSchemeDesignator;
        this.codingSchemeVersion = codingSchemeVersion;
        this.precedentReferencedAnnotationUID = precedentReferencedAnnotationUID;
    }

    @Override
    public void addCalculation(Calculation newCalculation) {
        this.calculationCollection.AddCalculation(newCalculation);
    }

    @Override
    public void addInference(Inference newInference) {
        this.inferenceCollection.AddInference(newInference);
    }

    @Override
    public void addUser(User newUser) {
        this.listUser.add(newUser);
    }

    @Override
    public void addEquipment(Equipment newEquipment) {
        this.listEquipment.add(newEquipment);
    }

    @Override
    public void addAnatomicEntity(AnatomicEntity newAnatomicEntity) {
        this.anatomicEntityCollection.AddAnatomicEntity(newAnatomicEntity);
    }

    @Override
    public void addImagingObservation(ImagingObservation newImagingObservation) {
        this.imagingObservationCollection.AddImagingObservation(newImagingObservation);
    }

    @Override
    public void addAimStatus(AimStatus newAimStatus) {
        this.listAimStatus.add(newAimStatus);
    }

    @Override
    public String getAimVersion() {
        return aimVersion;
    }

    @Override
    public void setAimVersion(String aimVersion, String accessKey) {
        if (!accessKey.equals(this.accessKey)) {
            return;
        }
        this.aimVersion = aimVersion;
    }

    @Override
    public AnatomicEntityCollection getAnatomicEntityCollection() {
        return anatomicEntityCollection;
    }

    @Override
    public void setAnatomicEntityCollection(AnatomicEntityCollection anatomicEntityCollection) {
        this.anatomicEntityCollection = anatomicEntityCollection;
    }

    @Override
    public Integer getCagridId() {
        return cagridId;
    }

    @Override
    public void setCagridId(Integer cagridId) {
        this.cagridId = cagridId;
    }

    @Override
    public CalculationCollection getCalculationCollection() {
        return calculationCollection;
    }

    @Override
    public void setCalculationCollection(CalculationCollection calculationCollection) {
        this.calculationCollection = calculationCollection;
    }

    @Override
    public String getCodeMeaning() {
        return codeMeaning;
    }

    @Override
    public void setCodeMeaning(String codeMeaning) {
        this.codeMeaning = codeMeaning;
    }

    @Override
    public String getCodeValue() {
        return codeValue;
    }

    @Override
    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    @Override
    public String getCodingSchemeDesignator() {
        return codingSchemeDesignator;
    }

    @Override
    public void setCodingSchemeDesignator(String codingSchemeDesignator) {
        this.codingSchemeDesignator = codingSchemeDesignator;
    }

    @Override
    public String getCodingSchemeVersion() {
        return codingSchemeVersion;
    }

    @Override
    public void setCodingSchemeVersion(String codingSchemeVersion) {
        this.codingSchemeVersion = codingSchemeVersion;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String getDateTime() {
        return dateTime;
    }

    @Override
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public ImagingObservationCollection getImagingObservationCollection() {
        return imagingObservationCollection;
    }

    @Override
    public void setImagingObservationCollection(ImagingObservationCollection imagingObservationCollection) {
        this.imagingObservationCollection = imagingObservationCollection;
    }

    @Override
    public InferenceCollection getInferenceCollection() {
        return inferenceCollection;
    }

    @Override
    public void setInferenceCollection(InferenceCollection inferenceCollection) {
        this.inferenceCollection = inferenceCollection;
    }

    @Override
    public List<AimStatus> getListAimStatus() {
        return listAimStatus;
    }

    @Override
    public void setListAimStatus(List<AimStatus> listAimStatus) {
        this.listAimStatus = listAimStatus;
    }

    @Override
    public List<Equipment> getListEquipment() {
        return listEquipment;
    }

    @Override
    public void setListEquipment(List<Equipment> listEquipment) {
        this.listEquipment = listEquipment;
    }

    @Override
    public List<User> getListUser() {
        return listUser;
    }

    @Override
    public void setListUser(List<User> listUser) {
        this.listUser = listUser;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPrecedentReferencedAnnotationUID() {
        return precedentReferencedAnnotationUID;
    }

    @Override
    public void setPrecedentReferencedAnnotationUID(String precedentReferencedAnnotationUID) {
        this.precedentReferencedAnnotationUID = precedentReferencedAnnotationUID;
    }

    @Override
    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    @Override
    public void setUniqueIdentifier(String uniqueIdentifier, String accessKey) {
        if (!accessKey.equals(this.accessKey)) {
            return;
        }
        this.uniqueIdentifier = uniqueIdentifier;
    }

    public void refreshUniqueIdentifier() {
        this.uniqueIdentifier = GenerateId.getUUID();
    }

    @Override
    public Node getXMLNode(Document doc) throws AimException {

        this.Control();

        Element annotation = doc.createElement(this.xsiType);
        annotation.setAttribute("cagridId", this.cagridId.toString());
        annotation.setAttribute("aimVersion", this.aimVersion);
        if (this.comment != null) {
            annotation.setAttribute("comment", this.comment);
        }
        annotation.setAttribute("dateTime", this.dateTime);
        annotation.setAttribute("name", this.name);
        annotation.setAttribute("uniqueIdentifier", this.uniqueIdentifier);
        annotation.setAttribute("codeValue", this.codeValue);
        annotation.setAttribute("codeMeaning", this.codeMeaning);
        annotation.setAttribute("codingSchemeDesignator", this.codingSchemeDesignator);
        if (this.codingSchemeVersion != null) {
            annotation.setAttribute("codingSchemeVersion", this.codingSchemeVersion);
        }
        if (this.precedentReferencedAnnotationUID != null) {
            annotation.setAttribute("precedentReferencedAnnotationUID", this.precedentReferencedAnnotationUID);
        }

        if (this.calculationCollection.getCalculationList().size() > 0) {
            annotation.appendChild(this.calculationCollection.getXMLNode(doc));
        }
        if (this.inferenceCollection.getInferenceList().size() > 0) {
            annotation.appendChild(this.inferenceCollection.getXMLNode(doc));
        }
        Element user = doc.createElement("user");
        for (int i = 0; i < this.listUser.size(); i++) {
            user.appendChild(this.listUser.get(i).getXMLNode(doc));
        }
        if (this.listUser.size() > 0) {
            annotation.appendChild(user);
        }
        Element equipment = doc.createElement("equipment");
        for (int i = 0; i < this.listEquipment.size(); i++) {
            equipment.appendChild(this.listEquipment.get(i).getXMLNode(doc));
        }
        if (this.listEquipment.size() > 0) {
            annotation.appendChild(equipment);
        }
        if (this.anatomicEntityCollection.getAnatomicEntityList().size() > 0) {
            annotation.appendChild(this.anatomicEntityCollection.getXMLNode(doc));
        }
        if (this.imagingObservationCollection.getImagingObservationList().size() > 0) {
            annotation.appendChild(this.imagingObservationCollection.getXMLNode(doc));
        }
        Element aimStatus = doc.createElement("aimStatus");
        for (int i = 0; i < this.listAimStatus.size(); i++) {
            aimStatus.appendChild(this.listAimStatus.get(i).getXMLNode(doc));
        }
        if (this.listAimStatus.size() > 0) {
            annotation.appendChild(aimStatus);
        }

        return annotation;

    }

    @Override
    public void setXMLNode(Node node) {

        NamedNodeMap map = node.getAttributes();
        this.aimVersion = map.getNamedItem("aimVersion").getNodeValue();

        if ("TCGA".equals(this.aimVersion) || "2.0".equals(this.aimVersion) || "1.0".equals(this.aimVersion)) {
            node = AnnotationConverter.annotationTCGA2V3(node);
            map = node.getAttributes();
            this.aimVersion = "AIM.3.0";
        }

        this.cagridId = Integer.parseInt(map.getNamedItem("cagridId").getNodeValue());
        this.dateTime = map.getNamedItem("dateTime").getNodeValue();
        this.name = map.getNamedItem("name").getNodeValue();
        this.uniqueIdentifier = map.getNamedItem("uniqueIdentifier").getNodeValue();
        this.codeValue = map.getNamedItem("codeValue").getNodeValue();
        this.codeMeaning = map.getNamedItem("codeMeaning").getNodeValue();
        this.codingSchemeDesignator = map.getNamedItem("codingSchemeDesignator").getNodeValue();

        if (map.getNamedItem("comment") != null) {
            this.comment = map.getNamedItem("comment").getNodeValue();
        }
        if (map.getNamedItem("codingSchemeVersion") != null) {
            this.codingSchemeVersion = map.getNamedItem("codingSchemeVersion").getNodeValue();
        }
        if (map.getNamedItem("precedentReferencedAnnotationUID") != null) {
            this.precedentReferencedAnnotationUID = map.getNamedItem("precedentReferencedAnnotationUID").getNodeValue();
        }

        this.listAimStatus.clear();
        this.listEquipment.clear();
        this.listUser.clear();
        NodeList listChils = node.getChildNodes();
        for (int i = 0; i < listChils.getLength(); i++) {
            if ("calculationCollection".equals(listChils.item(i).getNodeName())) {
                this.calculationCollection.setXMLNode(listChils.item(i));
            } else if ("inferenceCollection".equals(listChils.item(i).getNodeName())) {
                this.inferenceCollection.setXMLNode(listChils.item(i));
            } else if ("anatomicEntityCollection".equals(listChils.item(i).getNodeName())) {
                this.anatomicEntityCollection.setXMLNode(listChils.item(i));
            } else if ("imagingObservationCollection".equals(listChils.item(i).getNodeName())) {
                this.imagingObservationCollection.setXMLNode(listChils.item(i));
            } else if ("user".equals(listChils.item(i).getNodeName())) {
                NodeList tempList = listChils.item(i).getChildNodes();
                for (int j = 0; j < tempList.getLength(); j++) {
                    if ("User".equals(tempList.item(j).getNodeName())) {
                        User obj = new User();
                        obj.setXMLNode(tempList.item(j));
                        this.addUser(obj);
                    }
                }
            } else if ("equipment".equals(listChils.item(i).getNodeName())) {
                NodeList tempList = listChils.item(i).getChildNodes();
                for (int j = 0; j < tempList.getLength(); j++) {
                    if ("Equipment".equals(tempList.item(j).getNodeName())) {
                        Equipment obj = new Equipment();
                        obj.setXMLNode(tempList.item(j));
                        this.addEquipment(obj);
                    }
                }
            } else if ("aimStatus".equals(listChils.item(i).getNodeName())) {
                NodeList tempList = listChils.item(i).getChildNodes();
                for (int j = 0; j < tempList.getLength(); j++) {
                    if ("AimStatus".equals(tempList.item(j).getNodeName())) {
                        AimStatus obj = new AimStatus();
                        obj.setXMLNode(tempList.item(j));
                        this.addAimStatus(obj);
                    }
                }
            }
        }
    }

    @Override
    public Node getRDFNode(Document doc, String unquieID, String Prefix) throws AimException {
        this.Control();

        Element eAnnotation = doc.createElement(Prefix + this.xsiType);
        eAnnotation.setAttribute("rdf:ID", this.xsiType + "_".concat(unquieID));

        Element eCagridId = doc.createElement(Prefix + "cagridId");
        eCagridId.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#int");
        eCagridId.setTextContent(this.cagridId.toString());
        eAnnotation.appendChild(eCagridId);

        Element eAimVersion = doc.createElement(Prefix + "aimVersion");
        eAimVersion.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        eAimVersion.setTextContent(this.aimVersion.toString());
        eAnnotation.appendChild(eAimVersion);

        Element eComment = doc.createElement(Prefix + "comment");
        eComment.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        if (this.comment != null) {
            eComment.setTextContent(this.comment.toString());
            eAnnotation.appendChild(eComment);
        }

        Element eDateTime = doc.createElement(Prefix + "dateTime");
        eDateTime.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#date");
        eDateTime.setTextContent(this.dateTime.toString());
        eAnnotation.appendChild(eDateTime);

        Element eName = doc.createElement(Prefix + "name");
        eName.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        eName.setTextContent(this.name.toString());
        eAnnotation.appendChild(eName);

        Element eUniqueIdentifier = doc.createElement(Prefix + "uniqueIdentifier");
        eUniqueIdentifier.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        eUniqueIdentifier.setTextContent(this.uniqueIdentifier.toString());
        eAnnotation.appendChild(eUniqueIdentifier);

        Element eCodeValue = doc.createElement(Prefix + "codeValue");
        eCodeValue.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        eCodeValue.setTextContent(this.codeValue.toString());
        eAnnotation.appendChild(eCodeValue);

        Element eCodeMeaning = doc.createElement(Prefix + "codeMeaning");
        eCodeMeaning.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        eCodeMeaning.setTextContent(this.codeMeaning.toString());
        eAnnotation.appendChild(eCodeMeaning);

        Element eCodingSchemeDesignator = doc.createElement(Prefix + "codingSchemeDesignator");
        eCodingSchemeDesignator.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        eCodingSchemeDesignator.setTextContent(this.codingSchemeDesignator.toString());
        eAnnotation.appendChild(eCodingSchemeDesignator);

        Element eCodingSchemeVersion = doc.createElement(Prefix + "codingSchemeVersion");
        eCodingSchemeVersion.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        if (this.codingSchemeVersion != null) {
            eCodingSchemeVersion.setTextContent(this.codingSchemeVersion.toString());
            eAnnotation.appendChild(eCodingSchemeVersion);
        }

        Element ePrecedentReferencedAnnotationUID = doc.createElement(Prefix + "precedentReferencedAnnotationUID");
        ePrecedentReferencedAnnotationUID.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        if (this.precedentReferencedAnnotationUID != null) {
            ePrecedentReferencedAnnotationUID.setTextContent(this.precedentReferencedAnnotationUID.toString());
            eAnnotation.appendChild(ePrecedentReferencedAnnotationUID);
        }

        this.calculationCollection.appendNodes(doc, unquieID, eAnnotation, Prefix);
        this.inferenceCollection.appendNodes(doc, unquieID, eAnnotation, Prefix);
        this.anatomicEntityCollection.appendNodes(doc, unquieID, eAnnotation, Prefix);
        this.imagingObservationCollection.appendNodes(doc, unquieID, eAnnotation, Prefix);

        for (int i = 0; i < listUser.size(); i++) {
            Element eHas = doc.createElement(Prefix + "hasUser");
            eHas.appendChild(listUser.get(i).getRDFNode(doc, unquieID.concat("_").concat(Integer.toString(i + 1)), Prefix));
            eAnnotation.appendChild(eHas);
        }

        for (int i = 0; i < listAimStatus.size(); i++) {
            Element eHas = doc.createElement(Prefix + "hasAimStatus");
            eHas.appendChild(listAimStatus.get(i).getRDFNode(doc, unquieID.concat("_").concat(Integer.toString(i + 1)), Prefix));
            eAnnotation.appendChild(eHas);
        }

        for (int i = 0; i < listEquipment.size(); i++) {
            Element eHas = doc.createElement(Prefix + "hasEquipment");
            eHas.appendChild(listEquipment.get(i).getRDFNode(doc, unquieID.concat("_").concat(Integer.toString(i + 1)), Prefix));
            eAnnotation.appendChild(eHas);
        }

        return eAnnotation;
    }

    private void Control() throws AimException {
        if (getCagridId() == null) {
            throw new AimException("AimException: Annotation's cagridId can not be null");
        }
        if (getAimVersion() == null) {
            throw new AimException("AimException: Annotation's aimVersion can not be null");
        }
        if (getDateTime() == null) {
            throw new AimException("AimException: Annotation's dateTime can not be null");
        }
        if (getName() == null) {
            throw new AimException("AimException: Annotation's name can not be null");
        }
        if (getUniqueIdentifier() == null) {
            throw new AimException("AimException: Annotation's uniqueIdentifier can not be null");
        }
        if (getCodeValue() == null) {
            throw new AimException("AimException: Annotation's codeValue can not be null");
        }
        if (getCodeMeaning() == null) {
            throw new AimException("AimException: Annotation's codeMeaning can not be null");
        }
        if (getCodingSchemeDesignator() == null) {
            throw new AimException("AimException: Annotation's codingSchemeDesignator can not be null");
        }
    }

    @Override
    public void setOntologyPrefix(String Prefix) {
        this.OntologyPrefix = Prefix;
    }

    @Override
    public String getOntologyPrefix() {
        return this.OntologyPrefix;
    }

    protected void setXsiType(String xsiType) {
        this.xsiType = xsiType;
    }

    public String getXsiType() {
        return xsiType;
    }

    public boolean isEqualTo(Object other) {
        Annotation oth = (Annotation) other;
        if (this.cagridId != oth.cagridId) {
            return false;
        }
//        if (this.aimVersion == null ? oth.aimVersion != null : !this.aimVersion.equals(oth.aimVersion)) {
//            return false;
//        }
        if (this.comment == null ? oth.comment != null : !this.comment.equals(oth.comment)) {
            return false;
        }
        if (this.dateTime == null ? oth.dateTime != null : !this.dateTime.equals(oth.dateTime)) {
            return false;
        }
        if (this.name == null ? oth.name != null : !this.name.equals(oth.name)) {
            return false;
        }
        if (this.uniqueIdentifier == null ? oth.uniqueIdentifier != null : !this.uniqueIdentifier.equals(oth.uniqueIdentifier)) {
            return false;
        }
        if (this.codeValue == null ? oth.codeValue != null : !this.codeValue.equals(oth.codeValue)) {
            return false;
        }
        if (this.codeMeaning == null ? oth.codeMeaning != null : !this.codeMeaning.equals(oth.codeMeaning)) {
            return false;
        }
        if (this.codingSchemeDesignator == null ? oth.codingSchemeDesignator != null : !this.codingSchemeDesignator.equals(oth.codingSchemeDesignator)) {
            return false;
        }
        if (this.codingSchemeVersion == null ? oth.codingSchemeVersion != null : !this.codingSchemeVersion.equals(oth.codingSchemeVersion)) {
            return false;
        }
        if (this.precedentReferencedAnnotationUID == null ? oth.precedentReferencedAnnotationUID != null : !this.precedentReferencedAnnotationUID.equals(oth.precedentReferencedAnnotationUID)) {
            return false;
        }
        if (this.listAimStatus.size() != oth.listAimStatus.size()) {
            return false;
        }
        for (int i = 0; i < this.listAimStatus.size(); i++) {
            if (!this.listAimStatus.get(i).isEqualTo(oth.listAimStatus.get(i))) {
                return false;
            }
        }
        if (this.listEquipment.size() != oth.listEquipment.size()) {
            return false;
        }
        for (int i = 0; i < this.listEquipment.size(); i++) {
            if (!this.listEquipment.get(i).isEqualTo(oth.listEquipment.get(i))) {
                return false;
            }
        }
        if (this.listUser.size() != oth.listUser.size()) {
            return false;
        }
        for (int i = 0; i < this.listUser.size(); i++) {
            if (!this.listUser.get(i).isEqualTo(oth.listUser.get(i))) {
                return false;
            }
        }
        if (!this.calculationCollection.isEqualTo(oth.calculationCollection)) {
            return false;
        }
        if (!this.inferenceCollection.isEqualTo(oth.inferenceCollection)) {
            return false;
        }
        if (!this.anatomicEntityCollection.isEqualTo(oth.anatomicEntityCollection)) {
            return false;
        }
        if (!this.imagingObservationCollection.isEqualTo(oth.imagingObservationCollection)) {
            return false;
        }
        return true;
    }

    public boolean isEdited() {
        return !this.isEqualTo(this.intitalState);
    }
}
