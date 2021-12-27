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
import org.junit.Assert;
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

    @Test
    public void testStringIndices() {
        String tester = "1234568890";
        System.out.println("our test string is ["+tester+"]");
        testSubString(tester, 0, 0);
        testSubString(tester, 0, 1);
        testSubString(tester, 0, 2);
        testSubString(tester, 0, 3);
    }

    private void testSubString(String tester, int beginIndex, int endIndex) {
        System.out.println("substring of [" + tester + "] with args (" + beginIndex + "," + endIndex + ") => [" +
                               tester.substring(beginIndex, endIndex) + "]");
    }

    @Test
    public void testMultStrings() {
        testMultString("X", 5);
        testMultString("X", 3);
    }

    private void testMultString(String baseStr, int num) {
        log("test: expect " + num + " " + baseStr + " => " + Strings.multiplyString(baseStr, num));
    }

    @Test
    public void testStringsReplace() {
        String template = "place 0 {0}, place 1 {1}, place {2}";
        String replaced = Strings.replace(template, "zero", "one", "two");
        Assert.assertEquals("expect string replacement", "place 0 zero, place 1 one, place two", replaced);
        log("replaced string: " + replaced);
    }

    private void log(String msg) {
        System.out.println(msg);
    }
}
