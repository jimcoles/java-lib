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

/**
 * Enumerated states for various types of sub-systems, threads, clients, polling clients, servers, etc..
 * <p>
 * TODO: use Enumerated types
 *
 * @author coles
 */
public class SystemState extends ProcessState {

    //------------------------------------------------------------------------------
    // Static members
    //------------------------------------------------------------------------------
    public static final SystemState UNINITIALIZED = new SystemState("uninitialized", "Uninitialized", null, WarningLevel.LEVEL_WARN);
    public static final SystemState SOFT_DELETE = new SystemState("soft-delete", "Deleted/Hidden", null, WarningLevel.LEVEL_OK);

    // threads
    public static final SystemState NORMAL = new SystemState("normal", "Running", null, WarningLevel.LEVEL_OK);
    public static final SystemState DEAD = new SystemState("dead", "Not Running; attempting restart", null, WarningLevel.LEVEL_PROBLEM);

    // clients
    public static final SystemState CONNECTED = new SystemState("connected", "Connected", null, WarningLevel.LEVEL_OK);
    public static final SystemState RECONNECTING = new SystemState("reconnecting", "Not Connected; attempting reconnect", null, WarningLevel.LEVEL_PROBLEM);

    // polling client
    public static final SystemState LAST_CONNECTION_OK = new SystemState("last-connection-ok", "Last Connection OK", null, WarningLevel.LEVEL_OK);
    public static final SystemState CONNECTION_INIT = new SystemState("connection-init", "Initializing Connection", null, WarningLevel.LEVEL_WARN);
    public static final SystemState LAST_CONNECTION_FAILED = new SystemState("last-connection-failed", "Last Connection Failed", null, WarningLevel.LEVEL_PROBLEM);

    // servers
    public static final SystemState LISTENING = new SystemState("listening", "Listening", null, WarningLevel.LEVEL_OK);
    public static final SystemState LISTENING_NOT = new SystemState("not-listening", "Not Listening", null, WarningLevel.LEVEL_WARN);
    public static final SystemState LISTENING_FAILED = new SystemState("listening-failed", "Listening Process Failed", null, WarningLevel.LEVEL_PROBLEM);

    // remote agents
    public static final SystemState AGENT_RESPONDING = new SystemState("responding", "Alive and Responding", null, WarningLevel.LEVEL_OK);
    public static final SystemState AGENT_NOT_RESPONDING = new SystemState("not-responding", "Not Responding", null, WarningLevel.LEVEL_PROBLEM);
    public static final SystemState AGENT_INACTIVE = new SystemState("inactive", "Inactive", null, WarningLevel.LEVEL_OK);
    public static final SystemState AGENT_INACTIVE_ALIVE = new SystemState("inactive-alive", "Inactive / Alive", null, WarningLevel.LEVEL_WARN);

    //------------------------------------------------------------------------------
    // Instance vars
    //------------------------------------------------------------------------------

    //------------------------------------------------------------------------------
    // Constructor(s)
    //------------------------------------------------------------------------------
    private SystemState(String name, String displayName, String imageName, WarningLevel statusLevel) {
        super(name, displayName, imageName, statusLevel);
    }

    //------------------------------------------------------------------------------
    // Instance methods
    //------------------------------------------------------------------------------

}
