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
public class Calculation implements IAimXMLOperations {

    private Integer cagridId;
    private String uid;
    private String description;
    private String mathML;
    private String codeValue;
    private String codeMeaning;
    private String codingSchemeDesignator;
    private String codingSchemeVersion;
    private String algorithmName;
    private String algorithmVersion;
    private CalculationResultCollection calculationResultCollection;
    private ReferencedCalculationCollection referencedCalculationCollection;
    private ReferencedGeometricShapeCollection referencedGeometricShapeCollection;
    private String rdfID;
    private boolean codeValueCanBeNull;

    public Calculation() {
        this.calculationResultCollection = new CalculationResultCollection();
        this.referencedCalculationCollection = new ReferencedCalculationCollection();
        this.referencedGeometricShapeCollection = new ReferencedGeometricShapeCollection();
        this.codeValueCanBeNull = false;
    }

    public Calculation(Integer cagridId, String uid, String description, String mathML, String codeValue, String codeMeaning, String codingSchemeDesignator, String codingSchemeVersion, String algorithmName, String algorithmVersion) {
        this.cagridId = cagridId;
        this.uid = uid;
        this.description = description;
        this.mathML = mathML;
        this.codeValue = codeValue;
        this.codeMeaning = codeMeaning;
        this.codingSchemeDesignator = codingSchemeDesignator;
        this.codingSchemeVersion = codingSchemeVersion;
        this.algorithmName = algorithmName;
        this.algorithmVersion = algorithmVersion;
        this.codeValueCanBeNull = false;

        this.calculationResultCollection = new CalculationResultCollection();
        this.referencedCalculationCollection = new ReferencedCalculationCollection();
        this.referencedGeometricShapeCollection = new ReferencedGeometricShapeCollection();
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public String getAlgorithmVersion() {
        return algorithmVersion;
    }

    public void setAlgorithmVersion(String algorithmVersion) {
        this.algorithmVersion = algorithmVersion;
    }

    public Integer getCagridId() {
        return cagridId;
    }

    public void setCagridId(Integer cagridId) {
        this.cagridId = cagridId;
    }

    public CalculationResultCollection getCalculationResultCollection() {
        return calculationResultCollection;
    }

    public void setCalculationResultCollection(CalculationResultCollection calculationResultCollection) {
        this.calculationResultCollection = calculationResultCollection;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMathML() {
        return mathML;
    }

    public void setMathML(String mathML) {
        this.mathML = mathML;
    }

    public ReferencedCalculationCollection getReferencedCalculationCollection() {
        return referencedCalculationCollection;
    }

    public void setReferencedCalculationCollection(ReferencedCalculationCollection referencedCalculationCollection) {
        this.referencedCalculationCollection = referencedCalculationCollection;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ReferencedGeometricShapeCollection getReferencedGeometricShapeCollection() {
        return referencedGeometricShapeCollection;
    }

    public void setReferencedGeometricShapeCollection(ReferencedGeometricShapeCollection referencedGeometricShapeCollection) {
        this.referencedGeometricShapeCollection = referencedGeometricShapeCollection;
    }

    public void addReferencedCalculation(ReferencedCalculation newReferencedCalculation) {
        this.referencedCalculationCollection.AddReferencedCalculation(newReferencedCalculation);
    }

    public void addCalculationResult(CalculationResult newCalculationResult) {
        this.calculationResultCollection.AddCalculationResult(newCalculationResult);
    }

    public void addReferencedGeometricShape(ReferencedGeometricShape newReferencedGeometricShape) {
        this.referencedGeometricShapeCollection.AddReferencedGeometricShape(newReferencedGeometricShape);
    }

    public String getRdfID() {
        return rdfID;
    }

    public void setRdfID(String rdfID) {
        this.rdfID = rdfID;
    }

    public boolean isCodeValueCanBeNull() {
        return codeValueCanBeNull;
    }

    public void setCodeValueCanBeNull(boolean codeValueCanBeNull) {
        this.codeValueCanBeNull = codeValueCanBeNull;
    }

    @Override
    public Node getXMLNode(Document doc) throws AimException {
        this.Control();

        Element calculation = doc.createElement("Calculation");
        calculation.setAttribute("cagridId", this.cagridId.toString());
        calculation.setAttribute("uid", this.uid);
        calculation.setAttribute("description", this.description);
        if (this.mathML != null) {
            calculation.setAttribute("mathML", this.mathML);
        }
        if (this.codeValue != null) {
            calculation.setAttribute("codeValue", this.codeValue);
        }
        calculation.setAttribute("codeMeaning", this.codeMeaning);
        calculation.setAttribute("codingSchemeDesignator", this.codingSchemeDesignator);
        if (this.codingSchemeVersion != null) {
            calculation.setAttribute("codingSchemeVersion", this.codingSchemeVersion);
        }
        if (this.algorithmName != null) {
            calculation.setAttribute("algorithmName", this.algorithmName);
        }
        if (this.algorithmVersion != null) {
            calculation.setAttribute("algorithmVersion", this.algorithmVersion);
        }
        if (this.referencedCalculationCollection.getReferencedCalculationList().size() > 0) {
            calculation.appendChild(this.referencedCalculationCollection.getXMLNode(doc));
        }
        if (this.calculationResultCollection.getCalculationResultList().size() > 0) {
            calculation.appendChild(this.calculationResultCollection.getXMLNode(doc));
        }
        if (this.referencedGeometricShapeCollection.getReferencedGeometricShapeList().size() > 0) {
            calculation.appendChild(this.referencedGeometricShapeCollection.getXMLNode(doc));
        }
        return calculation;
    }

    @Override
    public void setXMLNode(Node node) {

        NodeList listChils = node.getChildNodes();
        for (int i = 0; i < listChils.getLength(); i++) {
            if ("referencedCalculationCollection".equals(listChils.item(i).getNodeName())) {
                this.referencedCalculationCollection.setXMLNode(listChils.item(i));
            } else if ("calculationResultCollection".equals(listChils.item(i).getNodeName())) {
                this.calculationResultCollection.setXMLNode(listChils.item(i));
            } else if ("referencedGeometricShapeCollection".equals(listChils.item(i).getNodeName())) {
                this.referencedGeometricShapeCollection.setXMLNode(listChils.item(i));
            }
        }

        NamedNodeMap map = node.getAttributes();
        this.cagridId = Integer.parseInt(map.getNamedItem("cagridId").getNodeValue());
        this.uid = map.getNamedItem("uid").getNodeValue();
        this.description = map.getNamedItem("description").getNodeValue();
        if (map.getNamedItem("mathML") != null) {
            this.mathML = map.getNamedItem("mathML").getNodeValue();
        }
        if (map.getNamedItem("codeValue") != null) {
            this.codeValue = map.getNamedItem("codeValue").getNodeValue();
        }
        this.codeMeaning = map.getNamedItem("codeMeaning").getNodeValue();
        this.codingSchemeDesignator = map.getNamedItem("codingSchemeDesignator").getNodeValue();
        if (map.getNamedItem("codingSchemeVersion") != null) {
            this.codingSchemeVersion = map.getNamedItem("codingSchemeVersion").getNodeValue();
        }
        if (map.getNamedItem("algorithmName") != null) {
            this.algorithmName = map.getNamedItem("algorithmName").getNodeValue();
        }
        if (map.getNamedItem("algorithmVersion") != null) {
            this.algorithmVersion = map.getNamedItem("algorithmVersion").getNodeValue();
        }
    }

    @Override
    public Node getRDFNode(Document doc, String unquieID, String Prefix) throws AimException {
        this.Control();
        Element eCalculation = doc.createElement(Prefix + "Calculation");
        if (this.rdfID != null) {
            eCalculation.setAttribute("rdf:ID", this.rdfID);
        } else {
            eCalculation.setAttribute("rdf:ID", "Calculation_".concat(unquieID));
        }

        Element eCagridId = doc.createElement(Prefix + "cagridId");
        eCagridId.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#int");
        eCagridId.setTextContent(this.cagridId.toString());
        eCalculation.appendChild(eCagridId);

        Element eUid = doc.createElement(Prefix + "uid");
        eUid.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        eUid.setTextContent(this.uid.toString());
        eCalculation.appendChild(eUid);

        Element eDescription = doc.createElement(Prefix + "description");
        eDescription.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        eDescription.setTextContent(this.description.toString());
        eCalculation.appendChild(eDescription);

        Element eMathML = doc.createElement(Prefix + "mathML");
        eMathML.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        if (this.mathML != null) {
            eMathML.setTextContent(this.mathML.toString());
            eCalculation.appendChild(eMathML);
        }

        Element eCodeValue = doc.createElement(Prefix + "codeValue");
        eCodeValue.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        if (this.getCodeValue() != null) {
            eCodeValue.setTextContent(this.codeValue.toString());
        }
        eCalculation.appendChild(eCodeValue);

        Element eCodeMeaning = doc.createElement(Prefix + "codeMeaning");
        eCodeMeaning.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        eCodeMeaning.setTextContent(this.codeMeaning.toString());
        eCalculation.appendChild(eCodeMeaning);

        Element eCodingSchemeDesignator = doc.createElement(Prefix + "codingSchemeDesignator");
        eCodingSchemeDesignator.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        eCodingSchemeDesignator.setTextContent(this.codingSchemeDesignator.toString());
        eCalculation.appendChild(eCodingSchemeDesignator);

        Element eCodingSchemeVersion = doc.createElement(Prefix + "codingSchemeVersion");
        eCodingSchemeVersion.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        if (this.codingSchemeVersion != null) {
            eCodingSchemeVersion.setTextContent(this.codingSchemeVersion.toString());
            eCalculation.appendChild(eCodingSchemeVersion);
        }

        Element eAlgorithmName = doc.createElement(Prefix + "algorithmName");
        eAlgorithmName.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        if (this.algorithmName != null) {
            eAlgorithmName.setTextContent(this.algorithmName.toString());
            eCalculation.appendChild(eAlgorithmName);
        }

        Element eAlgorithmVersion = doc.createElement(Prefix + "algorithmVersion");
        eAlgorithmVersion.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        if (this.algorithmVersion != null) {
            eAlgorithmVersion.setTextContent(this.algorithmVersion.toString());
            eCalculation.appendChild(eAlgorithmVersion);
        }


        this.referencedCalculationCollection.appendNodes(doc, unquieID, eCalculation, Prefix);
        this.calculationResultCollection.appendNodes(doc, unquieID, eCalculation, Prefix);
        this.referencedGeometricShapeCollection.appendNodes(doc, unquieID, eCalculation, Prefix);

        return eCalculation;


    }

    private void Control() throws AimException {

        if (getCagridId() == null) {
            throw new AimException("AimException: Calculation's cagridId can not be null");
        }
        if (getUid() == null) {
            throw new AimException("AimException: Calculation's uid can not be null");
        }
        if (getDescription() == null) {
            throw new AimException("AimException: Calculation's description can not be null");
        }
        if (!this.codeValueCanBeNull && getCodeValue() == null) {
            throw new AimException("AimException: Calculation's codeValue can not be null");
        }
        if (getCodeMeaning() == null) {
            throw new AimException("AimException: Calculation's codeMeaning can not be null");
        }
        if (getCodingSchemeDesignator() == null) {
            throw new AimException("AimException: Calculation's codingSchemeDesignator can not be null");
        }
    }

    public boolean isEqualTo(Object other) {
        Calculation oth = (Calculation) other;
        if (this.cagridId != oth.cagridId) {
            return false;
        }
        if (this.uid == null ? oth.uid != null : !this.uid.equals(oth.uid)) {
            return false;
        }
        if (this.description == null ? oth.description != null : !this.description.equals(oth.description)) {
            return false;
        }
        if (this.mathML == null ? oth.mathML != null : !this.mathML.equals(oth.mathML)) {
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
        if (this.algorithmName == null ? oth.algorithmName != null : !this.algorithmName.equals(oth.algorithmName)) {
            return false;
        }
        if (this.algorithmVersion == null ? oth.algorithmVersion != null : !this.algorithmVersion.equals(oth.algorithmVersion)) {
            return false;
        }
        if (!this.referencedCalculationCollection.isEqualTo(oth.referencedCalculationCollection)) {
            return false;
        }
        if (!this.calculationResultCollection.isEqualTo(oth.calculationResultCollection)) {
            return false;
        }
        if (!this.referencedGeometricShapeCollection.isEqualTo(oth.referencedGeometricShapeCollection)) {
            return false;
        }
        return true;
    }
}
