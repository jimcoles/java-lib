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

import org.jkcsoft.java.util.Strings;

import java.util.List;
import java.util.Vector;

/**
 * Adds type checking and synchronization over and above basic
 * java.util.List.
 */
public class PropertyList extends Vector implements List {
    // slightly redudant cache of String list. Lazy init in getListAsString().
    private String _strList = null;


    public PropertyList() {
    }

    public boolean containsProp(IPropMeta o) {
        return this.contains(o);
    }

    public boolean addProp(IPropMeta o) {
        return this.add(o);
    }

    public boolean removeProp(IPropMeta o) {
        return this.remove(o);
    }

    public PropertyListIterator propIterator() {
        return new PropertyListIterator(this.iterator());
    }

    /**
     * Return comma delimited list (no parentheses).
     */
    public String getColListAsString() {
        if (_strList == null) {
            _strList = Strings.buildCommaDelList(this, PropColLister.INSTANCE);
        }
        return _strList;
    }
}