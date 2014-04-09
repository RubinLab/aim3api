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

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.NodeList;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 *
 * @author Hakan BULU
 */
@SuppressWarnings("serial")
public class ImageAnnotation extends Annotation implements IAimXMLOperations {

    private SegmentationCollection segmentationCollection;
    private ImageReferenceCollection imageReferenceCollection;
    private GeometricShapeCollection geometricShapeCollection;
    private List<Person> listPerson = new ArrayList<Person>();
    private TextAnnotationCollection textAnnotationCollection;
    

    public ImageAnnotation() {
        super();
        this.segmentationCollection = new SegmentationCollection();
        this.imageReferenceCollection = new ImageReferenceCollection();
        this.geometricShapeCollection = new GeometricShapeCollection();
        this.textAnnotationCollection = new TextAnnotationCollection();
        setXsiType("ImageAnnotation");
    }

    public ImageAnnotation(Integer cagridId, String comment, String dateTime, String name, String codeValue, String codeMeaning, String codingSchemeDesignator, String codingSchemeVersion, String precedentReferencedAnnotationUID) {
        super(cagridId, comment, dateTime, name, codeValue, codeMeaning, codingSchemeDesignator, codingSchemeVersion, precedentReferencedAnnotationUID);
        this.segmentationCollection = new SegmentationCollection();
        this.imageReferenceCollection = new ImageReferenceCollection();
        this.geometricShapeCollection = new GeometricShapeCollection();
        this.textAnnotationCollection = new TextAnnotationCollection();
        setXsiType("ImageAnnotation");
    }

    public GeometricShapeCollection getGeometricShapeCollection() {
        return geometricShapeCollection;
    }

    public void setGeometricShapeCollection(GeometricShapeCollection geometricShapeCollection) {
        this.geometricShapeCollection = geometricShapeCollection;
    }

    public ImageReferenceCollection getImageReferenceCollection() {
        return imageReferenceCollection;
    }

    public void setImageReferenceCollection(ImageReferenceCollection imageReferenceCollection) {
        this.imageReferenceCollection = imageReferenceCollection;
    }

    public List<Person> getListPerson() {
        return listPerson;
    }

    public void setListPerson(List<Person> listPerson) {
        this.listPerson = listPerson;
    }

    public SegmentationCollection getSegmentationCollection() {
        return segmentationCollection;
    }

    public void setSegmentationCollection(SegmentationCollection segmentationCollection) {
        this.segmentationCollection = segmentationCollection;
    }

    public TextAnnotationCollection getTextAnnotationCollection() {
        return textAnnotationCollection;
    }

    public void setTextAnnotationCollection(TextAnnotationCollection textAnnotationCollection) {
        this.textAnnotationCollection = textAnnotationCollection;
    }

    public void addPerson(Person newPerson) {
        this.listPerson.add(newPerson);
    }

    public void addSegmentation(Segmentation newSegmentation) {
        this.segmentationCollection.AddSegmentation(newSegmentation);
    }

    public void addImageReference(ImageReference newSImageReference) {
        this.imageReferenceCollection.AddImageReference(newSImageReference);
    }

    public void addGeometricShape(GeometricShape newGeometricShape) {
        this.geometricShapeCollection.AddGeometricShape(newGeometricShape);
    }

    public void addTextAnnotation(TextAnnotation newTextAnnotation) {
        this.textAnnotationCollection.AddTextAnnotation(newTextAnnotation);
    }

    @Override
    public Node getXMLNode(Document doc) throws AimException {

        Element annotation = (Element) super.getXMLNode(doc);

        if (this.getSegmentationCollection().getSegmentationList().size() > 0) {
            annotation.appendChild(this.getSegmentationCollection().getXMLNode(doc));
        }
        if (this.getImageReferenceCollection().getImageReferenceList().size() > 0) {
            annotation.appendChild(this.getImageReferenceCollection().getXMLNode(doc));
        }
        if (this.getGeometricShapeCollection().getGeometricShapeList().size() > 0) {
            annotation.appendChild(this.getGeometricShapeCollection().getXMLNode(doc));
        }
        Element person = doc.createElement("person");
        for (int i = 0; i < this.getListPerson().size(); i++) {
            person.appendChild(this.getListPerson().get(i).getXMLNode(doc));
        }
        if (this.getListPerson().size() > 0) {
            annotation.appendChild(person);
        }
        if (this.getTextAnnotationCollection().getTextAnnotationList().size() > 0) {
            annotation.appendChild(this.getTextAnnotationCollection().getXMLNode(doc));
        }
        return annotation;
    }

    @Override
    public void setXMLNode(Node node) {
        super.setXMLNode(node);

        this.listPerson.clear();
        NodeList listChils = node.getChildNodes();
        for (int i = 0; i < listChils.getLength(); i++) {
            if ("segmentationCollection".equals(listChils.item(i).getNodeName())) {
                this.segmentationCollection.setXMLNode(listChils.item(i));
            } else if ("imageReferenceCollection".equals(listChils.item(i).getNodeName())) {
                this.imageReferenceCollection.setXMLNode(listChils.item(i));
            } else if ("geometricShapeCollection".equals(listChils.item(i).getNodeName())) {
                this.geometricShapeCollection.setXMLNode(listChils.item(i));
            } else if ("textAnnotationCollection".equals(listChils.item(i).getNodeName())) {
                this.textAnnotationCollection.setXMLNode(listChils.item(i));
            } else if ("person".equals(listChils.item(i).getNodeName())) {
                NodeList tempList = listChils.item(i).getChildNodes();
                for (int j = 0; j < tempList.getLength(); j++) {
                    if ("Person".equals(tempList.item(j).getNodeName())) {
                        Person obj = new Person();
                        obj.setXMLNode(tempList.item(j));
                        this.addPerson(obj);
                    }
                }
            }
        }    
    }

    @Override
    public Node getRDFNode(Document doc, String unquieID, String Prefix) throws AimException {

        Element eImageAnnotation = (Element) super.getRDFNode(doc, unquieID, Prefix);

        this.segmentationCollection.appendNodes(doc, unquieID, eImageAnnotation, Prefix);
        this.imageReferenceCollection.appendNodes(doc, unquieID, eImageAnnotation, Prefix);
        this.geometricShapeCollection.appendNodes(doc, unquieID, eImageAnnotation, Prefix);
        this.textAnnotationCollection.appendNodes(doc, unquieID, eImageAnnotation, Prefix);

        for (int i = 0; i < listPerson.size(); i++) {
            Person person = listPerson.get(i);
            String postFix = "_".concat(Integer.toString(i + 1));
            if (person.isDontAddIndexAsPostFixToRdfInstanceName()) {
                postFix = "";
            }

            Element eHas = doc.createElement(Prefix + "hasPerson");

            if (person.isDontCreateOwlIntance()) {
                eHas.setAttribute("rdf:resource", "#".concat(person.getRdfID()));
            } else if (person.getRdfID() != null) {
                eHas.appendChild(person.getRDFNode(doc, person.getRdfID().concat(postFix), Prefix));
            } else {
                eHas.appendChild(person.getRDFNode(doc, unquieID.concat(postFix), Prefix));
            }
            eImageAnnotation.appendChild(eHas);
        }

        return eImageAnnotation;
    }
    
    @Override
    public boolean isEqualTo(Object other) {
        ImageAnnotation oth = (ImageAnnotation) other;        
        if (this.listPerson.size() != oth.listPerson.size()) {
            return false;
        }
        for (int i = 0; i < this.listPerson.size(); i++) {
            if (!this.listPerson.get(i).isEqualTo(oth.listPerson.get(i))) {
                return false;
            }
        }
        if (!this.segmentationCollection.isEqualTo(oth.segmentationCollection)) {
            return false;
        }
        if (!this.imageReferenceCollection.isEqualTo(oth.imageReferenceCollection)) {
            return false;
        }
        if (!this.geometricShapeCollection.isEqualTo(oth.geometricShapeCollection)) {
            return false;
        }
        if (!this.textAnnotationCollection.isEqualTo(oth.textAnnotationCollection)) {
            return false;
        }
        return super.isEqualTo(other);
    }
    
}
