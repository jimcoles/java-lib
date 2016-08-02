/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2016. through present.
 *
 * Licensed under the following license agreement:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.beans;

/*
 * Title:
 * Desc:
 *
 */

/**
 * A generic interface for getting and setting bean properties.
 *
 * @author Jim Coles
 * @version 1.0
 */
public interface IDispatcher {
    /**
     * Get bean property.
     *
     * @param name Bean property name.
     * @return Bean property value as Object.
     */
    public Object get(Object bean, String name) throws Exception;

    /**
     * Set bean property via reflection API.
     *
     * @param name  Bean property name.
     * @param value Bean property value.
     */
    public Object set(Object bean, String name, Object value) throws Exception;

    /**
     * Invoke named method on target bean.
     *
     * @param name       Method name.
     * @param types      Parameter types.
     * @param parameters List of parameters passed to method.
     *
     * @return Return value from method (may be null).
     *
     * @throws Throwable When any exception occurs.
     */
//  public Object invoke(Object bean, String name, Class[] types, Object[] parameters)
//       throws Exception;
}
