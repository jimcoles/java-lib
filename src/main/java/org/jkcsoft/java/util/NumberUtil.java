package org.jkcsoft.java.util;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

import java.text.NumberFormat;
import java.text.DecimalFormat;
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
    throws ParseException
  {
    Number number = null;
    number = _numFormat.parse(numString);
    return number;
  }

}