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
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

/**
 *
 * @author Hakan BULU
 */
public class NonQuantifiable extends CharacteristicQuantification implements IAimXMLOperations {

    private String codeValue;
    private String codeMeaning;
    private String codingSchemeDesignator;
    private String codingSchemeVersion;

    public NonQuantifiable() {
        setXsiType("NonQuantifiable");
    }

    public NonQuantifiable(Integer cagridId, String name, Double annotatorConfidence, String codeValue, String codeMeaning, String codingSchemeDesignator, String codingSchemeVersion) {
        setCagridId(cagridId);
        setName(name);
        setAnnotatorConfidence(annotatorConfidence);
        this.codeValue = codeValue;
        this.codeMeaning = codeMeaning;
        this.codingSchemeDesignator = codingSchemeDesignator;
        this.codingSchemeVersion = codingSchemeVersion;
        setXsiType("NonQuantifiable");
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

    @Override
    public Node getXMLNode(Document doc) throws AimException {

        this.Control();

        Element characteristicQuantification = (Element) super.getXMLNode(doc);

        characteristicQuantification.setAttribute("codeValue", this.getCodeValue());
        characteristicQuantification.setAttribute("codeMeaning", this.getCodeMeaning());
        characteristicQuantification.setAttribute("codingSchemeDesignator", this.getCodingSchemeDesignator());
        if (this.getCodingSchemeVersion() != null) {
            characteristicQuantification.setAttribute("codingSchemeVersion", this.getCodingSchemeVersion());
        }

        return characteristicQuantification;
    }

    @Override
    public void setXMLNode(Node node) {
        super.setXMLNode(node);

        NamedNodeMap map = node.getAttributes();
        this.codeValue = map.getNamedItem("codeValue").getNodeValue();
        this.codeMeaning = map.getNamedItem("codeMeaning").getNodeValue();
        this.codingSchemeDesignator = map.getNamedItem("codingSchemeDesignator").getNodeValue();
        if (map.getNamedItem("codingSchemeVersion") != null) {
            this.codingSchemeVersion = map.getNamedItem("codingSchemeVersion").getNodeValue();
        }
    }

    @Override
    public Node getRDFNode(Document doc, String unquieID, String Prefix) throws AimException {

        this.Control();

        Element eNonQuantifiable = (Element) super.getRDFNode(doc, unquieID, Prefix);

        Element eCodeValue = doc.createElement(Prefix + "codeValue");
        eCodeValue.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        eCodeValue.setTextContent(this.codeValue.toString());
        eNonQuantifiable.appendChild(eCodeValue);

        Element eCodeMeaning = doc.createElement(Prefix + "codeMeaning");
        eCodeMeaning.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        eCodeMeaning.setTextContent(this.codeMeaning.toString());
        eNonQuantifiable.appendChild(eCodeMeaning);

        Element eCodingSchemeDesignator = doc.createElement(Prefix + "codingSchemeDesignator");
        eCodingSchemeDesignator.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        eCodingSchemeDesignator.setTextContent(this.codingSchemeDesignator.toString());
        eNonQuantifiable.appendChild(eCodingSchemeDesignator);

        Element eCodingSchemeVersion = doc.createElement(Prefix + "codingSchemeVersion");
        eCodingSchemeVersion.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        if (this.codingSchemeVersion != null) {
            eCodingSchemeVersion.setTextContent(this.codingSchemeVersion.toString());
            eNonQuantifiable.appendChild(eCodingSchemeVersion);
        }

        return eNonQuantifiable;
    }

    private void Control() throws AimException {

        if (this.getCodeValue() == null) {
            throw new AimException("AimException: NonQuantifiable's codeValue can not be null");
        }
        if (this.getCodeMeaning() == null) {
            throw new AimException("AimException: NonQuantifiable's codeMeaning can not be null");
        }
        if (this.getCodingSchemeDesignator() == null) {
            throw new AimException("AimException: NonQuantifiable's codingSchemeDesignator can not be null");
        }
    }
    
    @Override
    public boolean isEqualTo(Object other) {
        NonQuantifiable oth = (NonQuantifiable) other;
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
        return super.isEqualTo(other);
    }
}
