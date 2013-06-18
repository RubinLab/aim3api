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
@SuppressWarnings("serial")
public class AnatomicEntityCharacteristicCollection implements IAimXMLOperations {

    private List<AnatomicEntityCharacteristic> listAnatomicEntityCharacteristic = new ArrayList<AnatomicEntityCharacteristic>();

    public void AddAnatomicEntityCharacteristic(AnatomicEntityCharacteristic newAnatomicEntityCharacteristic) {
        this.listAnatomicEntityCharacteristic.add(newAnatomicEntityCharacteristic);
    }

    public List<AnatomicEntityCharacteristic> getAnatomicEntityCharacteristicList() {
        return this.listAnatomicEntityCharacteristic;
    }

    @Override
    public Node getXMLNode(Document doc) throws AimException {

        Element anatomicEntityCharacteristicCollection = doc.createElement("anatomicEntityCharacteristicCollection");
        for (int i = 0; i < this.listAnatomicEntityCharacteristic.size(); i++) {
            anatomicEntityCharacteristicCollection.appendChild(this.listAnatomicEntityCharacteristic.get(i).getXMLNode(doc));
        }

        return anatomicEntityCharacteristicCollection;
    }

    @Override
    public void setXMLNode(Node node) {

        this.listAnatomicEntityCharacteristic.clear();
        NodeList tempList = node.getChildNodes();
        for (int j = 0; j < tempList.getLength(); j++) {
            if ("AnatomicEntityCharacteristic".equals(tempList.item(j).getNodeName())) {
                AnatomicEntityCharacteristic obj = new AnatomicEntityCharacteristic();
                obj.setXMLNode(tempList.item(j));
                this.AddAnatomicEntityCharacteristic(obj);
            }
        }
    }

    @Override
    public Node getRDFNode(Document doc, String unquieID, String Prefix) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void appendNodes(Document doc, String unquieID, Node parentNode, String Prefix) throws AimException {
        for (int i = 0; i < listAnatomicEntityCharacteristic.size(); i++) {
            Element eHas = doc.createElement(Prefix + "hasAnatomicEntityCharacteristic");
            eHas.appendChild(listAnatomicEntityCharacteristic.get(i).getRDFNode(doc, unquieID.concat("_").concat(Integer.toString(i + 1)), Prefix));
            parentNode.appendChild(eHas);
        }
    }
    
    public boolean isEqualTo(Object other) {
        AnatomicEntityCharacteristicCollection oth = (AnatomicEntityCharacteristicCollection) other;
        if (this.listAnatomicEntityCharacteristic.size() != oth.listAnatomicEntityCharacteristic.size()) {
            return false;
        }
        for (int i = 0; i < this.listAnatomicEntityCharacteristic.size(); i++) {
            if (!this.listAnatomicEntityCharacteristic.get(i).isEqualTo(oth.listAnatomicEntityCharacteristic.get(i))) {
                return false;
            }
        }
        return true;
    }
}
