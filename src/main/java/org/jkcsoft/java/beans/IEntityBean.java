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
 * Interface for JavaBean in the TRI library that represent business entities
 * such as "Customer' or 'Employee'.  Often maps directly to a database table.
 *
 * @author Jim Coles
 * @version 1.0
 */

import java.util.List;

public interface IEntityBean extends IDispatch
{
  /** Supports persistence. Do we need a setter? */
  public void setLifeState(String lifeState);
  /** Supports persistence. */
  public String getLifeState();

  /**
   * Asks the bean to validate itself.  Can be used by the Command layer or the
   * JSP layer.
   *
   * @return A <code>java.util.List</code> of 1 or more Strings representing distinct
   * object validation error messages suitable for display to the end-user.
   * Null if the object is valid.
   */
  public List validate();

  public IEntityMeta getMetaData() throws Exception;

  // <IDispatch>
  public Object get(String prop) throws Exception;
  public void set(String prop, Object value) throws Exception;
  // </IDispatch>

}