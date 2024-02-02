/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2023 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.util;

import org.jkcsoft.java.testing.BaseTestCase;
import org.jkcsoft.java.util.Reflects;
import org.junit.Test;

import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

import static org.jkcsoft.java.testing.TestUtils.out;

/**
 * @author Jim Coles
 */
public class ReflectsTests extends BaseTestCase {
    
    @Test
    public void testReflectMethods () {
        Map<String, Method> mcMethods = Reflects.getMethodsMap(MyClass.class);
        MyClass mc = new MyClass("", 123, Date.from(Instant.now()));
        mcMethods.values().forEach(meth -> {
            out("mapped method: ", meth);
        });
    }
    
    public static class MyClass {
        
        private String fieldOne;
        private int fieldTwo;
        private Date fieldTrey;
        
        public MyClass(final String fieldOne, final int fieldTwo, final Date fieldTrey) {
            this.fieldOne = fieldOne;
            this.fieldTwo = fieldTwo;
            this.fieldTrey = fieldTrey;
        }
        
        public String getFieldOne() {
            return fieldOne;
        }
        
        public int getFieldTwo() {
            return fieldTwo;
        }
        
        public Date getFieldTrey() {
            return fieldTrey;
        }
    }
}
