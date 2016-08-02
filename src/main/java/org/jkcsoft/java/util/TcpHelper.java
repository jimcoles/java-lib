/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2016. through present.
 *
 * Licensed under the following license agreement:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */
package org.jkcsoft.java.util;

import org.apache.commons.logging.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author coles
 */
public class TcpHelper {
    private static Log log = LogHelper.getLogger(TcpHelper.class);

    public static String connectToServer(String host, int port, int timeoutMillis) {
        String fromServer = "";

        BufferedReader reader = null;
        PrintWriter writer = null;
        Socket agentSocket = null;
        try {
            try {
                // open a socket and send to 
                log.debug("Connected to remote socket");
                InetSocketAddress serverAddress = new InetSocketAddress(host, port);
                agentSocket = new Socket();
                agentSocket.connect(serverAddress, timeoutMillis);

                writer = new PrintWriter(agentSocket.getOutputStream());
                reader = new BufferedReader(new InputStreamReader(agentSocket.getInputStream()));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    log.debug("Server message: " + line);
                    fromServer += line;
                }
                log.debug("End of server response (=" + fromServer + ")");
            } catch (IOException ex) {
                log.error("Upon connection to and reading server response", ex);
            }
        } finally {
            JavaHelper.safeClose(writer);
            JavaHelper.safeClose(reader);
            JavaHelper.safeClose(agentSocket);
            log.debug("Closed socket connection");
        }

        return fromServer;
    }
}
