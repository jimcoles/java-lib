/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2023 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.annos.xpack;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Jim Coles
 */
public class PackageUtils {
    
    
    public static class DefaultPackageHandler implements XPackHandler {
        
        private Map<Package, List<Class<?>>> packageClasses = new HashMap<>();
        private List<Class<?>> allClasses = new LinkedList<>();
        
        @Override
        public void handle(List<Class> packageClasses) {
        
        }
    }
}
