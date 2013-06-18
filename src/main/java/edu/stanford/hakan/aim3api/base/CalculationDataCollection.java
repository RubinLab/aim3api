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
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Hakan BULU
 */
public class CalculationDataCollection implements IAimXMLOperations {

    private List<CalculationData> listCalculationData = new ArrayList<CalculationData>();

    public void AddCalculationData(CalculationData newCalculationData) {
        this.listCalculationData.add(newCalculationData);
    }

    public List<CalculationData> getCalculationDataList() {
        return this.listCalculationData;
    }

    @Override
    public Node getXMLNode(Document doc) throws AimException {

        Element calculationDataCollection = doc.createElement("calculationDataCollection");
        for (int i = 0; i < this.listCalculationData.size(); i++) {
            calculationDataCollection.appendChild(this.listCalculationData.get(i).getXMLNode(doc));
        }
        return calculationDataCollection;
    }

    @Override
    public void setXMLNode(Node node) {

        this.listCalculationData.clear();

        NodeList tempList = node.getChildNodes();
        for (int j = 0; j < tempList.getLength(); j++) {
            if ("CalculationData".equals(tempList.item(j).getNodeName())) {
                CalculationData obj = new CalculationData();
                obj.setXMLNode(tempList.item(j));
                this.AddCalculationData(obj);
            }
        }
    }

    @Override
    public Node getRDFNode(Document doc, String unquieID,String Prefix) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void appendNodes(Document doc, String unquieID, Node parentNode,String Prefix) throws AimException {
        for (int i = 0; i < listCalculationData.size(); i++) {
            Element eHas = doc.createElement(Prefix.concat("hasCalculationData"));
            eHas.appendChild(listCalculationData.get(i).getRDFNode(doc, unquieID.concat("_").concat(Integer.toString(i + 1)),Prefix));
            parentNode.appendChild(eHas);
            
        }
    }
    
        
    public boolean isEqualTo(Object other) {
        CalculationDataCollection oth = (CalculationDataCollection) other;
        if (this.listCalculationData.size() != oth.listCalculationData.size()) {
            return false;
        }
        for (int i = 0; i < this.listCalculationData.size(); i++) {
            if (!this.listCalculationData.get(i).isEqualTo(oth.listCalculationData.get(i))) {
                return false;
            }
        }
        return true;
    }
}
