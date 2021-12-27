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
 * Interface for accessing the 'meta' data for an IEntityBean class.  This is a catch-all
 * interface that will encompass the following information: <br>
 * - the mapping of bean properties to database columns
 * - field definitions including validation information such as max / min length
 *
 * @author Jim Coles
 * @version 1.0
 */

public interface IEntityMeta {
    public String getProgId();

    public String getTableName();

    /**
     * Returns a list of IPropMeta's.
     */
    public PropertyList getPropMetaList();

    public PropertyList getPKList();
}