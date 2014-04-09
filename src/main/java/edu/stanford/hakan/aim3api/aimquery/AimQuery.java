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
import java.util.List;
import java.util.Locale;

/**
 *
 * @author Hakan
 */
public class AimQuery {

    public static String convertToXQuery(String aimQuery, String nameSpace) throws AimException {
        QueryParser myParser = new QueryParser(aimQuery);
        String collectionName = myParser.getAimCollection();
        String whereClause = myParser.getWhereClause();
        List<QueryExpression> listExp = myParser.getListExpressions();
        for (int i = 0; i < listExp.size(); i++) {
            QueryExpression exp = listExp.get(i);
            String expression = exp.getExpression();
            String xQuery = exp.getxQuery();
            whereClause = whereClause.replace(expression, xQuery);
        }

        //whereClause += " and ($x[fn:string-length(@aimVersion) < 10] or $x[contains(lower-case(@aimVersion), lower-case('current')]))";
        //whereClause += " and $x[ends-with(lower-case(@aimVersion),'aim.3.0')]";
        //whereClause += " and $x[string-length(@aimVersion) < 100]";

        //*** to be sure, user does not want to query aimVersion field and this is not valuable while querying  the uniqueIdentifier
        if (whereClause.toLowerCase(new Locale("\\u0131")).indexOf("aimVersion".toLowerCase(new Locale("\\u0131"))) < 0 && whereClause.toLowerCase(new Locale("\\u0131")).indexOf("$x[lower-case(@uniqueIdentifier) = lower-case('".toLowerCase(new Locale("\\u0131"))) != 0) {
            whereClause += " and ($x[not(contains(@aimVersion,'^'))] or $x[contains(lower-case(@aimVersion),lower-case('current'))])";
        }

        if (!"/".equals(collectionName.substring(0, 1))) {
            collectionName = "/" + collectionName;
        }
        String res = "declare default element namespace '" + nameSpace + "'; for $x in collection('" + collectionName + "')/ImageAnnotation where " + whereClause + " return $x";
        return res.replaceAll(" (?i)and ", " and ").replaceAll(" (?i)or ", " or ");
    }
}
