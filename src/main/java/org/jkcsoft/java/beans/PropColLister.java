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

import org.jkcsoft.java.util.ILister;

public class PropColLister implements ILister {
    public static ILister INSTANCE = new PropColLister();

    private PropColLister() {
    }

    public String getListString(Object obj) {
        if (!(obj instanceof IPropMeta)) throw new IllegalArgumentException("Need object type of .beans.IPropMeta");
        IPropMeta pm = (IPropMeta) obj;
        return pm.getColName();
    }
}