/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2018 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.repos;

public interface PersistenceBroker {

    boolean isInTransaction();

    void commitTransaction();

    void close();

    void abortTransaction();

}
