/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2016. through present.
 *
 * Licensed under the following license agreement:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.xml;

import org.apache.commons.jxpath.JXPathContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

public class XMLUtil {
    //---------------------------------------------------------------------------
    // Static vars and static inits
    //---------------------------------------------------------------------------

    private static Transformer xtrans = null; // generic xsl transformer

    static {
        try {
            xtrans = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException ex) {
            ex.printStackTrace();
        }
    }

    //---------------------------------------------------------------------------
    // Public static methods
    //---------------------------------------------------------------------------

    /**
     * Gets an xml Document that is retrieved via an HTTP GET request.
     */
    public static Document getDocument(String strUrl, DocumentBuilder builder)
            throws Exception {
        // use XML parser using a java.net.url to act as http transmitter.
        URL url = new URL(strUrl);
        InputStream in = null;
        String strResponse = null;
        Document doc = null;
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        try {
            // TODO: Check for type of content to ensure InputStream and text/xml,
            //       as expected.
            in = (InputStream) conn.getContent();
            try {
                if (builder == null) throw new Exception("null parser from pool!");
                try {
                    doc = builder.parse(new InputSource(in));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } finally {
                in.close();
            }
        } finally {
            conn.disconnect();
        }
        return doc;
    }

    /**
     * Get Child Element Text.  Good when you have the following:
     * <Object>
     * <Property1>value1</Property1>
     * <Property2>value2</Property2>
     * </Object>
     * In effect, obj is the node for an object or row of data and
     * property is the name of the property.
     */
    public static String getCET(Node obj, String property, String ifnull)
            throws Exception {
        String retVal = null;
        JXPathContext jxp = JXPathContext.newContext(obj);
        Text text = (org.w3c.dom.Text) jxp.getValue(property);
        if (text == null) {
//     throw new Exception("Property not found: " + property);
            retVal = ifnull;
        } else {
            retVal = text.getData();
        }
        if (retVal == null) retVal = ifnull;
        return retVal;
    }

    public static String getText(Element elem) {
        return ((Text) elem.getFirstChild()).getData();
    }

    public static String getText(Element elem, String ifnull) {
        String retVal = null;
        Node node = elem.getFirstChild();
        if (node != null) {
            if (node.getNodeType() == Node.TEXT_NODE) {
                retVal = ((Text) node).getData();
            } else {
                System.out.println("Invalid element type in getText().");
            }
        } else {
            retVal = ifnull;
        }
        return retVal;
    }

    public static String domToString(Node node, int estSize)
            throws Exception {
        String retVal = null;
        if (node != null) {
            StringWriter sw = new StringWriter(estSize);
            xtrans.transform(new DOMSource(node), new StreamResult(sw));
            retVal = sw.toString();
            sw.close();
        }
        return retVal;
    }

    public static void domToString(Node node, Writer out)
            throws Exception {
        if (node != null) {
            xtrans.transform(new DOMSource(node), new StreamResult(out));
        }
        return;
    }
}