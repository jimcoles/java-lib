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
 * @author J. Coles
 * @version 1.2
 */

import java.util.Comparator;

/**
 *
 */
public final class Comparators {
    public static final Comparator COMP_LONG = new Comparator() {
        // -1 ==> o1 is less than o2
        // =  equal
        // 1  ==> o1 is greater than o2
        public int compare(Object o1, Object o2) {
            int retVal = 0;
            long diff = ((Long) o1).longValue() - ((Long) o2).longValue();
            if (diff < 0)
                retVal = -1;
            else if (diff == 0)
                retVal = 0;
            else
                retVal = 1;
            return retVal;
        }
    };

    public static final Comparator COMP_INTEGER = new Comparator() {
        // -1 ==> o1 is less than o2
        // =  equal
        // 1  ==> o1 is greater than o2
        public int compare(Object o1, Object o2) {
            int retVal = 0;
            long diff = ((Integer) o1).longValue() - ((Integer) o2).longValue();
            if (diff < 0)
                retVal = -1;
            else if (diff == 0)
                retVal = 0;
            else
                retVal = 1;
            return retVal;
        }
    };

    public static final Comparator COMP_STRING_IGNORE_CASE = java.lang.String.CASE_INSENSITIVE_ORDER;

}
