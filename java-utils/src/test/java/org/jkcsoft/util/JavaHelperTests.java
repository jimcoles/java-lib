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
import java.util.Collections;

/**
 * @author Jim Coles
 */
public class JavaHelperTests {

//    @Test
    public void testStackTrace() {
        System.out.println("this methods's stack info: " + JavaHelper.getCallerStackInfo());
    }
//    @Test
    public void testStackTrace1() {
        System.out.println("a nice exception trace:");
        Exception exception = new Exception();
        exception.printStackTrace();
    }

//    @Test
    public void testPrimCasts() {
        double myDoub = 2.3;
        int myInt = (int) myDoub;
        System.out.println("myInt after cast: " + myInt);
        Double myBoxDoub = myDoub;
        Integer myBoxInt = (int) myBoxDoub.doubleValue();
    }

//    @Test
    public void testArrayTypes() {
        int[][] ints = new int[][] {{1, 2, 3}};
        double doubles[] = new double[3];
        String strings[] = new String[3];
        MyClass myClasss[] = new MyClass[3];
        printStuff(ints);
        printStuff(doubles);
        printStuff(strings);
        printStuff(myClasss);
    }

    void printStuff(Object obj) {
        Class<?> aClass = obj.getClass();
        System.out.println("name for array => " + aClass.getName());
        System.out.println("simple name for array => " + aClass.getSimpleName());
        System.out.println("cann name for array => " + aClass.getCanonicalName());
        System.out.println("is array: " + aClass.isArray());
        System.out.println("is prim: " + aClass.isPrimitive());
        System.out.println("is synth: " + aClass.isSynthetic());
    }

    public static class MyClass {
        public int intVal;
    }

    @Test
    public void testArrayPassing() {
        int[] ints = {1, 2, 3};
        takeInts(ints);
        System.out.println((Arrays.toString(ints)));
        System.out.println(ints);
    }

    void takeInts(int[] intsArg) {
        System.out.println(intsArg);
        intsArg[1]=999;
    }
}
