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
 * <p>The tree of system nodes used for management console:</p>
 * <p>
 * <quote>
 * Root Node
 * -> Server Processes Node
 * Shutdown Node
 * SNMP Monitor Node
 * SNMP Poller Node
 * JMS client Node
 * -> JMS Device Node
 * <p>
 * -> Agents Node
 * Agent 1 Node
 * ...
 * Agent n Node
 * </quote>
 *
 * @author Jim Coles
 * @version 2.0
 * @since 1.0
 */
public class Constants {
    //--------------------------------------------------------------------------------------
    // Static members
    //--------------------------------------------------------------------------------------
    private static final String KEY_DERBY_SYSTEM_HOME = "derby.system.home";
//    private static Logger log = Logger.getLogger(TsessUpsGateway.class);

    public static final String KEY_JMS_ADMIN_URL = "jms-admin-url";

    public static final String KEY_AD = "active-directory";
    public static final String KEY_AD_HOST = KEY_AD + ".host";
    public static final String KEY_AD_PORT = KEY_AD + ".port";
    public static final String KEY_AD_SSL = KEY_AD + ".ssl";
    public static final String KEY_AD_SERVICE_USER_NAME = KEY_AD + ".service-user-name";
    public static final String KEY_AD_SERVICE_USER_PW = KEY_AD + ".service-user-pw";
    public static final String KEY_AD_ROOT_DN = KEY_AD + ".root-dn";
    public static final String KEY_AD_USER_NODE_DN = KEY_AD + ".user-node-dn";

    public static final String KEY_APP_HOME_URL = "tsess-home-url";
    public static final String KEY_APP_INSTANCE_NAME = "tsess-instance-name";
    public static final String KEY_STATUS_CHECK_INTERVAL = "status-check-interval-seconds";
    public static final String KEY_JMS_AGENT_TIMEOUT_MILLIS = "jms-agent-timeout-millis";

    public static final String KEY_SNMP = "ups-device";
    public static final String KEY_SNMP_CONN = KEY_SNMP + ".snmp-connection";
    public static final String KEY_SNMP_CONN_HOST = KEY_SNMP_CONN + ".hostname";
    public static final String KEY_SNMP_CONN_PORT = KEY_SNMP_CONN + ".port";
    public static final String KEY_SNMP_CONN_COMM = KEY_SNMP_CONN + ".community";

    public static final String KEY_SNMP_POLLER_INTERVAL = KEY_SNMP + ".ups-poller-interval-seconds";
    public static final String KEY_SNMP_SHUTDOWN_WAIT = KEY_SNMP + ".shutdown-wait-seconds";
    public static final String KEY_SNMP_MIN_BATTERY_MINUTES = KEY_SNMP + ".min-battery-life-minutes";
    public static final String KEY_SNMP_SNMP_MONITOR_PORT = KEY_SNMP + ".snmp-monitor-port";

    public static final String KEY_EMAIL_SERVER_HOST = "email-server.hostname";
    public static final String KEY_ADMIN_EMAIL = "tsess-admin-email";
    public static final String KEY_SHUTDOWN_GROUP_EMAIL = "tsess-shutdown-group-email";

    public static final String KEY_APP_AGENTS = "tsess-agents";
    public static final String KEY_APP_AGENT = KEY_APP_AGENTS + ".tsess-agent";

    public static final String KEY_POLLING_SERVER_PORT = "polling-server-port";

}
