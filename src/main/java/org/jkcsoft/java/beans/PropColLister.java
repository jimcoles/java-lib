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

import org.jkcsoft.java.util.Lister;

public class PropColLister implements Lister {
    public static Lister INSTANCE = new PropColLister();

    private PropColLister() {
    }

    public String getListString(Object obj) {
        if (!(obj instanceof IPropMeta)) throw new IllegalArgumentException("Need object type of .beans.IPropMeta");
        IPropMeta pm = (IPropMeta) obj;
        return pm.getColName();
    }
}