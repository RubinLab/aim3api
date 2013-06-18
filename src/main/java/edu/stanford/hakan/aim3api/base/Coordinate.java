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
public class Coordinate implements IAimXMLOperations {

    private Integer cagridId;
    private Integer dimensionIndex;
    private Integer position;

    public Coordinate() {
    }

    public Coordinate(Integer cagridId, Integer dimensionIndex, Integer position) {
        this.cagridId = cagridId;
        this.dimensionIndex = dimensionIndex;
        this.position = position;
    }

    public Integer getCagridId() {
        return cagridId;
    }

    public void setCagridId(Integer cagridId) {
        this.cagridId = cagridId;
    }

    public Integer getDimensionIndex() {
        return dimensionIndex;
    }

    public void setDimensionIndex(Integer dimensionIndex) {
        this.dimensionIndex = dimensionIndex;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public Node getXMLNode(Document doc) throws AimException {

        this.Control();
        
        Element coordinate = doc.createElement("Coordinate");
        coordinate.setAttribute("cagridId", this.cagridId.toString());
        coordinate.setAttribute("dimensionIndex", this.dimensionIndex.toString());
        coordinate.setAttribute("position", this.position.toString());
        return coordinate;
    }

    @Override
    public void setXMLNode(Node node) {
        NamedNodeMap map = node.getAttributes();
        this.cagridId = Integer.parseInt(map.getNamedItem("cagridId").getNodeValue());
        this.dimensionIndex = Integer.parseInt(map.getNamedItem("dimensionIndex").getNodeValue());
        this.position = Integer.parseInt(map.getNamedItem("position").getNodeValue());
    }

    @Override
    public Node getRDFNode(Document doc, String unquieID, String Prefix) throws AimException {
        
        this.Control();
        
        Element eCoordinate = doc.createElement(Prefix + "Coordinate");
        eCoordinate.setAttribute("rdf:ID", "Coordinate_".concat(unquieID));

        Element eCagridId = doc.createElement(Prefix + "cagridId");
        eCagridId.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#int");
        eCagridId.setTextContent(this.cagridId.toString());
        eCoordinate.appendChild(eCagridId);

        Element eDimensionIndex = doc.createElement(Prefix + "dimensionIndex");
        eDimensionIndex.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#int");
        eDimensionIndex.setTextContent(this.dimensionIndex.toString());
        eCoordinate.appendChild(eDimensionIndex);

        Element ePosition = doc.createElement(Prefix + "position");
        ePosition.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#int");
        ePosition.setTextContent(this.position.toString());
        eCoordinate.appendChild(ePosition);

        return eCoordinate;


    }

    private void Control() throws AimException {
        if (getCagridId() == null) {
            throw new AimException("AimException: Coordinate's cagridId can not be null");
        }
        if (getDimensionIndex() == null) {
            throw new AimException("AimException: Coordinate's dimensionIndex can not be null");
        }
        if (getPosition() == null) {
            throw new AimException("AimException: Coordinate's position can not be null");
        }
    }
    
    public boolean isEqualTo(Object other) {
        Coordinate oth = (Coordinate) other;
        if (this.cagridId != oth.cagridId) {
            return false;
        }
        if (this.dimensionIndex == null ? oth.dimensionIndex != null : !this.dimensionIndex.equals(oth.dimensionIndex)) {
            return false;
        }
        if (this.position == null ? oth.position != null : !this.position.equals(oth.position)) {
            return false;
        }
        return true;
    }
}
