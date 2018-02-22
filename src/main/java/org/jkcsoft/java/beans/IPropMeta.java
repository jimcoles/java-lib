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

/**
 * Meta data for bean properties.  This extends basic JavaBean meta data.
 *
 * @author Jim Coles
 * @version 1.0
 */
public interface IPropMeta {
    public String getProgId();

    public String getColName();

    /**
     * One of the types enumerated in java.sql.Types
     */
    public int getColType();

    public boolean isRequired();

    public boolean isPK();
}