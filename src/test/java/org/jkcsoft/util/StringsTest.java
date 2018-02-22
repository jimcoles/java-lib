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
import org.jkcsoft.java.util.Strings;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Jim Coles
 */
public class StringsTest {

    @Test
    public void testStrings() {
        List<String> stringList = Arrays.asList("a", "b", "c");
        System.out.println("List of abc with comma: " + JavaHelper.EOL
                + Strings.buildCommaDelList(stringList));
        System.out.println("List of abc with newline: " + JavaHelper.EOL
                + Strings.buildNewlineList(stringList));
    }

}
