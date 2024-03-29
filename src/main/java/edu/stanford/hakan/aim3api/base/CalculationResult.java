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

import edu.stanford.hakan.aim3api.base.AimUtility.CalculationResultIdentifier;
import edu.stanford.hakan.aim4api.base.CD;
import edu.stanford.hakan.aim3api.utility.Converter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author pc
 */
public class CalculationResult implements IAimXMLOperations {

    private Integer cagridId;
    private AimUtility.CalculationResultIdentifier type;
    private Integer numberOfDimensions;
    private String unitOfMeasure;
    private CalculationDataCollection calculationDataCollection = new CalculationDataCollection();
    private DimensionCollection dimensionCollection = new DimensionCollection();

    public CalculationResult() {
    }

    public CalculationResult(Integer cagridId, AimUtility.CalculationResultIdentifier type, Integer numberOfDimensions, String unitOfMeasure) {
        this.cagridId = cagridId;
        this.type = type;
        this.numberOfDimensions = numberOfDimensions;
        this.unitOfMeasure = unitOfMeasure;
    }

    public void addCalculationData(CalculationData newCalculationData) {
        this.calculationDataCollection.AddCalculationData(newCalculationData);
    }

    public void addDimension(Dimension newDimension) {
        this.dimensionCollection.AddDimension(newDimension);
    }

    public Integer getCagridId() {
        return cagridId;
    }

    public void setCagridId(Integer cagridId) {
        this.cagridId = cagridId;
    }

    public CalculationDataCollection getCalculationDataCollection() {
        return calculationDataCollection;
    }

    public void setCalculationDataCollection(CalculationDataCollection calculationDataCollection) {
        this.calculationDataCollection = calculationDataCollection;
    }

    public DimensionCollection getDimensionCollection() {
        return dimensionCollection;
    }

    public void setDimensionCollection(DimensionCollection dimensionCollection) {
        this.dimensionCollection = dimensionCollection;
    }

    public Integer getNumberOfDimensions() {
        return numberOfDimensions;
    }

    public void setNumberOfDimensions(Integer numberOfDimensions) {
        this.numberOfDimensions = numberOfDimensions;
    }

    public CalculationResultIdentifier getType() {
        return type;
    }

    public void setType(CalculationResultIdentifier type) {
        this.type = type;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    @Override
    public Node getXMLNode(Document doc) throws AimException {

        this.Control();

        Element calculationResult = doc.createElement("CalculationResult");
        calculationResult.setAttribute("cagridId", this.cagridId.toString());
        calculationResult.setAttribute("type", this.type.toString());
        calculationResult.setAttribute("numberOfDimensions", this.numberOfDimensions.toString());
        calculationResult.setAttribute("unitOfMeasure", this.unitOfMeasure);
        if (this.calculationDataCollection.getCalculationDataList().size() > 0) {
            calculationResult.appendChild(this.calculationDataCollection.getXMLNode(doc));
        }
        if (this.dimensionCollection.getDimensionList().size() > 0) {
            calculationResult.appendChild(this.dimensionCollection.getXMLNode(doc));
        }
        return calculationResult;
    }

    @Override
    public void setXMLNode(Node node) {

        NodeList listChils = node.getChildNodes();
        for (int i = 0; i < listChils.getLength(); i++) {
            if ("calculationDataCollection".equals(listChils.item(i).getNodeName())) {
                this.calculationDataCollection.setXMLNode(listChils.item(i));
            } else if ("dimensionCollection".equals(listChils.item(i).getNodeName())) {
                this.dimensionCollection.setXMLNode(listChils.item(i));
            }
        }

        NamedNodeMap map = node.getAttributes();
        this.cagridId = Integer.parseInt(map.getNamedItem("cagridId").getNodeValue());
        this.type = CalculationResultIdentifier.valueOf(map.getNamedItem("type").getNodeValue());
        this.numberOfDimensions = Integer.parseInt(map.getNamedItem("numberOfDimensions").getNodeValue());
        this.unitOfMeasure = map.getNamedItem("unitOfMeasure").getNodeValue();
    }

    @Override
    public Node getRDFNode(Document doc, String unquieID, String Prefix) throws AimException {
        this.Control();
        Element eCalculationResult = doc.createElement(Prefix + "CalculationResult");
        eCalculationResult.setAttribute("rdf:ID", "CalculationResult_".concat(unquieID));

        Element eCagridId = doc.createElement(Prefix + "cagridId");
        eCagridId.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#int");
        eCagridId.setTextContent(this.cagridId.toString());
        eCalculationResult.appendChild(eCagridId);

        Element eType = doc.createElement(Prefix + "type");
        eType.setTextContent(this.type.toString());
        eCalculationResult.appendChild(eType);

        Element eNumberOfDimensions = doc.createElement(Prefix + "numberOfDimensions");
        eNumberOfDimensions.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#int");
        eNumberOfDimensions.setTextContent(this.numberOfDimensions.toString());
        eCalculationResult.appendChild(eNumberOfDimensions);

        Element eUnitOfMeasure = doc.createElement(Prefix + "unitOfMeasure");
        eUnitOfMeasure.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        eUnitOfMeasure.setTextContent(this.unitOfMeasure.toString());
        eCalculationResult.appendChild(eUnitOfMeasure);

        this.calculationDataCollection.appendNodes(doc, unquieID, eCalculationResult, Prefix);
        this.dimensionCollection.appendNodes(doc, unquieID, eCalculationResult, Prefix);

        return eCalculationResult;
    }

    private void Control() throws AimException {

        if (getCagridId() == null) {
            throw new AimException("AimException: CalculationResult's cagridId can not be null");
        }
        if (getType() == null) {
            throw new AimException("AimException: CalculationResult's type can not be null");
        }
        if (getNumberOfDimensions() == null) {
            throw new AimException("AimException: CalculationResult's numberOfDimensions can not be null");
        }
        if (getUnitOfMeasure() == null) {
            throw new AimException("AimException: CalculationResult's unitOfMeasure can not be null");
        }
    }

    public boolean isEqualTo(Object other) {
        CalculationResult oth = (CalculationResult) other;
        if (this.cagridId != oth.cagridId) {
            return false;
        }
        if (this.type == null ? oth.type != null : !this.type.equals(oth.type)) {
            return false;
        }
        if (this.numberOfDimensions == null ? oth.numberOfDimensions != null : !this.numberOfDimensions.equals(oth.numberOfDimensions)) {
            return false;
        }
        if (this.unitOfMeasure == null ? oth.unitOfMeasure != null : !this.unitOfMeasure.equals(oth.unitOfMeasure)) {
            return false;
        }
        if (!this.calculationDataCollection.isEqualTo(oth.calculationDataCollection)) {
            return false;
        }
        if (!this.dimensionCollection.isEqualTo(oth.dimensionCollection)) {
            return false;
        }
        return true;
    }

    public edu.stanford.hakan.aim4api.base.CalculationResult toAimV4() {
        edu.stanford.hakan.aim4api.base.ExtendedCalculationResult res = new edu.stanford.hakan.aim4api.base.ExtendedCalculationResult();
        res.setDimensionCollection(this.getDimensionCollection().toAimV4());//
        res.setUnitOfMeasure(Converter.toST(this.getUnitOfMeasure()));//
        res.setCalculationDataCollection(this.getCalculationDataCollection().toAimV4());//
        res.setType(Converter.toAimV4(this.getType())); //
        res.setDataType(new CD("", "", "", ""));
        return res;
    }

    public CalculationResult(edu.stanford.hakan.aim4api.base.ExtendedCalculationResult v4) {
        this.setCagridId(0);
        this.setCalculationDataCollection(new CalculationDataCollection(v4.getCalculationDataCollection()));
        this.setDimensionCollection(new DimensionCollection(v4.getDimensionCollection()));
        this.setType(Converter.toAimV3(v4.getType()));
        this.setNumberOfDimensions(0);
        if (v4.getUnitOfMeasure() != null) {
            this.setUnitOfMeasure(v4.getUnitOfMeasure().getValue());
        }
    }
}
