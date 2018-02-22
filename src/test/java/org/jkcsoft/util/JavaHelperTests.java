/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2018 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.util;

import org.jkcsoft.java.util.JavaHelper;
import org.junit.Test;

/**
 * @author Jim Coles
 */
public class JavaHelperTests {

    @Test
    public void testStackTrace() {
        System.out.println("this methods's stack info: " + JavaHelper.getCallerStackInfo());
    }

}
