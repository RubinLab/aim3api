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
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

/**
 *
 * @author Hakan BULU
 */
public class CharacteristicQuantificationCollection implements IAimXMLOperations {

    private List<CharacteristicQuantification> listCharacteristicQuantification = new ArrayList<CharacteristicQuantification>();

    public CharacteristicQuantificationCollection() {
    }

    public void AddCharacteristicQuantification(CharacteristicQuantification newCharacteristicQuantification) {
        this.listCharacteristicQuantification.add(newCharacteristicQuantification);
    }

    public List<CharacteristicQuantification> getCharacteristicQuantificationList() {
        return this.listCharacteristicQuantification;
    }

    @Override
    public Node getXMLNode(Document doc) throws AimException {

        Element characteristicQuantificationCollection = doc.createElement("characteristicQuantificationCollection");
        for (int i = 0; i < this.listCharacteristicQuantification.size(); i++) {
            characteristicQuantificationCollection.appendChild(this.listCharacteristicQuantification.get(i).getXMLNode(doc));
        }

        return characteristicQuantificationCollection;
    }

    @Override
    public void setXMLNode(Node node) {
        this.listCharacteristicQuantification.clear();
        NodeList tempList = node.getChildNodes();
        for (int j = 0; j < tempList.getLength(); j++) {
            Node currentNode = tempList.item(j);
            if ("CharacteristicQuantification".equals(currentNode.getNodeName())) {

                NamedNodeMap map = currentNode.getAttributes();
                String xsiType = map.getNamedItem("xsi:type").getNodeValue();
                if ("Quantile".equals(xsiType)) {
                    Quantile obj = new Quantile();
                    obj.setXMLNode(currentNode);
                    this.AddCharacteristicQuantification(obj);
                } else if ("Scale".equals(xsiType)) {
                    Scale obj = new Scale();
                    obj.setXMLNode(currentNode);
                    this.AddCharacteristicQuantification(obj);
                } else if ("Numerical".equals(xsiType)) {
                    Numerical obj = new Numerical();
                    obj.setXMLNode(currentNode);
                    this.AddCharacteristicQuantification(obj);
                } else if ("NonQuantifiable".equals(xsiType)) {
                    NonQuantifiable obj = new NonQuantifiable();
                    obj.setXMLNode(currentNode);
                    this.AddCharacteristicQuantification(obj);
                } else if ("Interval".equals(xsiType)) {
                    Interval obj = new Interval();
                    obj.setXMLNode(currentNode);
                    this.AddCharacteristicQuantification(obj);
                }
            }
        }
    }

    @Override
    public Node getRDFNode(Document doc, String unquieID, String Prefix) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void appendNodes(Document doc, String unquieID, Node parentNode, String Prefix) throws AimException {
        for (int i = 0; i < listCharacteristicQuantification.size(); i++) {
            Element eHas = doc.createElement(Prefix + "hasCharacteristicQuantification");
            eHas.appendChild(listCharacteristicQuantification.get(i).getRDFNode(doc, unquieID.concat("_").concat(Integer.toString(i + 1)), Prefix));
            parentNode.appendChild(eHas);
        }
    }

    public boolean isEqualTo(Object other) {
        CharacteristicQuantificationCollection oth = (CharacteristicQuantificationCollection) other;
        if (this.listCharacteristicQuantification.size() != oth.listCharacteristicQuantification.size()) {
            return false;
        }
        for (int i = 0; i < this.listCharacteristicQuantification.size(); i++) {
            if (!this.listCharacteristicQuantification.get(i).isEqualTo(oth.listCharacteristicQuantification.get(i))) {
                return false;
            }
        }
        return true;
    }

    public edu.stanford.hakan.aim4api.base.CharacteristicQuantificationCollection toAimV4() {
        edu.stanford.hakan.aim4api.base.CharacteristicQuantificationCollection res = new edu.stanford.hakan.aim4api.base.CharacteristicQuantificationCollection();
        List<edu.stanford.hakan.aim3api.base.CharacteristicQuantification> list = this.getCharacteristicQuantificationList();
        for (edu.stanford.hakan.aim3api.base.CharacteristicQuantification itemV3 : list) {
            res.addCharacteristicQuantification(itemV3.toAimV4());
        }
        return res;
    }

    public CharacteristicQuantificationCollection(edu.stanford.hakan.aim4api.base.CharacteristicQuantificationCollection v4) {
        List<edu.stanford.hakan.aim4api.base.CharacteristicQuantification> listV4 = v4.getCharacteristicQuantificationList();
        for (edu.stanford.hakan.aim4api.base.CharacteristicQuantification itemV4 : listV4) {
            if ("Interval".equals(itemV4.getXsiType())) {
                this.AddCharacteristicQuantification(new Interval((edu.stanford.hakan.aim4api.base.Interval) itemV4));
            } else if ("NonQuantifiable".equals(itemV4.getXsiType())) {
                this.AddCharacteristicQuantification(new NonQuantifiable((edu.stanford.hakan.aim4api.base.NonQuantifiable) itemV4));
            } else if ("Numerical".equals(itemV4.getXsiType())) {
                this.AddCharacteristicQuantification(new Numerical((edu.stanford.hakan.aim4api.base.Numerical) itemV4));
            } else if ("Quantile".equals(itemV4.getXsiType())) {
                this.AddCharacteristicQuantification(new Quantile((edu.stanford.hakan.aim4api.base.Quantile) itemV4));
            } else if ("Scale".equals(itemV4.getXsiType())) {
                this.AddCharacteristicQuantification(new Scale((edu.stanford.hakan.aim4api.base.Scale) itemV4));
            }
        }
    }
}
