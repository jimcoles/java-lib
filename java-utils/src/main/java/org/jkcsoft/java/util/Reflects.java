/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2023 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jim Coles
 */
public class Reflects {
    
    private static Map<Class<?>, Map<String, Method>> classMethodsMap = new HashMap<>();
    
    public static Map<String, Method> getMethodsMap(Class<?> clazz) {
        return classMethodsMap.computeIfAbsent(clazz, (refClass) -> {
            Map<String, Method> methodsMap = new HashMap<>();
            for (Method method : clazz.getMethods()) {
                methodsMap.put(method.getName(), method);
            }
            return methodsMap;
        });
    }
    
}
