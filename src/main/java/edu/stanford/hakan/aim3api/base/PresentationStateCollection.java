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
public class PresentationStateCollection implements IAimXMLOperations {

    private List<PresentationState> listPresentationState = new ArrayList<PresentationState>();

    public void addPresentationState(PresentationState newPresentationState) {
        this.listPresentationState.add(newPresentationState);
    }

    public List<PresentationState> getPresentationStateList() {
        return this.listPresentationState;
    }

    @Override
    public Node getXMLNode(Document doc) throws AimException {

        Element presentationStateCollection = doc.createElement("presentationStateCollection");
        for (int i = 0; i < listPresentationState.size(); i++) {
            presentationStateCollection.appendChild(listPresentationState.get(i).getXMLNode(doc));
        }

        return presentationStateCollection;
    }

    @Override
    public void setXMLNode(Node node) {

        this.listPresentationState.clear();

        NodeList tempList = node.getChildNodes();
        for (int j = 0; j < tempList.getLength(); j++) {
            if ("PresentationState".equals(tempList.item(j).getNodeName())) {
                PresentationState obj = new PresentationState();
                obj.setXMLNode(tempList.item(j));
                this.addPresentationState(obj);
            }
        }
    }

    @Override
    public Node getRDFNode(Document doc, String unquieID, String Prefix) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void appendNodes(Document doc, String unquieID, Node parentNode, String Prefix) throws AimException {
        for (int i = 0; i < listPresentationState.size(); i++) {
            Element eHas = doc.createElement(Prefix + "hasPresentationState");
            eHas.appendChild(listPresentationState.get(i).getRDFNode(doc, unquieID.concat("_").concat(Integer.toString(i + 1)), Prefix));
            parentNode.appendChild(eHas);
        }
    }
    
    public boolean isEqualTo(Object other) {
        PresentationStateCollection oth = (PresentationStateCollection) other;
        if (this.listPresentationState.size() != oth.listPresentationState.size()) {
            return false;
        }
        for (int i = 0; i < this.listPresentationState.size(); i++) {
            if (!this.listPresentationState.get(i).isEqualTo(oth.listPresentationState.get(i))) {
                return false;
            }
        }
        return true;
    }
}
