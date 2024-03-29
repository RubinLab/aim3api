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
public class AnatomicEntityCollection implements IAimXMLOperations {

    private List<AnatomicEntity> listAnatomicEntity = new ArrayList<AnatomicEntity>();

    public AnatomicEntityCollection()
    {
    }
    
    public void AddAnatomicEntity(AnatomicEntity newAnatomicEntity) {
        this.listAnatomicEntity.add(newAnatomicEntity);
    }

    public List<AnatomicEntity> getAnatomicEntityList() {
        return this.listAnatomicEntity;
    }

    @Override
    public Node getXMLNode(Document doc) throws AimException {

        Element anatomicEntityCollection = doc.createElement("anatomicEntityCollection");
        for (int i = 0; i < this.listAnatomicEntity.size(); i++) {
            anatomicEntityCollection.appendChild(this.listAnatomicEntity.get(i).getXMLNode(doc));
        }

        return anatomicEntityCollection;
    }

    @Override
    public void setXMLNode(Node node) {

        this.listAnatomicEntity.clear();

        NodeList tempList = node.getChildNodes();
        for (int j = 0; j < tempList.getLength(); j++) {
            if ("AnatomicEntity".equals(tempList.item(j).getNodeName())) {
                AnatomicEntity obj = new AnatomicEntity();
                obj.setXMLNode(tempList.item(j));
                this.AddAnatomicEntity(obj);
            }
        }
    }

    @Override
    public Node getRDFNode(Document doc, String unquieID, String Prefix) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void appendNodes(Document doc, String unquieID, Node parentNode, String Prefix) throws AimException {
        for (int i = 0; i < listAnatomicEntity.size(); i++) {
            Element eHas = doc.createElement(Prefix + "hasAnatomicEntity");
            eHas.appendChild(listAnatomicEntity.get(i).getRDFNode(doc, unquieID.concat("_").concat(Integer.toString(i + 1)), Prefix));
            parentNode.appendChild(eHas);
        }
    }

    public boolean isEqualTo(Object other) {
        AnatomicEntityCollection oth = (AnatomicEntityCollection) other;
        if (this.listAnatomicEntity.size() != oth.listAnatomicEntity.size()) {
            return false;
        }
        for (int i = 0; i < this.listAnatomicEntity.size(); i++) {
            if (!this.listAnatomicEntity.get(i).isEqualTo(oth.listAnatomicEntity.get(i))) {
                return false;
            }
        }
        return true;
    }

    public edu.stanford.hakan.aim4api.base.ImagingPhysicalEntityCollection toAimV4() {
        edu.stanford.hakan.aim4api.base.ImagingPhysicalEntityCollection res = new edu.stanford.hakan.aim4api.base.ImagingPhysicalEntityCollection();
        List<edu.stanford.hakan.aim3api.base.AnatomicEntity> list = this.getAnatomicEntityList();
        for (edu.stanford.hakan.aim3api.base.AnatomicEntity itemV3 : list) {
            res.addImagingPhysicalEntity(itemV3.toAimV4());
        }
        return res;
    }

    public AnatomicEntityCollection(edu.stanford.hakan.aim4api.base.ImagingPhysicalEntityCollection v4) {
        List<edu.stanford.hakan.aim4api.base.ImagingPhysicalEntity> listV4 = v4.getImagingPhysicalEntityList();
        for (edu.stanford.hakan.aim4api.base.ImagingPhysicalEntity itemV4 : listV4) {
            this.AddAnatomicEntity(new AnatomicEntity(itemV4));
        }
    }

}
