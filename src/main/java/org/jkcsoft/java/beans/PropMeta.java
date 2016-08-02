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

/**
 * Generic impl for IPropMeta.
 *
 * @author Jim Coles
 * @version 1.0
 */
public class PropMeta implements IPropMeta
{
  //---------------------------------------------------------------------------
  //---------------------------------------------------------------------------
  private String  _progId     = null;
  private String  _colName    = null;
  private int     _colType       = -1;
  private boolean _isRequired = false;
  private boolean _isPK       = false;

  //---------------------------------------------------------------------------
  //---------------------------------------------------------------------------
  public PropMeta(String progId, String colName, boolean isRequired)
  {
    _progId = progId;
    _colName = colName;
    _isRequired = isRequired;
  }

  //---------------------------------------------------------------------------
  //---------------------------------------------------------------------------
  public String getProgId() { return _progId; }
  public String getColName() { return _colName; }
  public int getColType() { return _colType; }
  public boolean isRequired() { return _isRequired; }
  public boolean isPK() { return _isPK; }

  public void setPK(boolean isPK)
  {
    _isPK = isPK;
    if(_isPK) { _isRequired = true; }
  }
  public void setColType(int colType) {
    _colType = colType;
  }
}