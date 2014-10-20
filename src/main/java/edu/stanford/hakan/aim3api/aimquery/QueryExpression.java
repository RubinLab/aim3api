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
import java.util.Locale;

/**
 *
 * @author Hakan
 */
public class QueryExpression {

    private String expression = "";
    private String className = "";
    private String attributeName = "";
    private String function = "";
    private String value = "";
    private String xQuery = "";

    public QueryExpression(String expression, String className, String attributeName, String function, String value) throws AimException {
        this.expression = expression;
        this.className = className;
        this.attributeName = attributeName;
        this.function = function;
        this.value = value;
        this.xQuery = this.generateXQuery();
    }

    public String getClassName() {
        return className;
    }

    public String getExpression() {
        return expression;
    }

    public String getFunction() {
        return function;
    }

    public String getValue() {
        return value;
    }

    public String getxQuery() {
        return xQuery;
    }

    public String getAttributeName() {
        return attributeName;
    }

    private String generateXQuery() throws AimException {
        QueryMapping qMap = new QueryMapping();
        String xClass = qMap.getXPathClass(className).replace("ImageAnnotation", "$x");
        String xAttribute = "";
        if ("<>".equals(function.trim())) {
            function = "!=";
        }
        
//        if(!"<>".equals(function.trim()) && !"=".equals(function.trim()) && !"<=".equals(function.trim()) && !">=".equals(function.trim()) && !"like".equals(function.trim().toLowerCase(new Locale("\\u0131"))))
//            throw new AimException("AimException: " + function + " is not defined function.");
        
        String xFunction = function;
        if ("like".equals(function.toLowerCase(new Locale("\\u0131")).trim())) {
            if ((value.indexOf("%") < 0) || (value.indexOf("'%") >= 0 && value.indexOf("%'") >= 0)) {
                xAttribute = "[contains(lower-case(@" + qMap.getAttribute(attributeName) + "),lower-case(" + value + "))]";
            } else if (value.indexOf("'%") >= 0) {
                xAttribute = "[starts-with(lower-case(@" + qMap.getAttribute(attributeName) + "),lower-case(" + value + "))]";
            } else if (value.indexOf("%'") >= 0) {
                xAttribute = "[ends-with(lower-case(@" + qMap.getAttribute(attributeName) + "),lower-case(" + value + "))]";
            } else {
                xAttribute = "[contains(lower-case(@" + qMap.getAttribute(attributeName) + "),lower-case(" + value + "))]";
            }
        } else if (value.trim().startsWith("'")) {
            if("uniqueIdentifier".equals(attributeName))
            xAttribute = "[@" + qMap.getAttribute(attributeName) + " " + function + " " + value + "]";
                else
            xAttribute = "[lower-case(@" + qMap.getAttribute(attributeName) + ") " + function + " lower-case(" + value + ")]";
        } else {
            xAttribute = "[@" + qMap.getAttribute(attributeName) + " " + function + " " + value + "]";
        }

        return xClass + xAttribute.replace("%", "");
    }
}
