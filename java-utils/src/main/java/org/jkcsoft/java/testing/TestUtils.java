/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2023 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.testing;

import static org.jkcsoft.java.util.Strings.fmt;

/**
 * @author Jim Coles
 */
public class TestUtils {
    
    public static void out(String fmt, Object... args) {
        System.out.println(fmt(fmt, args));
    }
    
}
