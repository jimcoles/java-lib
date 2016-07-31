/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2016. through present.
 *
 * Licensed under the following license agreement:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.util;

/**
 * Author:  J. Coles
 * Version 1.2
 */
public interface ILister {
    /**
     * Like java.lang.Object.toString(), except returns a string suitable for
     * human reading.
     */
    public String getListString(Object obj);
}
