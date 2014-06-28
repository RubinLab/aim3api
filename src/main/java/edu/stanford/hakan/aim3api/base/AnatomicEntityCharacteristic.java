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

import edu.stanford.hakan.aim3api.utility.Converter;
import edu.stanford.hakan.aim4api.base.CD;
import edu.stanford.hakan.aim4api.base.ImagingPhysicalEntityCharacteristic;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Hakan BULU
 */
@SuppressWarnings("serial")
public class AnatomicEntityCharacteristic implements IAimXMLOperations {

    private Integer cagridId;
    private String codeValue;
    private String codeMeaning;
    private String codingSchemeDesignator;
    private String codingSchemeVersion;
    private Double annotatorConfidence;
    private String label;
    private CharacteristicQuantificationCollection characteristicQuantificationCollection = new CharacteristicQuantificationCollection();

    public AnatomicEntityCharacteristic() {
    }

    public AnatomicEntityCharacteristic(Integer cagridId, String codeValue, String codeMeaning, String codingSchemeDesignator, String codingSchemeVersion, Double annotatorConfidence, String label) {
        this.cagridId = cagridId;
        this.codeValue = codeValue;
        this.codeMeaning = codeMeaning;
        this.codingSchemeDesignator = codingSchemeDesignator;
        this.codingSchemeVersion = codingSchemeVersion;
        this.annotatorConfidence = annotatorConfidence;
        this.label = label;
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

    public CharacteristicQuantificationCollection getCharacteristicQuantificationCollection() {
        return characteristicQuantificationCollection;
    }

    public void setCharacteristicQuantificationCollection(CharacteristicQuantificationCollection characteristicQuantificationCollection) {
        this.characteristicQuantificationCollection = characteristicQuantificationCollection;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void addCharacteristicQuantification(CharacteristicQuantification newCharacteristicQuantification) {
        this.characteristicQuantificationCollection.AddCharacteristicQuantification(newCharacteristicQuantification);
    }

    @Override
    public Node getXMLNode(Document doc) throws AimException {
        this.Control();
        Element anatomicEntityCharacteristic = doc.createElement("AnatomicEntityCharacteristic");
        anatomicEntityCharacteristic.setAttribute("cagridId", this.cagridId.toString());
        anatomicEntityCharacteristic.setAttribute("codeValue", this.codeValue);
        anatomicEntityCharacteristic.setAttribute("codeMeaning", this.codeMeaning);
        anatomicEntityCharacteristic.setAttribute("codingSchemeDesignator", this.codingSchemeDesignator);
        if (this.codingSchemeVersion != null) {
            anatomicEntityCharacteristic.setAttribute("codingSchemeVersion", this.codingSchemeVersion);
        }
        if (this.annotatorConfidence != null) {
            anatomicEntityCharacteristic.setAttribute("annotatorConfidence", this.annotatorConfidence.toString());
        }
        anatomicEntityCharacteristic.setAttribute("label", this.label);
        if (this.characteristicQuantificationCollection.getCharacteristicQuantificationList().size() > 0) {
            anatomicEntityCharacteristic.appendChild(this.characteristicQuantificationCollection.getXMLNode(doc));
        }
        return anatomicEntityCharacteristic;
    }

    @Override
    public void setXMLNode(Node node) {

        NodeList listChils = node.getChildNodes();
        for (int i = 0; i < listChils.getLength(); i++) {
            if ("characteristicQuantificationCollection".equals(listChils.item(i).getNodeName())) {
                this.characteristicQuantificationCollection.setXMLNode(listChils.item(i));
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
        this.label = map.getNamedItem("label").getNodeValue();

    }

    @Override
    public Node getRDFNode(Document doc, String unquieID, String Prefix) throws AimException {
        this.Control();
        Element eAnatomicEntityCharacteristic = doc.createElement(Prefix + "AnatomicEntityCharacteristic");
        eAnatomicEntityCharacteristic.setAttribute("rdf:ID", "AnatomicEntityCharacteristic_".concat(unquieID));

        Element eCagridId = doc.createElement(Prefix + "cagridId");
        eCagridId.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#int");
        eCagridId.setTextContent(this.cagridId.toString());
        eAnatomicEntityCharacteristic.appendChild(eCagridId);

        Element eCodeValue = doc.createElement(Prefix + "codeValue");
        eCodeValue.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        eCodeValue.setTextContent(this.codeValue.toString());
        eAnatomicEntityCharacteristic.appendChild(eCodeValue);

        Element eCodeMeaning = doc.createElement(Prefix + "codeMeaning");
        eCodeMeaning.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        eCodeMeaning.setTextContent(this.codeMeaning.toString());
        eAnatomicEntityCharacteristic.appendChild(eCodeMeaning);

        Element eCodingSchemeDesignator = doc.createElement(Prefix + "codingSchemeDesignator");
        eCodingSchemeDesignator.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        eCodingSchemeDesignator.setTextContent(this.codingSchemeDesignator.toString());
        eAnatomicEntityCharacteristic.appendChild(eCodingSchemeDesignator);

        Element eCodingSchemeVersion = doc.createElement(Prefix + "codingSchemeVersion");
        eCodingSchemeVersion.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        if (this.codingSchemeVersion != null) {
            eCodingSchemeVersion.setTextContent(this.codingSchemeVersion.toString());
            eAnatomicEntityCharacteristic.appendChild(eCodingSchemeVersion);
        }

        Element eAnnotatorConfidence = doc.createElement(Prefix + "annotatorConfidence");
        eAnnotatorConfidence.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#float");
        if (this.annotatorConfidence != null) {
            eAnnotatorConfidence.setTextContent(this.annotatorConfidence.toString());
            eAnatomicEntityCharacteristic.appendChild(eAnnotatorConfidence);
        }

        Element eLabel = doc.createElement(Prefix + "label");
        eLabel.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        eLabel.setTextContent(this.label.toString());
        eAnatomicEntityCharacteristic.appendChild(eLabel);

        this.characteristicQuantificationCollection.appendNodes(doc, unquieID, eAnatomicEntityCharacteristic, Prefix);

        return eAnatomicEntityCharacteristic;

    }

    private void Control() throws AimException {

        if (getCagridId() == null) {
            throw new AimException("AimException: AnatomicEntityCharacteristic's cagridId can not be null");
        }
        if (getCodeValue() == null) {
            throw new AimException("AimException: AnatomicEntityCharacteristic's codeValue can not be null");
        }
        if (getCodeMeaning() == null) {
            throw new AimException("AimException: AnatomicEntityCharacteristic's codeMeaning can not be null");
        }
        if (getCodingSchemeDesignator() == null) {
            throw new AimException("AimException: AnatomicEntityCharacteristic's codingSchemeDesignator can not be null");
        }
        if (getLabel() == null) {
            throw new AimException("AimException: AnatomicEntityCharacteristic's label can not be null");
        }
    }

    public boolean isEqualTo(Object other) {
        AnatomicEntityCharacteristic oth = (AnatomicEntityCharacteristic) other;
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
        if (this.label == null ? oth.label != null : !this.label.equals(oth.label)) {
            return false;
        }
        return this.characteristicQuantificationCollection.isEqualTo(oth.characteristicQuantificationCollection);
    }

    public edu.stanford.hakan.aim4api.base.ImagingPhysicalEntityCharacteristic toAimV4() {
        edu.stanford.hakan.aim4api.base.ImagingPhysicalEntityCharacteristic res = new edu.stanford.hakan.aim4api.base.ImagingPhysicalEntityCharacteristic();
        CD typeCode = new CD();
        typeCode.setCode(this.getCodeValue());
        typeCode.setCodeSystem(this.getCodeMeaning());
        typeCode.setCodeSystemName(this.getCodingSchemeDesignator());
        typeCode.setCodeSystemVersion(this.getCodingSchemeVersion());
        res.addTypeCode(typeCode);
        res.setAnnotatorConfidence(this.getAnnotatorConfidence());
        res.setLabel(Converter.toST(this.getLabel()));
        res.setCharacteristicQuantificationCollection(this.getCharacteristicQuantificationCollection().toAimV4());
        return res;
    }

    AnatomicEntityCharacteristic(ImagingPhysicalEntityCharacteristic v4) {
         this.setCagridId(0);
        if (v4.getListTypeCode().size() > 0) {
            CD typeCode = v4.getListTypeCode().get(0);
            if (typeCode.getCode() != null) {
                this.setCodeValue(typeCode.getCode());
            }
            if (typeCode.getCodeSystem() != null) {
                this.setCodeMeaning(typeCode.getCodeSystem());
            }
            if (typeCode.getCodeSystemName() != null) {
                this.setCodingSchemeDesignator(typeCode.getCodeSystemName());
            }
            if (typeCode.getCodeSystemVersion() != null) {
                this.setCodingSchemeVersion(typeCode.getCodeSystemVersion());
            }
        }

        this.setAnnotatorConfidence(v4.getAnnotatorConfidence());
        if (v4.getLabel() != null) {
            this.setLabel(v4.getLabel().getValue());
        }
        if (v4.getCharacteristicQuantificationCollection().getCharacteristicQuantificationList().size() > 0) {
            this.setCharacteristicQuantificationCollection(new CharacteristicQuantificationCollection(v4.getCharacteristicQuantificationCollection()));
        }
    }
}
