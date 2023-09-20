/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2018 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.annos.xpack.test;

import org.jkcsoft.annos.xpack.XPackHandler;
import org.junit.Test;

import java.util.List;

/**
 * @author Jim Coles
 */
public class TestXpackHandler implements XPackHandler {
    
    public static void main(String[] args) {
    
    }
    @Test
    public void testPackageAnno() {
        // should trigger annotations
    }
    
    @Override
    public void handle(List<Class> packageClasses) {
        System.out.println("package classes" + packageClasses);
    }
}
