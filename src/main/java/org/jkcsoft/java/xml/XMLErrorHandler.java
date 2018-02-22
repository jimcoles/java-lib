/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2018 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.xml;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XMLErrorHandler implements ErrorHandler {
    public void warning(SAXParseException ex) {
        store(ex, "[Warning]");
    }

    public void error(SAXParseException ex) {
        store(ex, "[Error]");
    }

    public void fatalError(SAXParseException ex) throws SAXException {
        store(ex, "[Fatal Error]");
    }

    void store(SAXParseException ex, String type) {
        // build error text
        String errorString = type + " at line number, " + ex.getLineNumber()
                + ": " + ex.getMessage() + "\n";
        System.out.println(errorString);
    }
}