/**
 * This work was created by participants in the DataONE project, and is
 * jointly copyrighted by participating institutions in DataONE. For
 * more information on DataONE, see our web site at http://dataone.org.
 *
 *   Copyright ${year}
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * $Id$
 */
package org.dataone.eml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.dataone.eml.EMLDocument.DistributionMetadata;
import org.dataone.service.types.ObjectFormat;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author berkley
 * This class parses EML documents to pull any relevant D1 information
 * for systemMetadata creation and other purposes.
 */
public class DataoneEMLParser
{
    private static DataoneEMLParser parser = null;
    
    /**
     * private constructor.  To create a new parser, use getInstance().
     */
    private DataoneEMLParser()
    {
        
    }
    
    /**
     * singleton accessor
     * @return a parser to parse EML documents
     */
    public static DataoneEMLParser getInstance()
    {
        if(parser == null)
        {
            parser = new DataoneEMLParser();
        }
        return parser;
    }
    
    /**
     * parse an eml document and return any distribution urls
     * @param is
     * @throws XPathExpressionException 
     */
    public EMLDocument parseDocument(InputStream is)
        throws ParserConfigurationException, IOException, SAXException, XPathExpressionException
    {
        //info we need:
        //1) any distribution urls
        //2) doctype (public_id)
        //3) mime type for each 1
        EMLDocument doc = new EMLDocument();
        
        System.out.println("parsing EML document for any distribution urls");
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        
        Document d = builderFactory.newDocumentBuilder().parse(is);
        String namespace = d.getFirstChild().getNamespaceURI();
        System.out.println("namespace: " + namespace);
        
        //switch on the namespace
        if(namespace.equals(ObjectFormat.EML_2_0_0.toString()))
        {
            return parseEML200Document(d);
        }
        else if(namespace.equals(ObjectFormat.EML_2_0_1.toString()))
        {
            return parseEML201Document(d);
        }
        else if(namespace.equals(ObjectFormat.EML_2_1_0.toString()))
        {
            return parseEML210Document(d);
        }
        else
        {
            throw new ParserConfigurationException(
                    "This parser only parses EML 2.0.0, 2.0.1 and 2.1.0.  Namespace " + 
                    namespace + " is not supported.");
        }
    }
    
    private NodeList runXPath(String expression, Node n)
      throws XPathExpressionException
    {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression expr = xpath.compile(expression);
        NodeList result = (org.w3c.dom.NodeList)expr.evaluate(n, XPathConstants.NODESET);
        return result;
    }
    
    /**
     * parse an EML 2.0.0 document
     * @param d
     * @return
     * @throws XPathExpressionException 
     */
    private EMLDocument parseEML200Document(Document d) throws XPathExpressionException
    {
        System.out.println("Parsing an EML 2.0.0 document.");
        return parseEMLDocument(d, ObjectFormat.EML_2_0_0);
    }
    
    /**
     * parse an EML 2.0.1 document
     * @param d
     * @return
     */
    private EMLDocument parseEML201Document(Document d) throws XPathExpressionException
    {
        System.out.println("Parsing an EML 2.0.1 document.");
        return parseEMLDocument(d, ObjectFormat.EML_2_0_1);
    }
    
    /**
     * parse and EML 2.1.0 document
     * @param d
     * @return
     */
    private EMLDocument parseEML210Document(Document d) throws XPathExpressionException
    {
        System.out.println("Parsing an EML 2.1.0 document.");
        return parseEMLDocument(d, ObjectFormat.EML_2_1_0);
    }
    
    private EMLDocument parseEMLDocument(Document d, ObjectFormat docType) throws XPathExpressionException
    {
        EMLDocument emld = new EMLDocument();
        NodeList result = runXPath("//distribution", d);        
        
        for (int i = 0; i < result.getLength(); i++) 
        {
            String url = runXPath("online/url", result.item(i)).item(0).getFirstChild().getNodeValue();
            String mimeType = "";
            Node physicalNode = result.item(i).getParentNode();
            NodeList nl1 = runXPath("dataFormat/textFormat", physicalNode);
            NodeList nl2 = runXPath("dataFormat/binaryRasterFormat", physicalNode);
            NodeList nl3 = runXPath("dataFormat/externallyDefinedFormat", physicalNode);
            //TODO: this isn't entirely right, but it's a good start.  Need to 
            //figure out how to property parse the EML to get a better idea
            //of what the mime type is
            if(nl1.getLength() > 0)
            { //found a text format
                mimeType = ObjectFormat.TEXT_PLAIN.toString();
            }
            else if(nl2.getLength() > 0)
            {
                mimeType = "application/octet-stream";
            }
            else if(nl3.getLength() > 0)
            {
                mimeType = "application/octet-stream";
            }
            
            System.out.println("mime type: " + mimeType); 
            System.out.println("url: " + url);
            emld.addDistributionMetadata(url, mimeType);
        }
        
        emld.setObjectFormat(docType);
        System.out.println("document type: " + emld.format.toString());
        
        return emld;
    }
}
