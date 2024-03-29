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
import edu.stanford.hakan.aim4api.base.TwoDimensionGeometricShapeEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 *
 * @author Hakan BULU
 */
public class TwoDimensionSpatialCoordinate extends SpatialCoordinate implements IAimXMLOperations {

    private String imageReferenceUID;
    private Integer referencedFrameNumber;
    private Double x;
    private Double y;

    public TwoDimensionSpatialCoordinate() {
        setXsiType("TwoDimensionSpatialCoordinate");
    }

    public TwoDimensionSpatialCoordinate(Integer cagridId, Integer coordinateIndex, String imageReferenceUID, Integer referencedFrameNumber, Double x, Double y) {

        setCagridId(cagridId);
        setCoordinateIndex(coordinateIndex);
        this.imageReferenceUID = imageReferenceUID;
        this.referencedFrameNumber = referencedFrameNumber;
        this.x = x;
        this.y = y;
        setXsiType("TwoDimensionSpatialCoordinate");
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public String getImageReferenceUID() {
        return imageReferenceUID;
    }

    public void setImageReferenceUID(String imageReferenceUID) {
        this.imageReferenceUID = imageReferenceUID;
    }

    public Integer getReferencedFrameNumber() {
        return referencedFrameNumber;
    }

    public void setReferencedFrameNumber(Integer referencedFrameNumber) {
        this.referencedFrameNumber = referencedFrameNumber;
    }

    @Override
    public Node getXMLNode(Document doc) throws AimException {

        this.Control();
        
        Element spatialCoordinate = (Element) super.getXMLNode(doc);
        spatialCoordinate.setAttribute("imageReferenceUID", this.getImageReferenceUID());
        spatialCoordinate.setAttribute("referencedFrameNumber", this.getReferencedFrameNumber().toString());
        spatialCoordinate.setAttribute("x", this.getX().toString());
        spatialCoordinate.setAttribute("y", this.getY().toString());
        return spatialCoordinate;
    }

    @Override
    public void setXMLNode(Node node) {
        super.setXMLNode(node);
        NamedNodeMap map = node.getAttributes();
        this.setImageReferenceUID(map.getNamedItem("imageReferenceUID").getNodeValue());
        this.setReferencedFrameNumber(Integer.parseInt(map.getNamedItem("referencedFrameNumber").getNodeValue()));
        this.setX(Double.parseDouble(map.getNamedItem("x").getNodeValue()));
        this.setY(Double.parseDouble(map.getNamedItem("y").getNodeValue()));
    }

    @Override
    public Node getRDFNode(Document doc, String unquieID, String Prefix) throws AimException {

        this.Control();
        
        Element eTwoDimensionSpatialCoordinate = (Element) super.getRDFNode(doc, unquieID, Prefix);

        Element eImageReferenceUID = doc.createElement(Prefix + "imageReferenceUID");
        eImageReferenceUID.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        eImageReferenceUID.setTextContent(this.imageReferenceUID.toString());
        eTwoDimensionSpatialCoordinate.appendChild(eImageReferenceUID);

        Element eReferencedFrameNumber = doc.createElement(Prefix + "referencedFrameNumber");
        eReferencedFrameNumber.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#int");
        eReferencedFrameNumber.setTextContent(this.referencedFrameNumber.toString());
        eTwoDimensionSpatialCoordinate.appendChild(eReferencedFrameNumber);

        Element eX = doc.createElement(Prefix + "x");
        eX.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#float");
        eX.setTextContent(this.x.toString());
        eTwoDimensionSpatialCoordinate.appendChild(eX);

        Element eY = doc.createElement(Prefix + "y");
        eY.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#float");
        eY.setTextContent(this.y.toString());
        eTwoDimensionSpatialCoordinate.appendChild(eY);

        return eTwoDimensionSpatialCoordinate;
    }

    private void Control() throws AimException {
        if (this.getImageReferenceUID() == null) {
            throw new AimException("AimException: TwoDimensionSpatialCoordinate's imageReferenceUID can not be null");
        }
        if (this.getReferencedFrameNumber() == null) {
            throw new AimException("AimException: TwoDimensionSpatialCoordinate's referencedFrameNumber can not be null");
        }
        if (this.getX() == null) {
            throw new AimException("AimException: TwoDimensionSpatialCoordinate's x can not be null");
        }
        if (this.getY() == null) {
            throw new AimException("AimException: TwoDimensionSpatialCoordinate's y can not be null");
        }
    }
    
        
        
    @Override
    public boolean isEqualTo(Object other) {
        TwoDimensionSpatialCoordinate oth = (TwoDimensionSpatialCoordinate) other;
        if (this.x == null ? oth.x != null : !this.x.equals(oth.x)) {
            return false;
        }
        if (this.y == null ? oth.y != null : !this.y.equals(oth.y)) {
            return false;
        }
        if (this.imageReferenceUID == null ? oth.imageReferenceUID != null : !this.imageReferenceUID.equals(oth.imageReferenceUID)) {
            return false;
        }
        if (this.referencedFrameNumber == null ? oth.referencedFrameNumber != null : !this.referencedFrameNumber.equals(oth.referencedFrameNumber)) {
            return false;
        }
        return super.isEqualTo(other);
    }

    public edu.stanford.hakan.aim4api.base.TwoDimensionSpatialCoordinate toAimV4(TwoDimensionGeometricShapeEntity twoDimensionGeometricShapeEntity) {
        edu.stanford.hakan.aim4api.base.TwoDimensionSpatialCoordinate res = new edu.stanford.hakan.aim4api.base.TwoDimensionSpatialCoordinate();
        res.setCoordinateIndex(this.getCoordinateIndex());
        res.setX(this.getX());
        res.setY(this.getY());
        res.setTwoDimensionGeometricShapeEntity(twoDimensionGeometricShapeEntity);
        //res.setTwoDimensionSpatialCoordinateCollection(twoDimensionSpatialCoordinateCollection);
        res.getTwoDimensionGeometricShapeEntity().setReferencedFrameNumber(this.getReferencedFrameNumber());
        res.getTwoDimensionGeometricShapeEntity().setImageReferenceUid(Converter.toII(this.getImageReferenceUID()));
        return res;
    }

    public TwoDimensionSpatialCoordinate(edu.stanford.hakan.aim4api.base.TwoDimensionSpatialCoordinate v4) {
        setXsiType("TwoDimensionSpatialCoordinate");
        this.setCagridId(0);
        this.setCoordinateIndex(v4.getCoordinateIndex());
        this.setX(v4.getX());
        this.setY(v4.getY());
        if (v4.getTwoDimensionGeometricShapeEntity() != null && v4.getTwoDimensionGeometricShapeEntity().getImageReferenceUid() != null) {
            this.setImageReferenceUID(v4.getTwoDimensionGeometricShapeEntity().getImageReferenceUid().getRoot());
        }
        if (v4.getTwoDimensionGeometricShapeEntity() != null) {
            this.setReferencedFrameNumber(v4.getTwoDimensionGeometricShapeEntity().getReferencedFrameNumber());
        }
    }

}
