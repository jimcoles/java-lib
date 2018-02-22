/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2018 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */
package org.jkcsoft.java.util;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.XMLConfiguration;

import java.io.IOException;
import java.util.*;

/**
 * Wraps a commons.configuration.Configuration object via delegation.
 * <p>
 * Primary value-add is
 * - delegated defaulting
 * - validation.
 * - logging.
 * <p>
 * TODO: Add config file version upon .save( );
 *
 * @author Jim Coles
 * @version 1.0
 */
public class ConfigHelper extends AbstractConfiguration {

    //----------------------------------------------------------------------------
    // Instance vars
    //----------------------------------------------------------------------------
    private Configuration config;
    private Configuration defaultConfig;

    //----------------------------------------------------------------------------
    // Constructor(s)
    //----------------------------------------------------------------------------
    public ConfigHelper(XMLConfiguration config, XMLConfiguration defaultConfig) throws Exception {
        if (config == null) throw new Exception("Null config object");
        if (defaultConfig == null) throw new Exception("Null default config object");

        this.config = config;
        this.defaultConfig = defaultConfig;

        try {
            LogHelper.info(this, "Using editable config file [" + getConfFilePath(config) + "] and " +
                    "default [" + getConfFilePath(defaultConfig) + "]");
        } catch (Exception e) {
            LogHelper.error(this, "Could not get config file path info ==> ", e);
        }
    }

    //----------------------------------------------------------------------------
    // Instance methods
    //----------------------------------------------------------------------------

    /**
     * @param config
     */
    public String getConfFilePath(XMLConfiguration config) {
        String path = "null config";
        if (config != null) {
            if (config.getFile() != null) {
                try {
                    path = config.getFile().getCanonicalPath();
                } catch (IOException e) {
                    path = e.getMessage();
                }
            } else if (config.getURL() != null) {
                path = config.getURL().toString();
            }
        }
        return path;
    }

    //----------------------------------------------------------------------------
    // Instance methods
    //----------------------------------------------------------------------------
    public static boolean validateRequiredProperty(Configuration pConfig, String propName, List errors) {
        boolean valid = false;
        try {
            Object value = pConfig.getProperty(propName);
            valid = true;
        } catch (NoSuchElementException e) {
            String msg = "Missing configuration property [" + propName + "]";
            errors.add(msg);
            valid = false;
        }
        return valid;
    }


    protected Object getPropertyDirect(String key) {
        return getProperty(key);
    }

    /* (non-Javadoc)
    * @see org.apache.commons.configuration.AbstractConfiguration#addPropertyDirect(java.lang.String, java.lang.Object)
    */
    protected void addPropertyDirect(String key, Object value) {
        // TODO Auto-generated method stub
        config.addProperty(key, value);
    }

    /* (non-Javadoc)
     * @see org.apache.commons.configuration.AbstractConfiguration#containsKey(java.lang.String)
     */
    public boolean containsKey(String key) {
        return getProperty(key) != null;
    }

    /* (non-Javadoc)
     * @see org.apache.commons.configuration.AbstractConfiguration#getKeys()
     */
    public Iterator getKeys() {
        return defaultConfig.getKeys();
    }

    /**
     * Apply customized defaulting logic.
     *
     * @see org.apache.commons.configuration.AbstractConfiguration#getProperty(java.lang.String)
     */
    public Object getProperty(String key) {
        Object value = null;
        value = config.getProperty(key);
        if (value == null) {
            value = defaultConfig.getProperty(key);
            if (value != null) {
                LogHelper.info(this, "Using default value [" + value + "] for [" + key + "]");
            } else {
                LogHelper.warn(this, "No default value for [" + key + "]");
            }
        }
        return value;
    }

    /* (non-Javadoc)
     * @see org.apache.commons.configuration.AbstractConfiguration#isEmpty()
     */
    public boolean isEmpty() {
        return config.isEmpty() && defaultConfig.isEmpty();
    }

    /* (non-Javadoc)
     * @see org.apache.commons.configuration.AbstractConfiguration#clearProperty(java.lang.String)
     */
    public void clearProperty(String key) {
        // TODO Auto-generated method stub
        config.clearProperty(key);
    }

    public Configuration getEditableConfig() {
        return config;
    }


    public Configuration getDefaultConfig() {
        return defaultConfig;
    }

    public Properties getProperties() {
        Properties props = null;

        Set keysSet = new TreeSet();
        addKeys(keysSet, config.getKeys());
        addKeys(keysSet, defaultConfig.getKeys());

        if (keysSet.size() > 0) {
            props = new Properties();
            for (Iterator keyIter = keysSet.iterator(); keyIter.hasNext(); ) {
                String key = (String) keyIter.next();
                props.put(key, getString(key));
            }
        }
        return props;
    }

    private void addKeys(Set keysSet, Iterator keysIter) {
        while (keysIter.hasNext()) {
            String key = (String) keysIter.next();
            keysSet.add(key);
        }
    }
}
