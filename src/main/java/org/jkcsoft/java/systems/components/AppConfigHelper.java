/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2016. through present.
 *
 * Licensed under the following license agreement:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */
package org.jkcsoft.java.systems.components;

import org.apache.commons.logging.Log;
import org.jkcsoft.java.util.LogHelper;
import org.jkcsoft.java.util.Strings;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Takes property key of the form '/abc/xyz'.  E.g., 'db/url'
 * <p>
 * Basic idea is to give all the various objects within an app a nice interface to
 * get configuration data such as URLs to RDB servers, smtp servers, etc.  Should hide
 * complexity and nature of underlying data store which might be any of the following:
 * <p>
 * - XML Files
 * - .properties file
 * - JNDI service
 * <p>
 * Usage: Any collaborating group of objects (in an application, web server, Junit suite, etc)
 * should create one AppConfigHelper upon initialization.  Make that object available as a
 * singleton.
 * <p>
 * theApp -> init config source( ) -> AppConfigHelper
 * <p>
 * anObj -> theApp.getConfigVal(key) -> theAppConfigHelper.getConfigProperty(key)
 * <p>
 * TODO: Return a 'Config' interface from init() methods and enable use of various Config
 * impl classes for each type of underlying datastore.
 *
 * @author Jim Coles
 * @version 1.0
 */
public class AppConfigHelper {
    private static Log log = LogHelper.getLogger(AppConfigHelper.class);

    private String _propFileName;
    private ResourceBundle bundle;

    //----------------------------------------------------------------------------
    // Private instance vars
    //----------------------------------------------------------------------------
    //----------------------------------------------------------------------------
    // Constructor(s) (private, package, protected, public)
    //----------------------------------------------------------------------------

    /**
     * Properties file -based config
     */
    public AppConfigHelper(String propFileName) {
        _propFileName = propFileName;
        ResourceBundle bundle = ResourceBundle.getBundle(propFileName);
    }

    //----------------------------------------------------------------------------
    // Public methods - accessors, mutators, other
    //----------------------------------------------------------------------------
    public String getConfigProperty(String propKeySlashes, String defValue)
            throws Exception {
        if (Strings.isEmpty(propKeySlashes)) throw new Exception("Null or empty key argument");

        // trim forward and leading '/'
        String propKey = propKeySlashes;
        if (propKey.charAt(0) == '/') propKey = propKey.substring(1);
        if (propKey.charAt(propKey.length() - 1) == '/') propKey = propKey.substring(0, propKey.length() - 2);
        propKey = propKey.replace('/', '.');

        String retVal = defValue;
        try {
            if (bundle != null) {
                retVal = bundle.getString(propKey);
                if (retVal == null || Strings.isEmpty(retVal)) {
                    throw new MissingResourceException("", "", propKey);
                }
                log.info("Found property ==> " + propKey + "=" + retVal);
            }
        } catch (MissingResourceException e) {
            if (defValue == null) {
                log.error("Initialization property not found for key '" + propKey + "' with no default value", e);
                throw e;
            }
        }
        if (retVal == null) {
            if (defValue != null) {
                retVal = defValue;
                log.info("Initialization property not found '" + propKey + "'; using default '" + defValue + "'");
            } else {
                throw new Exception("Initialization property not found for key '" + propKey + "' with no default value");
            }
        }
        return retVal;
    }


    //----------------------------------------------------------------------------
    // Private methods
    //----------------------------------------------------------------------------

}
