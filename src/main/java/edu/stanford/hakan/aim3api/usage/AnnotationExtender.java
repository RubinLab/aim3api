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
package edu.stanford.hakan.aim3api.usage;

import edu.stanford.hakan.aim3api.base.AimException;
import edu.stanford.hakan.aim3api.base.AimUtility.CalculationResultIdentifier;
import edu.stanford.hakan.aim3api.base.Calculation;
import edu.stanford.hakan.aim3api.base.CalculationData;
import edu.stanford.hakan.aim3api.base.CalculationResult;
import edu.stanford.hakan.aim3api.base.Dimension;
import edu.stanford.hakan.aim3api.base.ImageAnnotation;

/**
 *
 * @author Hakan BULU
 */
public class AnnotationExtender {
    
     public static ImageAnnotation addFeature(ImageAnnotation imageAnnotation, double[] featureValue, String[] featureString, double featureVersion) throws AimException {
        if (featureValue.length != featureString.length) {
            throw new AimException("AimException: lenght of featureValue and featureString must be equal");
        }
        
        Calculation calculation = new Calculation();
        calculation.setCagridId(0);
        calculation.setAlgorithmVersion(Double.toString(featureVersion));
        calculation.setUid("0");
        calculation.setDescription("description");
        calculation.setCodeValue("codeValue");
        calculation.setCodeMeaning("codeMeaning");
        calculation.setCodingSchemeDesignator("codingSchemeDesignator");

        for (int i = 0; i < featureValue.length; i++) {
            // Create a CalculationResult instance
            CalculationResult calculationResult = new CalculationResult();
            calculationResult.setCagridId(0);
            calculationResult.setType(CalculationResultIdentifier.Scalar);
            calculationResult.setUnitOfMeasure("ratio");
            calculationResult.setNumberOfDimensions(1);
            // Create a CalculationData instance
            CalculationData calculationData = new CalculationData();
            calculationData.setCagridId(0);
            calculationData.setValue(featureValue[i]);
            calculationData.addCoordinate(0, 0, 0);
            // Create a Dimension instance
            Dimension dimension = new Dimension(0, 0, 1, featureString[i]);
            // Add calculationData to calculationResult
            calculationResult.addCalculationData(calculationData);
            // Add dimension to calculationResult
            calculationResult.addDimension(dimension);
            // Add calculationResult to calculation
            calculation.addCalculationResult(calculationResult);
        }

        imageAnnotation.addCalculation(calculation);
        return imageAnnotation;
    }
     
}
