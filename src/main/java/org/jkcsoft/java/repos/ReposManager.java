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

import org.jkcsoft.java.systems.messages.Request;

public interface ReposManager {

    /**
     * Called at app init-time. Should validate XML meta, load it, and sync to
     * db. Throw exception/log if db meta can not be synced with XML. Meta
     * entities to sync: -
     */
    public void init() throws Exception;

    /**
     * Public Funnel point method for ALL inserts
     */
    public void insert(Request request, FlexEntity entity) throws Exception;

    /**
     * Public Funnel point method for ALL updates
     */
    public void update(Request request, FlexEntity entity);

    /**
     * Funnel point method for ALL selects/queries.
     * <p>
     * TODO: Define Query structure
     */
    public void select();

    /**
     * Soft deletes only: set isActive=false
     */
    public void delete(Request request, FlexEntity entity);

    public void addModelElement(FlexModelElement fme);

    public void safeClose(PersistenceBroker broker);

    public void safeRollback(PersistenceBroker broker);

    public PersistenceBroker getBroker();

}