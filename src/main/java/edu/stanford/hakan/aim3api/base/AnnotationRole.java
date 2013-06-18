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

/**
 *
 * @author Hakan BULU
 */
public class AnnotationRole implements IAimXMLOperations {

    private Integer cagridId;
    private String codeValue;
    private String codeMeaning;
    private String codingSchemeDesignator;
    private String codingSchemeVersion;
    private Integer roleSequenceNumber;

    public AnnotationRole() {
    }

    public AnnotationRole(Integer cagridId, String codeValue, String codeMeaning, String codingSchemeDesignator, String codingSchemeVersion, Integer roleSequenceNumber) {
        this.cagridId = cagridId;
        this.codeValue = codeValue;
        this.codeMeaning = codeMeaning;
        this.codingSchemeDesignator = codingSchemeDesignator;
        this.codingSchemeVersion = codingSchemeVersion;
        this.roleSequenceNumber = roleSequenceNumber;
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

    public Integer getRoleSequenceNumber() {
        return roleSequenceNumber;
    }

    public void setRoleSequenceNumber(Integer roleSequenceNumber) {
        this.roleSequenceNumber = roleSequenceNumber;
    }

    @Override
    public Node getXMLNode(Document doc) throws AimException {

        this.Control();

        Element annotationRole = doc.createElement("AnnotationRole");
        annotationRole.setAttribute("cagridId", Integer.toString(this.cagridId));
        annotationRole.setAttribute("codeValue", this.codeValue);
        annotationRole.setAttribute("codeMeaning", this.codeMeaning);
        annotationRole.setAttribute("codingSchemeDesignator", this.codingSchemeDesignator);
        if (this.codingSchemeVersion != null) {
            annotationRole.setAttribute("codingSchemeVersion", this.codingSchemeVersion);
        }
        annotationRole.setAttribute("roleSequenceNumber", Integer.toString(this.roleSequenceNumber));
        return annotationRole;
    }

    @Override
    public void setXMLNode(Node node) {

        NamedNodeMap map = node.getAttributes();
        this.cagridId = Integer.parseInt(map.getNamedItem("cagridId").getNodeValue());
        this.codeValue = map.getNamedItem("codeValue").getNodeValue();
        this.codeMeaning = map.getNamedItem("codeMeaning").getNodeValue();
        this.codingSchemeDesignator = map.getNamedItem("codingSchemeDesignator").getNodeValue();
        if (map.getNamedItem("codingSchemeVersion") != null) {
            this.codingSchemeVersion = map.getNamedItem("codingSchemeVersion").getNodeValue();
        }
        this.roleSequenceNumber = Integer.parseInt(map.getNamedItem("roleSequenceNumber").getNodeValue());
    }

    @Override
    public Node getRDFNode(Document doc, String unquieID, String Prefix) throws AimException {
        this.Control();
        Element eAnnotationRole = doc.createElement(Prefix + "AnnotationRole");
        eAnnotationRole.setAttribute("rdf:ID", "AnnotationRole_".concat(unquieID));

        Element eCagridId = doc.createElement(Prefix + "cagridId");
        eCagridId.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#int");
        eCagridId.setTextContent(this.cagridId.toString());
        eAnnotationRole.appendChild(eCagridId);

        Element eCodeValue = doc.createElement(Prefix + "codeValue");
        eCodeValue.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        eCodeValue.setTextContent(this.codeValue.toString());
        eAnnotationRole.appendChild(eCodeValue);

        Element eCodeMeaning = doc.createElement(Prefix + "codeMeaning");
        eCodeMeaning.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        eCodeMeaning.setTextContent(this.codeMeaning.toString());
        eAnnotationRole.appendChild(eCodeMeaning);

        Element eCodingSchemeDesignator = doc.createElement(Prefix + "codingSchemeDesignator");
        eCodingSchemeDesignator.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        eCodingSchemeDesignator.setTextContent(this.codingSchemeDesignator.toString());
        eAnnotationRole.appendChild(eCodingSchemeDesignator);

        Element eCodingSchemeVersion = doc.createElement(Prefix + "codingSchemeVersion");
        eCodingSchemeVersion.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        if (this.codingSchemeVersion != null) {
            eCodingSchemeVersion.setTextContent(this.codingSchemeVersion.toString());
            eAnnotationRole.appendChild(eCodingSchemeVersion);
        }

        Element eRoleSequenceNumber = doc.createElement(Prefix + "roleSequenceNumber");
        eRoleSequenceNumber.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#int");
        eRoleSequenceNumber.setTextContent(this.roleSequenceNumber.toString());
        eAnnotationRole.appendChild(eRoleSequenceNumber);

        return eAnnotationRole;
    }

    private void Control() throws AimException {
        if (getCagridId() == null) {
            throw new AimException("AimException: AnnotationRole's cagridId can not be null");
        }
        if (getCodeValue() == null) {
            throw new AimException("AimException: AnnotationRole's codeValue can not be null");
        }
        if (getCodeMeaning() == null) {
            throw new AimException("AimException: AnnotationRole's codeMeaning can not be null");
        }
        if (getCodingSchemeDesignator() == null) {
            throw new AimException("AimException: AnnotationRole's codingSchemeDesignator can not be null");
        }
        if (getRoleSequenceNumber() == null) {
            throw new AimException("AimException: AnnotationRole's roleSequenceNumber can not be null");
        }
    }
    
    public boolean isEqualTo(Object other) {
        AnnotationRole oth = (AnnotationRole) other;
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
        if (this.roleSequenceNumber == null ? oth.roleSequenceNumber != null : !this.roleSequenceNumber.equals(oth.roleSequenceNumber)) {
            return false;
        }
        return true;
    }
}
