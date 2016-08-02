package org.jkcsoft.java.util;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 *
 * @author
 * @version 1.0
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Numbers {
    //----------------------------------------------------------------------------
    // Static vars
    //----------------------------------------------------------------------------
    private static Map _numUtils = new HashMap(5);

    //----------------------------------------------------------------------------
    // Public static methods
    //----------------------------------------------------------------------------

    /**
     * Acts as object factory for NumberUtil's as keyed off of the number
     * format strNumFormat.  This way we never build more than one number
     * formatter for a given format string.
     */
    public static NumberUtil getNumUtil(String strNumFormat) {
        NumberUtil retVal = null;
        if (_numUtils.containsKey(strNumFormat)) {
            retVal = (NumberUtil) _numUtils.get(strNumFormat);
        } else {
            retVal = new NumberUtil(strNumFormat);
            _numUtils.put(strNumFormat, retVal);
        }
        return retVal;
    }

    /**
     *
     */
    public static String[] intArrayToStringArray(int[] intArr) {
        String[] strArr = null;
        if (intArr != null && intArr.length > 0) {
            strArr = new String[intArr.length];
            for (int i = 0; i < intArr.length; i++) {
                strArr[i] = String.valueOf(intArr[i]);
            }
        }
        return strArr;
    }

    /**
     *
     */
    public static int[] stringArrayToIntArray(String[] strArr) {
        int[] retInts = null;
        Vector vec = new Vector();
        if (strArr != null) {
            for (int i = 0; i < strArr.length; i++) {
                try {
                    vec.add(Integer.valueOf(strArr[i]));
                } catch (NumberFormatException e) {
                    // if bad input, a letter for instance then just try the next elem.
                }
            }
        }
        if (vec.size() > 0) {
            retInts = new int[vec.size()];
            for (int i = 0; i < vec.size(); i++) {
                retInts[i] = ((Integer) vec.elementAt(i)).intValue();
            }
        }
        return retInts;
    }

    public static String arrayToString(long[] items) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < items.length; i++)
            buffer = buffer.append(items[i] + (i == items.length - 1 ? "" : ","));
        return buffer.toString();
    }

    /**
     * Returns true if the specified number string represents a valid
     * integer in the specified range.
     *
     * @param numberString a String representing an integer
     * @param min the minimal value in the valid range
     * @param min the maximal value in the valid range
     * @return true if valid, false otherwise
     */
    public static boolean isValidInteger(String strNum, int min, int max) {
        boolean retVal = false;
        try {
            int iNum = Integer.parseInt(strNum);
            if (iNum >= min && iNum <= max) {
                retVal = true;
            }
        } catch (NumberFormatException e) {
            // Ignore and return null
        }
        return retVal;
    }
}