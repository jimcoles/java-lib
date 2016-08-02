/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2016. through present.
 *
 * Licensed under the following license agreement:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */
package org.jkcsoft.java.systems.components;

import org.jkcsoft.java.systems.events.Event;

import java.util.Date;

/**
 * @author coles
 */
public interface SystemNode {
    //------------------------------------------------------------------------------
    public String getDisplayName();

    public ProcessState getState() throws Exception;

    /**
     * Never makes remote call or evaluation to determine state.
     */
    public ProcessState getCachedState();

    public WarningLevel getWarningLevel();

    public void setState(ProcessState state);

    public String getStateMessage();

    public void setStateMessage(String stateMessage);

    public SystemNode getParentSystemNode();

    public Date getStateChangeDate();

    public void shutdown(Event context);

    public Thread getRunnerThread();
}