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
package edu.stanford.hakan.aim3api.aimquery;

import edu.stanford.hakan.aim3api.base.AimException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author Hakan
 */
public class QueryMapping {

    private Hashtable<String, String> hashClasses = new Hashtable<String, String>();
    private Hashtable<String, String> hashAttributes = new Hashtable<String, String>();

    public QueryMapping() {
        this.fillTheHastable();
    }

    private void fillTheHastable() {
        //*** Classes
        this.hashClasses.put("MultiPoint".toLowerCase(new Locale("\\u0131")), "TextAnnotation/multiPoint/MultiPoint");
        this.hashClasses.put("ReferencedCalculation".toLowerCase(new Locale("\\u0131")), "Calculation/referencedCalculationCollection/ReferencedCalculation");
        this.hashClasses.put("ThreeDimensionSpatialCoordinate".toLowerCase(new Locale("\\u0131")), "GeometricShape/spatialCoordinateCollection/ThreeDimensionSpatialCoordinate");
        this.hashClasses.put("TwoDimensionSpatialCoordinate".toLowerCase(new Locale("\\u0131")), "GeometricShape/spatialCoordinateCollection/TwoDimensionSpatialCoordinate");
        this.hashClasses.put("ReferencedAnnotation".toLowerCase(new Locale("\\u0131")), "AnnotationOfAnnotation/referencedAnnotationCollection/ReferencedAnnotation");
        this.hashClasses.put("AnnotationRole".toLowerCase(new Locale("\\u0131")), "ReferencedAnnotation/annotationRole/AnnotationRole");
        this.hashClasses.put("Numerical".toLowerCase(new Locale("\\u0131")), "AnatomicEntityCharacteristic/characteristicQuantificationCollection/Numerical");
        this.hashClasses.put("CalculationData".toLowerCase(new Locale("\\u0131")), "CalculationResult/calculationDataCollection/CalculationData");
        this.hashClasses.put("Coordinate".toLowerCase(new Locale("\\u0131")), "CalculationData/coordinateCollection/Coordinate");
        this.hashClasses.put("ReferencedGeometricShape".toLowerCase(new Locale("\\u0131")), "Calculation/referencedGeometricShapeCollection/ReferencedGeometricShape");
        this.hashClasses.put("ReferencedGeometricShape".toLowerCase(new Locale("\\u0131")), "ImagingObservation/referencedGeometricShape/ReferencedGeometricShape");
        this.hashClasses.put("AnatomicEntity".toLowerCase(new Locale("\\u0131")), "ImageAnnotation/anatomicEntityCollection/AnatomicEntity");
        this.hashClasses.put("AnatomicEntityCharacteristic".toLowerCase(new Locale("\\u0131")), "AnatomicEntity/anatomicEntityCharacteristicCollection/AnatomicEntityCharacteristic");
        this.hashClasses.put("Scale".toLowerCase(new Locale("\\u0131")), "AnatomicEntityCharacteristic/characteristicQuantificationCollection/Scale");
        this.hashClasses.put("Quantile".toLowerCase(new Locale("\\u0131")), "AnatomicEntityCharacteristic/characteristicQuantificationCollection/Quantile");
        this.hashClasses.put("NonQuantifiable".toLowerCase(new Locale("\\u0131")), "AnatomicEntityCharacteristic/characteristicQuantificationCollection/NonQuantifiable");
        this.hashClasses.put("Interval".toLowerCase(new Locale("\\u0131")), "AnatomicEntityCharacteristic/characteristicQuantificationCollection/Interval");
        this.hashClasses.put("DICOMImageReference".toLowerCase(new Locale("\\u0131")), "ImageAnnotation/imageReferenceCollection/DICOMImageReference");
        this.hashClasses.put("WebImageReference".toLowerCase(new Locale("\\u0131")), "ImageAnnotation/imageReferenaceCollection/WebImageReference");
        //this.hashClasses.put("ImageStudy".toLowerCase(new Locale("\\u0131")), "DICOMImageReference/imageStudy/ImageStudy");
        this.hashClasses.put("ImageStudy".toLowerCase(new Locale("\\u0131")), "ImageReference/imageStudy/ImageStudy");
        this.hashClasses.put("PresentationState".toLowerCase(new Locale("\\u0131")), "DICOMImageReference/presentationStateCollection/PresentationState");
        this.hashClasses.put("Dimension".toLowerCase(new Locale("\\u0131")), "CalculationResult/dimensionCollection/Dimension");
        this.hashClasses.put("Image".toLowerCase(new Locale("\\u0131")), "ImageSeries/imageCollection/Image");
        this.hashClasses.put("Point".toLowerCase(new Locale("\\u0131")), "ImageAnnotation/geometricShapeCollection/Point");
        this.hashClasses.put("Polyline".toLowerCase(new Locale("\\u0131")), "ImageAnnotation/geometricShapeCollection/Polyline");
        this.hashClasses.put("Circle".toLowerCase(new Locale("\\u0131")), "ImageAnnotation/geometricShapeCollection/Circle");
        this.hashClasses.put("Ellipse".toLowerCase(new Locale("\\u0131")), "ImageAnnotation/geometricShapeCollection/Ellipse");
        this.hashClasses.put("TextAnnotation".toLowerCase(new Locale("\\u0131")), "ImageAnnotation/textAnnotationCollection/TextAnnotation");
        this.hashClasses.put("GeometricShape".toLowerCase(new Locale("\\u0131")), "ImageAnnotation/geometricShapeCollection/GeometricShape");
        this.hashClasses.put("SpatialCoordinate".toLowerCase(new Locale("\\u0131")), "GeometricShape/SpatialCoordinate/spatialCoordinateCollection");
        this.hashClasses.put("Segmentation".toLowerCase(new Locale("\\u0131")), "ImageAnnotation/segmentationCollection/Segmentation");
        this.hashClasses.put("ImagingObservation".toLowerCase(new Locale("\\u0131")), "Segmentation/imagingObservation/ImagingObservation");
        this.hashClasses.put("ImagingObservation".toLowerCase(new Locale("\\u0131")), "ImageAnnotation/imagingObservationCollection/ImagingObservation");
        this.hashClasses.put("AimStatus".toLowerCase(new Locale("\\u0131")), "ImageAnnotation/aimStatus/AimStatus");
        this.hashClasses.put("Calculation".toLowerCase(new Locale("\\u0131")), "ImageAnnotation/calculationCollection/Calculation");
        this.hashClasses.put("CalculationResult".toLowerCase(new Locale("\\u0131")), "Calculation/calculationResultCollection/CalculationResult");
        this.hashClasses.put("ImagingObservationCharacteristic".toLowerCase(new Locale("\\u0131")), "ImagingObservation/imagingObservationCharacteristicCollection/ImagingObservationCharacteristic");
        this.hashClasses.put("User".toLowerCase(new Locale("\\u0131")), "ImageAnnotation/user/User");
        this.hashClasses.put("ImageSeries".toLowerCase(new Locale("\\u0131")), "ImageStudy/imageSeries/ImageSeries");
        this.hashClasses.put("CharacteristicQuantification".toLowerCase(new Locale("\\u0131")), "ImagingObservationCharacteristic/characteristicQuantificationCollection/CharacteristicQuantification");
        this.hashClasses.put("Equipment".toLowerCase(new Locale("\\u0131")), "ImageAnnotation/equipment/Equipment");
        this.hashClasses.put("Person".toLowerCase(new Locale("\\u0131")), "ImageAnnotation/person/Person");
        this.hashClasses.put("Inference".toLowerCase(new Locale("\\u0131")), "ImageAnnotation/inferenceCollection/Inference");
        this.hashClasses.put("ImageAnnotation".toLowerCase(new Locale("\\u0131")), "ImageAnnotation");

        //*** Attributes
        this.hashAttributes.put("aimVersion".toLowerCase(new Locale("\\u0131")), "aimVersion");
        this.hashAttributes.put("algorithmName".toLowerCase(new Locale("\\u0131")), "algorithmName");
        this.hashAttributes.put("algorithmVersion".toLowerCase(new Locale("\\u0131")), "algorithmVersion");
        this.hashAttributes.put("annotationVersion".toLowerCase(new Locale("\\u0131")), "annotationVersion");
        this.hashAttributes.put("annotatorConfidence".toLowerCase(new Locale("\\u0131")), "annotatorConfidence");
        this.hashAttributes.put("authorizedBy".toLowerCase(new Locale("\\u0131")), "authorizedBy");
        this.hashAttributes.put("birthDate".toLowerCase(new Locale("\\u0131")), "birthDate");
        this.hashAttributes.put("cagridId".toLowerCase(new Locale("\\u0131")), "cagridId");
        this.hashAttributes.put("codeMeaning".toLowerCase(new Locale("\\u0131")), "codeMeaning");
        this.hashAttributes.put("codeValue".toLowerCase(new Locale("\\u0131")), "codeValue");
        this.hashAttributes.put("codingSchemeDesignator".toLowerCase(new Locale("\\u0131")), "codingSchemeDesignator");
        this.hashAttributes.put("codingSchemeVersion".toLowerCase(new Locale("\\u0131")), "codingSchemeVersion");
        this.hashAttributes.put("coordinateIndex".toLowerCase(new Locale("\\u0131")), "coordinateIndex");
        this.hashAttributes.put("dateTime".toLowerCase(new Locale("\\u0131")), "dateTime");
        this.hashAttributes.put("dimensionIndex".toLowerCase(new Locale("\\u0131")), "dimensionIndex");
        this.hashAttributes.put("ethnicGroup".toLowerCase(new Locale("\\u0131")), "ethnicGroup");
        this.hashAttributes.put("fontColor".toLowerCase(new Locale("\\u0131")), "fontColor");
        this.hashAttributes.put("fontEffect".toLowerCase(new Locale("\\u0131")), "fontEffect");
        this.hashAttributes.put("fontOpacity".toLowerCase(new Locale("\\u0131")), "fontOpacity");
        this.hashAttributes.put("fontSize".toLowerCase(new Locale("\\u0131")), "fontSize");
        this.hashAttributes.put("fontStyle".toLowerCase(new Locale("\\u0131")), "fontStyle");
        this.hashAttributes.put("frameOfReferenceUID".toLowerCase(new Locale("\\u0131")), "frameOfReferenceUID");
        this.hashAttributes.put("imageEvidence".toLowerCase(new Locale("\\u0131")), "imageEvidence");
        this.hashAttributes.put("imageReferenceUID".toLowerCase(new Locale("\\u0131")), "imageReferenceUID");
        this.hashAttributes.put("includeFlag".toLowerCase(new Locale("\\u0131")), "includeFlag");
        this.hashAttributes.put("instanceUID".toLowerCase(new Locale("\\u0131")), "instanceUID");
        this.hashAttributes.put("isPresent".toLowerCase(new Locale("\\u0131")), "isPresent");
        this.hashAttributes.put("lineColor".toLowerCase(new Locale("\\u0131")), "lineColor");
        this.hashAttributes.put("lineOpacity".toLowerCase(new Locale("\\u0131")), "lineOpacity");
        this.hashAttributes.put("lineStyle".toLowerCase(new Locale("\\u0131")), "lineStyle");
        this.hashAttributes.put("lineThickness".toLowerCase(new Locale("\\u0131")), "lineThickness");
        this.hashAttributes.put("loginName".toLowerCase(new Locale("\\u0131")), "loginName");
        this.hashAttributes.put("manufacturerModelName".toLowerCase(new Locale("\\u0131")), "manufacturerModelName");
        this.hashAttributes.put("manufacturerName".toLowerCase(new Locale("\\u0131")), "manufacturerName");
        this.hashAttributes.put("mathML".toLowerCase(new Locale("\\u0131")), "mathML");
        this.hashAttributes.put("maxOperator".toLowerCase(new Locale("\\u0131")), "maxOperator");
        this.hashAttributes.put("maxValue".toLowerCase(new Locale("\\u0131")), "maxValue");
        this.hashAttributes.put("minOperator".toLowerCase(new Locale("\\u0131")), "minOperator");
        this.hashAttributes.put("minValue".toLowerCase(new Locale("\\u0131")), "minValue");
        this.hashAttributes.put("numberOfDimensions".toLowerCase(new Locale("\\u0131")), "numberOfDimensions");
        this.hashAttributes.put("numberWithinRoleOfClinicalTrial".toLowerCase(new Locale("\\u0131")), "numberWithinRoleOfClinicalTrial");
        this.hashAttributes.put("precedentReferencedAnnotationUID".toLowerCase(new Locale("\\u0131")), "precedentReferencedAnnotationUID");
        this.hashAttributes.put("referencedAnnotationUID".toLowerCase(new Locale("\\u0131")), "referencedAnnotationUID");
        this.hashAttributes.put("referencedFrameNumber".toLowerCase(new Locale("\\u0131")), "referencedFrameNumber");
        this.hashAttributes.put("referencedShapeIdentifier".toLowerCase(new Locale("\\u0131")), "referencedShapeIdentifier");
        this.hashAttributes.put("referencedSopInstanceUID".toLowerCase(new Locale("\\u0131")), "referencedSopInstanceUID");
        this.hashAttributes.put("roleInTrial".toLowerCase(new Locale("\\u0131")), "roleInTrial");
        this.hashAttributes.put("roleSequenceNumber".toLowerCase(new Locale("\\u0131")), "roleSequenceNumber");
        this.hashAttributes.put("segmentNumber".toLowerCase(new Locale("\\u0131")), "segmentNumber");
        this.hashAttributes.put("shapeIdentifier".toLowerCase(new Locale("\\u0131")), "shapeIdentifier");
        this.hashAttributes.put("softwareVersion".toLowerCase(new Locale("\\u0131")), "softwareVersion");
        this.hashAttributes.put("sopClassUID".toLowerCase(new Locale("\\u0131")), "sopClassUID");
        this.hashAttributes.put("sopInstanceUID".toLowerCase(new Locale("\\u0131")), "sopInstanceUID");
        this.hashAttributes.put("startDate".toLowerCase(new Locale("\\u0131")), "startDate");
        this.hashAttributes.put("startTime".toLowerCase(new Locale("\\u0131")), "startTime");
        this.hashAttributes.put("textJustify".toLowerCase(new Locale("\\u0131")), "textJustify");
        this.hashAttributes.put("ucumString".toLowerCase(new Locale("\\u0131")), "ucumString");
        this.hashAttributes.put("uniqueIdentifier".toLowerCase(new Locale("\\u0131")), "uniqueIdentifier");
        this.hashAttributes.put("unitOfMeasure".toLowerCase(new Locale("\\u0131")), "unitOfMeasure");
        
        this.hashAttributes.put("comment".toLowerCase(new Locale("\\u0131")), "comment");
        this.hashAttributes.put("name".toLowerCase(new Locale("\\u0131")), "name");
        this.hashAttributes.put("value".toLowerCase(new Locale("\\u0131")), "value");
        this.hashAttributes.put("operator".toLowerCase(new Locale("\\u0131")), "operator");
        this.hashAttributes.put("label".toLowerCase(new Locale("\\u0131")), "label");
        this.hashAttributes.put("position".toLowerCase(new Locale("\\u0131")), "position");
        this.hashAttributes.put("uri".toLowerCase(new Locale("\\u0131")), "uri");
        this.hashAttributes.put("index".toLowerCase(new Locale("\\u0131")), "index");
        this.hashAttributes.put("size".toLowerCase(new Locale("\\u0131")), "size");
        this.hashAttributes.put("font".toLowerCase(new Locale("\\u0131")), "font");
        this.hashAttributes.put("text".toLowerCase(new Locale("\\u0131")), "text");
        this.hashAttributes.put("description".toLowerCase(new Locale("\\u0131")), "description");
        this.hashAttributes.put("bin".toLowerCase(new Locale("\\u0131")), "bin");
        this.hashAttributes.put("uid".toLowerCase(new Locale("\\u0131")), "uid");
        this.hashAttributes.put("x".toLowerCase(new Locale("\\u0131")), "x");
        this.hashAttributes.put("y".toLowerCase(new Locale("\\u0131")), "y");
        this.hashAttributes.put("z".toLowerCase(new Locale("\\u0131")), "z");
        this.hashAttributes.put("type".toLowerCase(new Locale("\\u0131")), "type");
        this.hashAttributes.put("id".toLowerCase(new Locale("\\u0131")), "id");
        this.hashAttributes.put("sex".toLowerCase(new Locale("\\u0131")), "sex");
    }

//    "for $x in collection("aim.dbxml")/ImageAnnotation where $x/person/Person[@name="Hakan Bulu"] return $x"
    public String getXPathClass(String className) throws AimException {
        String classNameLow = className.toLowerCase(new Locale("\\u0131"));
        String temp = "";
        String path = this.hashClasses.get(classNameLow);
        if (path == null) {
            throw new AimException("AimException: AimQL Syntax Error, " + className + " is not a valid AIM Class.");
        }
        String[] array = path.split("/");
        if ("ImageAnnotation".equals(array[0])) {
            temp = this.hashClasses.get(classNameLow);
        } else {
            temp += getXPathClass(array[0]) + "/" + this.hashClasses.get(classNameLow);
        }
        String[] control = temp.split("/");
        List<String> listClasses = new ArrayList<String>();
        for (int i = 0; i < control.length; i++) {
            if (listClasses.indexOf(control[i]) < 0) {
                listClasses.add(control[i]);
            }
        }
        String res = "";
        for (int i = 0; i < listClasses.size(); i++) {
            if (i < listClasses.size() - 1) {
                res += listClasses.get(i) + "/";
            } else {
                res += listClasses.get(i);
            }
        }
        return res;
    }

    public String getAttribute(String attribute) throws AimException {
        if (this.hashAttributes.get(attribute.toLowerCase(new Locale("\\u0131"))) == null) {
            throw new AimException("AimException: AimQL Syntax Error, " + attribute + " is not a valid AIM Attribute.");
        }
        if (this.hashAttributes.get(attribute.toLowerCase(new Locale("\\u0131"))) != null) {
            return this.hashAttributes.get(attribute.toLowerCase(new Locale("\\u0131")));
        } else {
            return attribute.toLowerCase(new Locale("\\u0131"));
        }
    }
}
