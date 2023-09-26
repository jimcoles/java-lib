/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2018 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */
package org.jkcsoft.java.testing;

import junit.framework.TestCase;
import org.jkcsoft.java.util.LogHelper;
import org.jkcsoft.java.util.Strings;

import java.text.MessageFormat;

/**
 * @author coles
 */
public abstract class BaseTestCase extends TestCase {

    protected void setUp() throws Exception {
//    	Log.init("log4j.xml");
        LogHelper.init();
    }

    public void handleException(Throwable ex) {
        LogHelper.error(this, "Last-ditch exception caught", ex);
        fail("Last-ditch handler");
    }

    public void out(String fmt, Object ... args) {
        System.out.println(Strings.fmt(fmt, args));
    }
    
}
