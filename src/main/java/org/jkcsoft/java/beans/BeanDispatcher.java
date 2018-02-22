/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2018 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.beans;

/*
 *
 */

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * BeanDispatcher is a class for getting and setting and invoking methods on a
 * JavaBean dynamically at runtime. Its useful for writing re-usable code such
 * as translating a data between JavaBean, XML, JDBC, etc.
 * <br><br>
 * It uses the Java reflection api (java.lang.reflect.*) and java.beans.* to
 * dispatch calls into the bean.
 * <br><br>
 * Recommended usage is to create one BeanDispatcher per class of JavaBean.
 * A good place to store the helper is as a static
 * private member of the bean class itself.  There is some overhead in creating
 * a BeanDispatcher so do not create one every time you need to make a call.
 * <p>
 * Source: This is a variation of RoseBeanProxy by Thornton Rose and also
 * "Java 2 performance and idiom guide" [larman,guthrie] ch 6.4.
 *
 * @author Jim Coles
 * @version 1.0
 */
public class BeanDispatcher implements IDispatcher {
    //---------------------------------------------------------------------------
    // Private instance vars
    //---------------------------------------------------------------------------
    private Class _beanClass;
    private Map _propertyDescriptorsByName = new HashMap();

    //---------------------------------------------------------------------------
    // Constructor(s)
    //---------------------------------------------------------------------------

    /**
     * Constructs a dispatcher for the given class.
     *
     * @param theBeanClass The target bean class.
     * @throws IntrospectionException if the specified class is not a valid
     *                                JavaBean.
     */
    public BeanDispatcher(Class theBeanClass)
            throws IntrospectionException {
        BeanInfo bi;
        PropertyDescriptor[] pds;
        String name;

        // Save reference to bean class.
        _beanClass = theBeanClass;

        // Build Map for bean property descriptors.
        bi = Introspector.getBeanInfo(_beanClass);
        pds = bi.getPropertyDescriptors();
        for (int i = 0; i < pds.length; i++) {
            name = pds[i].getName();
            _propertyDescriptorsByName.put(name, pds[i]);
        }
    }

    //---------------------------------------------------------------------------
    // Public instance methods
    //---------------------------------------------------------------------------

    /**
     * Get bean property.
     *
     * @param name Bean property name.
     * @return Bean property value as Object.
     */
    public Object get(Object bean, String name) throws Exception {
        PropertyDescriptor pd;
        Method getter;

        pd = (PropertyDescriptor) _propertyDescriptorsByName.get(name);

        if (pd == null) {
            throw new NoSuchFieldException("Unknown property: " + name);
        }

        getter = pd.getReadMethod();
        if (getter == null) {
            throw new NoSuchMethodException("No read method for: " + name);
        }

        return getter.invoke(bean, new Object[]{});
    }

    /**
     * Set bean property via reflection API.
     *
     * @param name  Bean property name.
     * @param value Bean property value.
     */
    public Object set(Object bean, String name, Object value)
            throws Exception {
        PropertyDescriptor pd;
        Method setter;

        pd = (PropertyDescriptor) _propertyDescriptorsByName.get(name);
        if (pd == null) throw new NoSuchFieldException("Unknown property: " + name);

        setter = pd.getWriteMethod();
        if (setter == null) throw new NoSuchMethodException("No write method for: " + name);

        return setter.invoke(bean, new Object[]{value});
    }

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
//       throws Exception {
//    return _beanClass.getMethod(name, types).invoke(bean, parameters);
//  }
}
