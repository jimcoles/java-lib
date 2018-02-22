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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Preferred logging api is
 * <p>
 * Log.info(this, message)
 *
 * @version 1.0
 */

public class LogHelper {
    private static String defaultWebConfigFile = "/WEB-INF/classes/log4j.xml";

    /**
     * Set property expected by Log4j static init logic.
     */
    public static void init(String configFileName) {
        System.setProperty("log4j.configuration", configFileName);
    }

    public static void init() {
        init("log4j.xml");
    }

    public static void initForWeb() {
        init(defaultWebConfigFile);
    }

    private static void _defaultInit() {
//        BasicConfigurator.resetConfiguration();
//        BasicConfigurator.configure();
    }


    /**
     * Private constructor. Does nothing.
     */
    private LogHelper() {
    }

    public static Log getLogger(Object o) {
        if (o != null) {
            return getLogger(o.getClass());
        }
        return null;
    }

    public static Log getLogger(Class clazz) {
        return LogFactory.getLog(clazz);
    }

    public static Log getLogger(String name) {
        return LogFactory.getLog(name);
    }

    public static void debug(Object cat, Object msg) {
        if (assertNotNull(cat)) {
            getLogger(cat).debug(msg);
        }
    }

    public static void fatal(Object cat, Object msg, Throwable t) {
        if (assertNotNull(cat)) {
            getLogger(cat).fatal(msg, t);
        }
    }

    public static void fatal(Object cat, Object msg) {
        if (assertNotNull(cat)) {
            getLogger(cat).fatal(msg);
        }
    }

    public static void error(Object cat, Object msg, Throwable t) {
        if (assertNotNull(cat)) {
            getLogger(cat).error(msg, t);
        }
    }

    public static void error(Object cat, Object msg) {
        if (assertNotNull(cat)) {
            getLogger(cat).error(msg);
        }
    }

    public static void info(Object cat, Object msg) {
        if (assertNotNull(cat)) {
            getLogger(cat).info(msg);
        }
    }

    public static void warn(Object cat, Object msg) {
        if (assertNotNull(cat)) {
            getLogger(cat).warn(msg);
        }
    }

    public static void warn(Object cat, Object msg, Throwable t) {
        if (assertNotNull(cat)) {
            getLogger(cat).warn(msg, t);
        }
    }

    private static boolean assertNotNull(Object obj) {
        boolean result = true;
        if (obj == null) {
            LogHelper.error(LogHelper.class, "You must supply a non-null value for logger category object.");
            result = false;
        }
        return result;
    }
}
