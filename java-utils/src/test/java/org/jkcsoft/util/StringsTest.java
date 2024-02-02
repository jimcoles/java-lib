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

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.jkcsoft.java.testing.BaseTestCase;
import org.jkcsoft.java.util.JavaHelper;
import org.jkcsoft.java.util.Strings;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.jkcsoft.java.util.Strings.*;

/**
 *
 * @author Jim Coles
 */
public class StringsTest extends BaseTestCase {

    public void testListsToStrings() {
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
        // basic replacer logic
        String template = "place 0 {0}, place 1 {1}, place {2}";
        String replaced = fmt(template, "zero", "one", "two");
        Assert.assertEquals("expect string replacement", "place 0 zero, place 1 one, place two", replaced);
        log("replaced string: " + replaced);
        
        // put in a bad format string -> expect no errors
        testBadFormat("this has a stray single quote ' ");
        testBadFormat("this has sl4j style braces w/o number {}");
        testBadFormat("this has mismatched number of args {0}", "one", "two");
        testBadFormat("this has bad arg index {2}", "one");
        
    }

    private void testBadFormat(String badTemplate, Object ... args) {
        try {
            String result = fmt(badTemplate, args);
            log("==> tested template: " + badTemplate);
            log("==>          output: " + result);
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Strings.fmt() should never throw an exception and " +
                            "should respond with the supplied template.");
        }
        
    }
    
    @Test
    public void testFormatterMemoization() {
        String template = "place 0 {0}, place 1 {1}, place {2}";
        String fmt1 = fmt(template, "zero", "one", "two");
        String fmt2 = fmt(template, "zero", "one", "two");
        log("replaced string: " + fmt1);
    }
    
    public static class Widget {
        int id;
        String name;
    
        public Widget(int id, String name) {
            this.id = id;
            this.name = name;
        }
        
        @Override
        public String toString() {
            return "Widget{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
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
    
    @Test
    public void testMapLister() {
        Map<String, String> map = Map.of("7", "Jim", "1", "Wally", "3", "Dingo");
        
        log("map string: " + mapToString(map));
        
        log("map string: " + mapToString(map, ", "));
        
        Map<UUID, Widget> okeyMap = Map.of(UUID.randomUUID(), new Widget(9, "dingo"), UUID.randomUUID(), new Widget(3, "flange"));
        
        log("complex keyed map string: " + mapToString(okeyMap));
        
    }
    
    @Test
    public void testLimiter() {
        var inStr = "1234567890";
        var limitedStr = Strings.limit(inStr, 5);
        log(fmt("in string [{0}]. limited string [{1}]", inStr, limitedStr));
        Assert.assertEquals("string limit", limitedStr, "12345..");
    }

    public void testEmptyPredicates() {
        Assert.assertTrue(Strings.isEmpty(null));
        Assert.assertTrue(Strings.isEmpty(""));
        Assert.assertFalse(Strings.isEmpty("abc"));
        Assert.assertTrue(Strings.isEmpty("\n"));   // empty cuz defaults to trim which rems lead/trail new-line
        Assert.assertTrue(Strings.isNotEmpty("\n", false)); // not empty if we don't trim
    }
    
    private void log(String msg) {
        System.out.println(msg);
    }
}
