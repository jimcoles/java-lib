/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2018 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.systems.components;

import org.jkcsoft.java.util.Dates;
import org.jkcsoft.java.util.LogHelper;

import java.util.Date;


/**
 * <p>Represents any component or sub-system of the overall system that we are
 * interested in for status or control.  Primarily represents processes or
 * physical networks nodes or 'objects'.
 * </p>
 * <p>Represents a hierarchy of sub-systems particularly to support the requirement to
 * present a roll-up of system status.
 * </p>
 *
 * @author coles
 */
public abstract class AbstractSystemNode implements SystemNode {
    //------------------------------------------------------------------------------
    // Static members
    //------------------------------------------------------------------------------


    //------------------------------------------------------------------------------
    // Instance vars
    //------------------------------------------------------------------------------
    private ProcessState state;
    private String stateMessage;  // holds exception and 'message'
    private SystemNode parent;
    private Date stateChangeDate;

//    private ProcessState rolledUpStatusLevel;

//    private List subSystems;

    //------------------------------------------------------------------------------
    // Constructor(s)
    //------------------------------------------------------------------------------
    public AbstractSystemNode(SystemNode parent) {
        this.parent = parent;
    }


    //------------------------------------------------------------------------------
    // Instance methods
    //------------------------------------------------------------------------------

    public ProcessState getState() throws Exception {
        return state;
    }

    public void setState(ProcessState state) {
        if (state != this.getCachedState()) {
            LogHelper.debug(this, "Setting state of [" + this + "] to [" + state + "]");
            this.state = state;
            stateMessage = null;
            stateChangeDate = Dates.nowDate();
        }
    }

    public String getStateMessage() {
        return stateMessage;
    }

    public void setStateMessage(String stateMessage) {
        this.stateMessage = stateMessage;
    }

    public SystemNode getParentSystemNode() {
        return parent;
    }

    public WarningLevel getWarningLevel() {
        ProcessState cachedState = getCachedState();
        if (cachedState != null) {
            return cachedState.getWarningLevel();
        }
        return null;
    }

    public String toString() {
        return getDisplayName();
    }


    public Date getStateChangeDate() {
        return stateChangeDate;
    }

    public ProcessState getCachedState() {
        return state;
    }

}
