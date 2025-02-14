/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2018 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

/*
 *
 */
package org.jkcsoft.java.util;

import org.apache.commons.logging.Log;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Various helpers for Bean operations.  Uses Introspection.
 *
 * @author Jim Coles
 */
public class Beans {
    private static final Log log = LogHelper.getLogger(Beans.class);
    
    public static List toPropertyCollection(Collection objectCollection, String propName)
        throws Exception
    {
        List retColl = null;
        if (objectCollection != null) {
            retColl = new Vector();
            Iterator iter = objectCollection.iterator();
            while (iter.hasNext()) {
                retColl.add(get(iter.next(), propName));
            }
        }
        return retColl;
    }
    
    public static Map toMapByProperty(Collection objectCollection, String propName)
        throws Exception
    {
        Map retMap = null;
        if (objectCollection != null) {
            retMap = new HashMap();
            Iterator iter = objectCollection.iterator();
            while (iter.hasNext()) {
                Object item = iter.next();
                if (item != null)
                    retMap.put(get(item, propName), item);
            }
        }
        return retMap;
    }
    
    /**
     * Returns true if collection of beans contains at least one bean where the value of
     * property propName equals the supplied value.  Should work for Integer and String
     * properties without the need for Comparator argument.
     *
     * @param objectCollection
     * @param propName
     * @param value
     * @return
     * @throws Exception
     */
    public static boolean contains(Collection objectCollection, String propName, Object value)
        throws Exception
    {
        boolean bContains = false;
        if (objectCollection != null) {
            Iterator iter = objectCollection.iterator();
            while (iter.hasNext()) {
                Object o = iter.next();
                if (get(o, propName).equals(value)) {
                    bContains = true;
                    break;
                }
            }
        }
        return bContains;
    }
    
    /**
     * Returns a single bean with specified value for keyPropName.
     *
     * @param objectCollection
     * @param keyPropName
     * @param value
     * @return
     * @throws Exception
     */
    public static <T> T selectByKey(Collection<T> objectCollection, String keyPropName, Object value)
        throws Exception
    {
        T theBean = null;
        if (objectCollection != null) {
            Iterator<T> iter = objectCollection.iterator();
            while (iter.hasNext()) {
                T o = iter.next();
                if (get(o, keyPropName).equals(value)) {
                    theBean = o;
                    break;
                }
            }
        }
        return theBean;
    }
    
    public static <T> Collection<T> select(Collection<T> objectCollection, Predicate<T> filter) {
        return objectCollection.stream().filter(filter).collect(Collectors.toList());
    }
    
    public static Object get(Object bean, String propName)
        throws Exception
    {
        PropertyDescriptor pd = getPropertyDescriptor(bean, propName);
        
        if (pd == null) {
            throw new NoSuchFieldException("Unknown property: " + propName);
        }
        
        return get(bean, pd);
    }
    
    public static Object get(final Object bean, final PropertyDescriptor pd)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        Method getter = pd.getReadMethod();
        if (getter == null) {
            throw new NoSuchMethodException("No read method for: " + pd.getName());
        }
        
        return getter.invoke(bean);
    }
    
    public static void set(Object bean, String propName, Object value)
        throws Exception
    {
        PropertyDescriptor pd = getPropertyDescriptor(bean, propName);
        
        if (pd == null) {
            throw new NoSuchFieldException("Unknown property: " + propName);
        }
        
        set(bean, pd, value);
    }
    
    public static void set(final Object bean, final PropertyDescriptor pd, final Object value)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        Method setter = pd.getWriteMethod();
        if (setter == null) {
            throw new NoSuchMethodException("No write method for: " + pd.getName());
        }
        
        setter.invoke(bean, value);
    }
    
    public static PropertyDescriptor getPropertyDescriptor(Object bean, String propName) throws IntrospectionException {
        BeanInfo bi = Introspector.getBeanInfo(bean.getClass());
        PropertyDescriptor pds[] = bi.getPropertyDescriptors();
        PropertyDescriptor pd = null;
        for (int i = 0; i < pds.length; i++) {
            if (propName.equalsIgnoreCase(pds[i].getName())) {
                pd = pds[i];
                break;
            }
        }
        return pd;
    }
    
    /**
     * Doesn't work yet!
     *
     * @param bean
     */
    public static void cleanBean(Object bean) {
        if (bean == null) return;
        
        try {
            BeanInfo bi = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor pds[] = bi.getPropertyDescriptors();
            PropertyDescriptor pd = null;
            for (int i = 0; i < pds.length; i++) {
                pd = pds[i];
                //Method getter = pd.getReadMethod();
                Method setter = pd.getWriteMethod();
                if (pd.getPropertyType() == Integer.class) {
                    setter.invoke(bean, 0);
                }
                else if (pd.getPropertyType() == Double.class) {
                    setter.invoke(bean, 0);
                }
                else {
                    try {
                        setter.invoke(bean, new Object[]{null});
                    }
                    catch (Throwable e) {
                        log.warn("cleanBean()", e);
                    }
                }
            }
        }
        catch (Throwable e) {
            log.warn("cleanBean()", e);
        }
    }
    
    public static PropertyDescriptor[] getPropertyDescriptors(Class<?> clazz) {
        PropertyDescriptor[] pds = null;
        try {
            BeanInfo bi = Introspector.getBeanInfo(clazz);
            pds = bi.getPropertyDescriptors();
        }
        catch (Exception ex) {
            log.error(ex);
        }
        return pds;
    }
    
    public static String toBeanString(Object bean) {
        StringBuilder sb = new StringBuilder(Strings.restAfterLast(bean.getClass().getName(), "."));
        
        PropertyDescriptor[] pds = getPropertyDescriptors(bean.getClass());
        
        for (int idxPd = 0; idxPd < pds.length; idxPd++) {
            PropertyDescriptor pd = pds[idxPd];
            if (pd.getPropertyType().isPrimitive() ||
                pd.getPropertyType().equals(Long.class) ||
                pd.getPropertyType().equals(Integer.class) ||
                pd.getPropertyType().equals(String.class)) {
                try {
                    sb.append(" [" + pd.getName() + "] " + get(bean, pd.getName()));
                }
                catch (Exception e) {
                    sb.append(" [" + pd.getName() + "] " + e.getMessage());
                }
            }
        }
        
        return sb.toString();
    }
}

