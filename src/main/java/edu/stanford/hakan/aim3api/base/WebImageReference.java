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

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

/**
 *
 * @author Hakan BULU
 */
public class WebImageReference extends ImageReference implements IAimXMLOperations {

    private String uri;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public WebImageReference() {
        setXsiType("WebImageReference");
    }

    public WebImageReference(Integer cagridId, String uri) {
        setCagridId(cagridId);
        setXsiType("WebImageReference");
        this.uri = uri;
    }

    @Override
    public Node getXMLNode(Document doc) throws AimException {
        
        this.Control();

        Element imageReference = (Element) super.getXMLNode(doc);
        imageReference.setAttribute("uri", this.getUri());
        return imageReference;
    }

    @Override
    public void setXMLNode(Node node) {
        super.setXMLNode(node);

        NamedNodeMap map = node.getAttributes();
        this.uri = map.getNamedItem("uri").getNodeValue();
    }

    @Override
    public Node getRDFNode(Document doc, String unquieID, String Prefix) throws AimException {

        this.Control();
        
        Element eWebImageReference = (Element) super.getRDFNode(doc, unquieID, Prefix);

        Element eUri = doc.createElement(Prefix + "uri");
        eUri.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        eUri.setTextContent(this.uri.toString());
        eWebImageReference.appendChild(eUri);

        return eWebImageReference;
    }

    private void Control() throws AimException {

        if (this.getUri() == null) {
            throw new AimException("AimException: WebImageReference's uri can not be null");
        }
    }
    
            
    @Override
    public boolean isEqualTo(Object other) {
        WebImageReference oth = (WebImageReference) other;
        if (this.uri == null ? oth.uri != null : !this.uri.equals(oth.uri)) {
            return false;
        }       
        return super.isEqualTo(other);
    }
}
