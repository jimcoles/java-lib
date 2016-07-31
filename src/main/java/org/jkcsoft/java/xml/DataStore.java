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

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import java.io.File;

public class DataStore {
    //------------------------------------------------------------------------
    // Constants
    //------------------------------------------------------------------------
    public static final String PARM_STOREDIR = "datastore-dir";

    //------------------------------------------------------------------------
    // Statics
    //------------------------------------------------------------------------
    private static DataStore _instance = null;

    public static DataStore getInstance()
            throws Exception {
        if (_instance == null) {
            _instance = new DataStore("datastore");
        }
        return _instance;
    }

    //------------------------------------------------------------------------
    // Instance vars
    //------------------------------------------------------------------------
    private boolean _bInit = false;
    private String _storeDir = null;

    //------------------------------------------------------------------------
    // Constructor(s)
    //------------------------------------------------------------------------
    private DataStore(String storeDir) throws Exception {
        _storeDir = storeDir;
        _bInit = true;
    }

    //------------------------------------------------------------------------
    // Public methods
    //------------------------------------------------------------------------

    /**
     * Returns the org.w3c.dom.Document for the named XML datasource.
     * This class does not cache the document so consuming objects should
     * do any caching if appropriate.  This method should not be called
     * every time a data consumer needs data from the data store.
     */
    public Document getDoc(String docName)
            throws Exception {
        DocumentBuilder parser = _getNewParser();
        String fullpath = _storeDir + File.separator + docName;
        return parser.parse(fullpath);
    }

    //------------------------------------------------------------------------
    // Private methods
    //------------------------------------------------------------------------

    private DocumentBuilder _getNewParser()
            throws Exception {
        DocumentBuilder retDB = null;
        // TODO: Decide how this re-usable class should determine which
        //       DocumentBuilderFactory to use.
        return retDB;
    }
}