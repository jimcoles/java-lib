/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2018 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

/*
 * Created on Dec 14, 2004
 *
 */
package org.jkcsoft.java.systems.procedures;

import org.jkcsoft.java.repos.ReposManager;
import org.jkcsoft.java.systems.messages.Request;
import org.jkcsoft.java.systems.messages.Response;

/**
 * Abstract base class for all Domain Model Access Procedures (DMAPs).
 * <p>
 * Some possible sub-types or just standard types of Dmaps:
 * <p>
 * - Insert
 * - Select
 * - SelectSingleton
 * - Update
 * - Delete
 *
 * @author coles
 */
public abstract class Procedure {

    private ReposManager reposManager;

    /**
     * The entry point used by the DmapBroker.  All Dmaps will extend this base
     * and therefore the logic in this method will touch all requests to the 'Model'.
     * Good place to do logging, access control and transaction management.
     */
    public abstract void execute(Request request, Response response)
            throws Exception;

    /**
     * Used to enforce access control
     */
//	public abstract String getProcId();
    protected ReposManager getReposManager() {
        return reposManager;
    }
}
