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
        testMultString("X", 5, null);
        testMultString("X", 3, null);
        testMultString("X", 5, "-");
    }

    private void testMultString(String baseStr, int num, String multiPrefix) {
        log("test: expect " + num + " " + baseStr + " => " + Strings.multiplyString(baseStr, num, multiPrefix));
    }
    
    @Test
    public void testStringFormatter() {
        String template = "place 0 {0}, place 1 {1}, place {2}";
        String replaced = Strings.fmt(template, "zero", "one", "two");
        Assert.assertEquals("expect string replacement", "place 0 zero, place 1 one, place two", replaced);
        log("replaced string: " + replaced);
    }
    
    @Test
    public void testFormatterMemoization() {
        String template = "place 0 {0}, place 1 {1}, place {2}";
        String fmt1 = Strings.fmt(template, "zero", "one", "two");
        String fmt2 = Strings.fmt(template, "zero", "one", "two");
        log("replaced string: " + fmt1);
    }
    
    public static class Widget {
        int id;
        String name;
    
        public Widget(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }
    
    @Test
    public void testLister() {
        List<Widget> widgets = List.of(new Widget(1, "fred"), new Widget(2, "dingo"));
        String widgetList = Strings.buildDelList(
            widgets,
            widget -> "[" + widget.id + "|" + widget.name + "]",
            ",");
        log("widget list: " + widgetList);
    }

    private void log(String msg) {
        System.out.println(msg);
    }
}
