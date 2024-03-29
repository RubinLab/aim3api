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
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Hakan BULU
 */
public class GeometricShape implements IGeometricShape, IAimXMLOperations {

    private Integer cagridId;
    private String lineColor;
    private String lineOpacity;
    private String lineStyle;
    private String lineThickness;
    private Boolean includeFlag;
    private Integer shapeIdentifier;
    private SpatialCoordinateCollection spatialCoordinateCollection = new SpatialCoordinateCollection();
    private String xsiType;

    protected void setXsiType(String xsiType) {
        this.xsiType = xsiType;
    }

    public String getXsiType() {
        return this.xsiType;
    }

    public GeometricShape() {
    }

    public GeometricShape(Integer cagridId, String lineColor, String lineOpacity, String lineStyle, String lineThickness, Boolean includeFlag, Integer shapeIdentifier) {
        this.cagridId = cagridId;
        this.lineColor = lineColor;
        this.lineOpacity = lineOpacity;
        this.lineStyle = lineStyle;
        this.lineThickness = lineThickness;
        this.includeFlag = includeFlag;
        this.shapeIdentifier = shapeIdentifier;
    }

    @Override
    public Integer getCagridId() {
        return this.cagridId;
    }

    @Override
    public void setCagridId(Integer cagridId) {
        this.cagridId = cagridId;
    }

    @Override
    public Boolean getIncludeFlag() {
        return this.includeFlag;
    }

    @Override
    public void setIncludeFlag(Boolean includeFlag) {
        this.includeFlag = includeFlag;
    }

    @Override
    public String getLineColor() {
        return this.lineColor;
    }

    @Override
    public void setLineColor(String lineColor) {
        this.lineColor = lineColor;
    }

    @Override
    public String getLineOpacity() {
        return this.lineOpacity;
    }

    @Override
    public void setLineOpacity(String lineOpacity) {
        this.lineOpacity = lineOpacity;
    }

    @Override
    public String getLineStyle() {
        return this.lineStyle;
    }

    @Override
    public void setLineStyle(String lineStyle) {
        this.lineStyle = lineStyle;
    }

    @Override
    public String getLineThickness() {
        return this.lineThickness;
    }

    @Override
    public void setLineThickness(String lineThickness) {
        this.lineThickness = lineThickness;
    }

    @Override
    public Integer getShapeIdentifier() {
        return this.shapeIdentifier;
    }

    @Override
    public void setShapeIdentifier(Integer shapeIdentifier) {
        this.shapeIdentifier = shapeIdentifier;
    }

    @Override
    public void setSpatialCoordinateCollection(SpatialCoordinateCollection spatialCoordinateCollection) {
        this.spatialCoordinateCollection = spatialCoordinateCollection;
    }

    @Override
    public SpatialCoordinateCollection getSpatialCoordinateCollection() {
        return this.spatialCoordinateCollection;
    }

    @Override
    public void addSpatialCoordinate(SpatialCoordinate spatialCoordinate) {
        this.spatialCoordinateCollection.AddSpatialCoordinate(spatialCoordinate);
    }

    public List<SpatialCoordinate> getSpatialCoordinateList() {
        return this.spatialCoordinateCollection.getSpatialCoordinateList();
    }

    @Override
    public Node getXMLNode(Document doc) throws AimException {

        this.Control();

        Element geometricShape = doc.createElement("GeometricShape");
        geometricShape.setAttribute("cagridId", this.cagridId.toString());
        if (this.lineColor != null) {
            geometricShape.setAttribute("lineColor", this.lineColor);
        }
        if (this.lineOpacity != null) {
            geometricShape.setAttribute("lineOpacity", this.lineOpacity);
        }
        if (this.lineStyle != null) {
            geometricShape.setAttribute("lineStyle", this.lineStyle);
        }
        if (this.lineThickness != null) {
            geometricShape.setAttribute("lineThickness", this.lineThickness);
        }
        geometricShape.setAttribute("includeFlag", this.includeFlag.toString());
        geometricShape.setAttribute("shapeIdentifier", this.shapeIdentifier.toString());
        geometricShape.setAttribute("xsi:type", this.xsiType);
        if (this.spatialCoordinateCollection.getSpatialCoordinateList().size() > 0) {
            geometricShape.appendChild(this.spatialCoordinateCollection.getXMLNode(doc));
        }

        return geometricShape;

    }

    @Override
    public void setXMLNode(Node node) {

        NodeList listChils = node.getChildNodes();
        for (int i = 0; i < listChils.getLength(); i++) {
            if ("spatialCoordinateCollection".equals(listChils.item(i).getNodeName())) {
                this.spatialCoordinateCollection.setXMLNode(listChils.item(i));
            }
        }

        NamedNodeMap map = node.getAttributes();
        this.cagridId = Integer.parseInt(map.getNamedItem("cagridId").getNodeValue());
        if (map.getNamedItem("lineColor") != null) {
            this.lineColor = map.getNamedItem("lineColor").getNodeValue();
        }
        if (map.getNamedItem("lineOpacity") != null) {
            this.lineOpacity = map.getNamedItem("lineOpacity").getNodeValue();
        }
        if (map.getNamedItem("lineStyle") != null) {
            this.lineStyle = map.getNamedItem("lineStyle").getNodeValue();
        }
        if (map.getNamedItem("lineThickness") != null) {
            this.lineThickness = map.getNamedItem("lineThickness").getNodeValue();
        }
        this.includeFlag = Boolean.parseBoolean(map.getNamedItem("includeFlag").getNodeValue());
        this.shapeIdentifier = Integer.parseInt(map.getNamedItem("shapeIdentifier").getNodeValue());
        this.xsiType = map.getNamedItem("xsi:type").getNodeValue();

    }

    @Override
    public Node getRDFNode(Document doc, String unquieID, String Prefix) throws AimException {

        this.Control();

        Element eGeometricShape = doc.createElement(Prefix + this.xsiType);
        eGeometricShape.setAttribute("rdf:ID", this.xsiType + "_".concat(unquieID));

        Element eCagridId = doc.createElement(Prefix + "cagridId");
        eCagridId.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#int");
        eCagridId.setTextContent(this.cagridId.toString());
        eGeometricShape.appendChild(eCagridId);

        Element eLineColor = doc.createElement(Prefix + "lineColor");
        eLineColor.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        if (this.lineColor != null) {
            eLineColor.setTextContent(this.lineColor.toString());
            eGeometricShape.appendChild(eLineColor);
        }

        Element eLineOpacity = doc.createElement(Prefix + "lineOpacity");
        eLineOpacity.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        if (this.lineOpacity != null) {
            eLineOpacity.setTextContent(this.lineOpacity.toString());
            eGeometricShape.appendChild(eLineOpacity);
        }

        Element eLineStyle = doc.createElement(Prefix + "lineStyle");
        eLineStyle.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        if (this.lineStyle != null) {
            eLineStyle.setTextContent(this.lineStyle.toString());
            eGeometricShape.appendChild(eLineStyle);
        }

        Element eLineThickness = doc.createElement(Prefix + "lineThickness");
        eLineThickness.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        if (this.lineThickness != null) {
            eLineThickness.setTextContent(this.lineThickness.toString());
            eGeometricShape.appendChild(eLineThickness);
        }

        Element eIncludeFlag = doc.createElement(Prefix + "includeFlag");
        eIncludeFlag.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#boolean");
        eIncludeFlag.setTextContent(this.includeFlag.toString());
        eGeometricShape.appendChild(eIncludeFlag);

        Element eShapeIdentifier = doc.createElement(Prefix + "shapeIdentifier");
        eShapeIdentifier.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#int");
        eShapeIdentifier.setTextContent(this.shapeIdentifier.toString());
        eGeometricShape.appendChild(eShapeIdentifier);

        this.spatialCoordinateCollection.appendNodes(doc, unquieID, eGeometricShape, Prefix);

        return eGeometricShape;
    }

    private void Control() throws AimException {
        if (getCagridId() == null) {
            throw new AimException("AimException: GeometricShape's cagridId can not be null");
        }
        if (getIncludeFlag() == null) {
            throw new AimException("AimException: GeometricShape's includeFlag can not be null");
        }
        if (getShapeIdentifier() == null) {
            throw new AimException("AimException: GeometricShape's shapeIdentifier can not be null");
        }
    }

    public boolean isEqualTo(Object other) {
        GeometricShape oth = (GeometricShape) other;
        if (this.cagridId != oth.cagridId) {
            return false;
        }
        if (this.lineColor == null ? oth.lineColor != null : !this.lineColor.equals(oth.lineColor)) {
            return false;
        }
        if (this.lineOpacity == null ? oth.lineOpacity != null : !this.lineOpacity.equals(oth.lineOpacity)) {
            return false;
        }
        if (this.lineStyle == null ? oth.lineStyle != null : !this.lineStyle.equals(oth.lineStyle)) {
            return false;
        }
        if (this.lineThickness == null ? oth.lineThickness != null : !this.lineThickness.equals(oth.lineThickness)) {
            return false;
        }
        if (this.includeFlag == null ? oth.includeFlag != null : !this.includeFlag.equals(oth.includeFlag)) {
            return false;
        }
        if (this.shapeIdentifier == null ? oth.shapeIdentifier != null : !this.shapeIdentifier.equals(oth.shapeIdentifier)) {
            return false;
        }
        return this.spatialCoordinateCollection.isEqualTo(oth.spatialCoordinateCollection);
    }

    public edu.stanford.hakan.aim4api.base.MarkupEntity toAimV4() {
        edu.stanford.hakan.aim4api.base.TwoDimensionGeometricShapeEntity res = null;

        if ("Circle".equals(this.getXsiType())) {
            res = new edu.stanford.hakan.aim4api.base.TwoDimensionCircle();
        } else if ("Ellipse".equals(this.getXsiType())) {
            res = new edu.stanford.hakan.aim4api.base.TwoDimensionEllipse();
        } else if ("MultiPoint".equals(this.getXsiType())) {
            res = new edu.stanford.hakan.aim4api.base.TwoDimensionMultiPoint();
        } else if ("Point".equals(this.getXsiType())) {
            res = new edu.stanford.hakan.aim4api.base.TwoDimensionPoint();
        } else if ("Polyline".equals(this.getXsiType())) {
            res = new edu.stanford.hakan.aim4api.base.TwoDimensionPolyline();
        }

        res.setIncludeFlag(this.getIncludeFlag());
        res.setLineColor(Converter.toST(this.getLineColor()));
        res.setLineOpacity(Converter.toST(this.getLineOpacity()));
        res.setLineStyle(Converter.toST(this.getLineStyle()));
        res.setLineThickness(Converter.toST(this.getLineThickness()));
        res.setShapeIdentifier(this.getShapeIdentifier());
        res.setTwoDimensionSpatialCoordinateCollection(this.getSpatialCoordinateCollection().toAimV4(res));
        return res;
    }

//    public GeometricShape(edu.stanford.hakan.aim4api.base.MarkupEntity v4) {        
//    }
}
