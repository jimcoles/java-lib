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

import java.util.Iterator;

public class PropertyListIterator {
    //---------------------------------------------------------------------------
    //---------------------------------------------------------------------------
    private Iterator _iter = null;

    //---------------------------------------------------------------------------
    //---------------------------------------------------------------------------
    public PropertyListIterator(Iterator iter) {
        _iter = iter;
    }

    //---------------------------------------------------------------------------
    //---------------------------------------------------------------------------
    public boolean hasNext() {
        return _iter.hasNext();
    }

    public IPropMeta next() {
        return (IPropMeta) _iter.next();
    }
}