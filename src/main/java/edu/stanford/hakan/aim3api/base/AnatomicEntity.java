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

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

/**
 *
 * @author Hakan BULU
 */
@SuppressWarnings("serial")
public class AnatomicEntity implements IAimXMLOperations {

    private Integer cagridId;
    private String codeValue;
    private String codeMeaning;
    private String codingSchemeDesignator;
    private String codingSchemeVersion;
    private Double annotatorConfidence;
    private Boolean isPresent;
    private String label;
    private AnatomicEntityCharacteristicCollection anatomicEntityCharacteristicCollection;

    public AnatomicEntity() {
        this.anatomicEntityCharacteristicCollection = new AnatomicEntityCharacteristicCollection();
    }

    public AnatomicEntity(Integer cagridId, String codeValue, String codeMeaning, String codingSchemeDesignator, String codingSchemeVersion, Double annotatorConfidence, Boolean isPresent, String label) {
        this.cagridId = cagridId;
        this.codeValue = codeValue;
        this.codeMeaning = codeMeaning;
        this.codingSchemeDesignator = codingSchemeDesignator;
        this.codingSchemeVersion = codingSchemeVersion;
        this.annotatorConfidence = annotatorConfidence;
        this.isPresent = isPresent;
        this.label = label;
        this.anatomicEntityCharacteristicCollection = new AnatomicEntityCharacteristicCollection();
    }

    public Double getAnnotatorConfidence() {
        return annotatorConfidence;
    }

    public void setAnnotatorConfidence(Double annotatorConfidence) {
        this.annotatorConfidence = annotatorConfidence;
    }

    public Integer getCagridId() {
        return cagridId;
    }

    public void setCagridId(Integer cagridId) {
        this.cagridId = cagridId;
    }

    public String getCodeMeaning() {
        return codeMeaning;
    }

    public void setCodeMeaning(String codeMeaning) {
        this.codeMeaning = codeMeaning;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    public String getCodingSchemeDesignator() {
        return codingSchemeDesignator;
    }

    public void setCodingSchemeDesignator(String codingSchemeDesignator) {
        this.codingSchemeDesignator = codingSchemeDesignator;
    }

    public String getCodingSchemeVersion() {
        return codingSchemeVersion;
    }

    public void setCodingSchemeVersion(String codingSchemeVersion) {
        this.codingSchemeVersion = codingSchemeVersion;
    }

    public AnatomicEntityCharacteristicCollection getAnatomicEntityCharacteristicCollection() {
        return anatomicEntityCharacteristicCollection;
    }

    public void setAnatomicEntityCharacteristicCollection(AnatomicEntityCharacteristicCollection anatomicEntityCharacteristicCollection) {
        this.anatomicEntityCharacteristicCollection = anatomicEntityCharacteristicCollection;
    }

    public Boolean getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(Boolean isPresent) {
        this.isPresent = isPresent;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void addAnatomicEntityCharacteristic(AnatomicEntityCharacteristic newAnatomicEntityCharacteristic) {
        this.anatomicEntityCharacteristicCollection.AddAnatomicEntityCharacteristic(newAnatomicEntityCharacteristic);
    }

    @Override
    public Node getXMLNode(Document doc) throws AimException {

        this.Control();

        Element anatomicEntity = doc.createElement("AnatomicEntity");
        anatomicEntity.setAttribute("cagridId", this.cagridId.toString());
        anatomicEntity.setAttribute("codeValue", this.codeValue);
        anatomicEntity.setAttribute("codeMeaning", this.codeMeaning);
        anatomicEntity.setAttribute("codingSchemeDesignator", this.codingSchemeDesignator);
        if (this.codingSchemeVersion != null) {
            anatomicEntity.setAttribute("codingSchemeVersion", this.codingSchemeVersion);
        }
        if (this.annotatorConfidence != null) {
            anatomicEntity.setAttribute("annotatorConfidence", this.annotatorConfidence.toString());
        }
        if (this.isPresent != null) {
            anatomicEntity.setAttribute("isPresent", this.isPresent.toString());
        }
        anatomicEntity.setAttribute("label", this.label);
        if (this.anatomicEntityCharacteristicCollection.getAnatomicEntityCharacteristicList().size() > 0) {
            anatomicEntity.appendChild(this.anatomicEntityCharacteristicCollection.getXMLNode(doc));
        }
        return anatomicEntity;
    }

    @Override
    public void setXMLNode(Node node) {

        NodeList listChils = node.getChildNodes();
        for (int i = 0; i < listChils.getLength(); i++) {
            if ("anatomicEntityCharacteristicCollection".equals(listChils.item(i).getNodeName())) {
                this.anatomicEntityCharacteristicCollection.setXMLNode(listChils.item(i));
            }
        }

        NamedNodeMap map = node.getAttributes();
        this.cagridId = Integer.parseInt(map.getNamedItem("cagridId").getNodeValue());
        this.codeValue = map.getNamedItem("codeValue").getNodeValue();
        this.codeMeaning = map.getNamedItem("codeMeaning").getNodeValue();
        this.codingSchemeDesignator = map.getNamedItem("codingSchemeDesignator").getNodeValue();
        if (map.getNamedItem("codingSchemeVersion") != null) {
            this.codingSchemeVersion = map.getNamedItem("codingSchemeVersion").getNodeValue();
        }
        if (map.getNamedItem("annotatorConfidence") != null) {
            this.annotatorConfidence = Double.parseDouble(map.getNamedItem("annotatorConfidence").getNodeValue());
        }
        if (map.getNamedItem("isPresent") != null) {
            this.isPresent = Boolean.parseBoolean(map.getNamedItem("isPresent").getNodeValue());
        }
        this.label = map.getNamedItem("label").getNodeValue();
    }

    @Override
    public Node getRDFNode(Document doc, String unquieID, String Prefix) throws AimException {
        this.Control();
        Element eAnatomicEntity = doc.createElement(Prefix + "AnatomicEntity");
        eAnatomicEntity.setAttribute("rdf:ID", "AnatomicEntity_".concat(unquieID));

        Element eCagridId = doc.createElement(Prefix + "cagridId");
        eCagridId.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#int");
        eCagridId.setTextContent(this.cagridId.toString());
        eAnatomicEntity.appendChild(eCagridId);

        Element eCodeValue = doc.createElement(Prefix + "codeValue");
        eCodeValue.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        eCodeValue.setTextContent(this.codeValue.toString());
        eAnatomicEntity.appendChild(eCodeValue);

        Element eCodeMeaning = doc.createElement(Prefix + "codeMeaning");
        eCodeMeaning.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        eCodeMeaning.setTextContent(this.codeMeaning.toString());
        eAnatomicEntity.appendChild(eCodeMeaning);

        Element eCodingSchemeDesignator = doc.createElement(Prefix + "codingSchemeDesignator");
        eCodingSchemeDesignator.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        eCodingSchemeDesignator.setTextContent(this.codingSchemeDesignator.toString());
        eAnatomicEntity.appendChild(eCodingSchemeDesignator);

        Element eCodingSchemeVersion = doc.createElement(Prefix + "codingSchemeVersion");
        eCodingSchemeVersion.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        if (this.codingSchemeVersion != null) {
            eCodingSchemeVersion.setTextContent(this.codingSchemeVersion.toString());
            eAnatomicEntity.appendChild(eCodingSchemeVersion);
        }

        Element eAnnotatorConfidence = doc.createElement(Prefix + "annotatorConfidence");
        eAnnotatorConfidence.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#float");
        if (this.annotatorConfidence != null) {
            eAnnotatorConfidence.setTextContent(this.annotatorConfidence.toString());
            eAnatomicEntity.appendChild(eAnnotatorConfidence);
        }

        Element eIsPresent = doc.createElement(Prefix + "isPresent");
        eIsPresent.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#boolean");
        if (this.isPresent != null) {
            eIsPresent.setTextContent(this.isPresent.toString());
            eAnatomicEntity.appendChild(eIsPresent);
        }

        Element eLabel = doc.createElement(Prefix + "label");
        eLabel.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        eLabel.setTextContent(this.label.toString());
        eAnatomicEntity.appendChild(eLabel);

        this.anatomicEntityCharacteristicCollection.appendNodes(doc, unquieID, eAnatomicEntity, Prefix);

        return eAnatomicEntity;
    }

    private void Control() throws AimException {
        if (getCagridId() == null) {
            throw new AimException("AimException: AnatomicEntity's cagridId can not be null");
        }
        if (getCodeValue() == null) {
            throw new AimException("AimException: AnatomicEntity's codeValue can not be null");
        }
        if (getCodeMeaning() == null) {
            throw new AimException("AimException: AnatomicEntity's codeMeaning can not be null");
        }
        if (getCodingSchemeDesignator() == null) {
            throw new AimException("AimException: AnatomicEntity's codingSchemeDesignator can not be null");
        }
        if (getLabel() == null) {
            throw new AimException("AimException: AnatomicEntity's label can not be null");
        }
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final AnatomicEntity other = (AnatomicEntity) obj;
//        if (this.cagridId != other.cagridId && (this.cagridId == null || !this.cagridId.equals(other.cagridId))) {
//            return false;
//        }
//        if ((this.codeValue == null) ? (other.codeValue != null) : !this.codeValue.equals(other.codeValue)) {
//            return false;
//        }
//        if ((this.codeMeaning == null) ? (other.codeMeaning != null) : !this.codeMeaning.equals(other.codeMeaning)) {
//            return false;
//        }
//        if ((this.codingSchemeDesignator == null) ? (other.codingSchemeDesignator != null) : !this.codingSchemeDesignator.equals(other.codingSchemeDesignator)) {
//            return false;
//        }
//        if ((this.codingSchemeVersion == null) ? (other.codingSchemeVersion != null) : !this.codingSchemeVersion.equals(other.codingSchemeVersion)) {
//            return false;
//        }
//        if (this.annotatorConfidence != other.annotatorConfidence && (this.annotatorConfidence == null || !this.annotatorConfidence.equals(other.annotatorConfidence))) {
//            return false;
//        }
//        if (this.isPresent != other.isPresent && (this.isPresent == null || !this.isPresent.equals(other.isPresent))) {
//            return false;
//        }
//        if ((this.label == null) ? (other.label != null) : !this.label.equals(other.label)) {
//            return false;
//        }
//        if (this.anatomicEntityCharacteristicCollection != other.anatomicEntityCharacteristicCollection && (this.anatomicEntityCharacteristicCollection == null || !this.anatomicEntityCharacteristicCollection.isEqualTo(other.anatomicEntityCharacteristicCollection))) {
//            return false;
//        }
//        return true;
//    }

//    @Override
//    public int hashCode() {
//        int hash = 3;
//        return hash;
//    }
    
    public boolean isEqualTo(Object other) {
        AnatomicEntity oth = (AnatomicEntity) other;
        if (this.cagridId != oth.cagridId) {
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
        if (this.annotatorConfidence == null ? oth.annotatorConfidence != null : !this.annotatorConfidence.equals(oth.annotatorConfidence)) {
            return false;
        }
        if (this.isPresent == null ? oth.isPresent != null : !this.isPresent.equals(oth.isPresent)) {
            return false;
        }
        if (this.label == null ? oth.label != null : !this.label.equals(oth.label)) {
            return false;
        }
        return this.anatomicEntityCharacteristicCollection.isEqualTo(oth.anatomicEntityCharacteristicCollection);
    }
    
}
