/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2018 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

public class NumberUtil {
    //----------------------------------------------------------------------------
    // Instance vars
    //----------------------------------------------------------------------------
    private String _strNumFormat = null;
    private NumberFormat _numFormat = null;

    //----------------------------------------------------------------------------
    // Constructor(s)
    //----------------------------------------------------------------------------
    public NumberUtil(String strNumFormat) {
        _strNumFormat = strNumFormat;
        _numFormat = new DecimalFormat(strNumFormat);
    }

    //----------------------------------------------------------------------------
    // Public methods
    //----------------------------------------------------------------------------

    /**
     * Converts a String to a Number, using the specified pattern.
     * (see java.text.NumberFormat for pattern description)
     *
     * @param numString the String to convert
     * @return the corresponding Number
     * @exception ParseException, if the String doesn't match the pattern
     */
    public Number toNumber(String numString)
            throws ParseException {
        Number number = null;
        number = _numFormat.parse(numString);
        return number;
    }

}