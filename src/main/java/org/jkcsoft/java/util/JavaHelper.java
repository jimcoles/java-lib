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

import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

/**
 * @author coles
 */
public class JavaHelper {

    private static final Log log = LogHelper.getLogger(JavaHelper.class);

    public static final String EOL = System.getProperty("line.separator");

    /**
     * @param server
     */
    public static void safeClose(ServerSocket server) {
        if (server != null) {
            try {
                server.close();
            } catch (IOException e) {
                log.error("Upon closing", e);
            }
        } else {
            log.warn("Null ServerSocket sent to safeClose()");
        }
    }

    /**
     * @param outputStream
     */
    public static void safeClose(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                log.error("Upon closing", e);
            }
        } else {
            log.warn("Null OutputStream sent to safeClose()");
        }
    }

    /**
     * @param socket
     */
    public static void safeClose(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                log.error("Upon closing", e);
            }
        } else {
            log.warn("Null Socket sent to safeClose()");
        }
    }

    /**
     * @param writer
     */
    public static void safeClose(BufferedWriter writer) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                log.error("Upon closing", e);
            }
        } else {
            log.warn("Null BufferedWriter sent to safeClose()");
        }
    }

    /**
     * @param reader
     */
    public static void safeClose(BufferedReader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                log.error("Upon closing", e);
            }
        } else {
            log.warn("Null BufferedReader sent to safeClose()");
        }
    }

    /**
     * @param writer
     */
    public static void safeClose(PrintWriter writer) {
        if (writer != null) {
            writer.close();
        } else {
            log.warn("Null PrintWriter sent to safeClose()");
        }
    }

    /**
     * Sleeps for sleep millis unless interrupted.
     *
     * @param sleep
     */
    public static void sleep(long sleep) {
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            log.warn("Thread [" + Thread.currentThread().getName() + "] interrupted during sleeep.");
        }
    }

    public static String stackTrace(Throwable th) {
        if (th == null) return null;

        java.io.StringWriter sw = new java.io.StringWriter();
        java.io.PrintWriter pw = new java.io.PrintWriter(sw);
        th.printStackTrace(pw);
        return sw.toString();
    }

    /**
     * @return true if both values are non-null and obj1.equals(obj2)
     */
    public static boolean equals(Object obj1, Object obj2) {
        boolean eq = false;
        if (obj1 != null && obj2 != null) {
            eq = obj1.equals(obj2);
        }
        return eq;
    }

    public static boolean equals(String obj1, String obj2) {
        boolean eq = false;
        if (obj1 != null && obj2 != null) {
            eq = obj1.equals(obj2);
        } else {
            eq = Strings.isEmpty(obj1) && Strings.isEmpty(obj2);
        }
        return eq;
    }

    public static boolean equals(Integer obj1, int obj2) {
        boolean eq = false;
        if (obj1 != null) {
            eq = obj1.shortValue() == obj2;
        }
        return eq;
    }

    public static boolean equals(Long obj1, long obj2) {
        boolean eq = false;
        if (obj1 != null) {
            eq = obj1.shortValue() == obj2;
        }
        return eq;
    }

    public static boolean equals(Boolean obj1, boolean obj2) {
        boolean eq = false;
        if (obj1 != null) {
            eq = obj1.booleanValue() == obj2;
        }
        return eq;
    }

    public static boolean toBoolean(Integer obj1) {
        return !equals(obj1, 0);
    }

    public static boolean toBoolean(Long obj1) {
        return !equals(obj1, 0);
    }

    public static boolean toBoolean(int value) {
        return (value != 0);
    }

    public static int toInt(boolean value) {
        return (value) ? 1 : 0;
    }

    public static InetAddress getUsefulInetAddr() throws UnknownHostException {
        InetAddress returnInetAddr = InetAddress.getLocalHost();
        int usefulCount = 0;
        try {
            Enumeration niEnum = NetworkInterface.getNetworkInterfaces();
            while (niEnum.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) niEnum.nextElement();
                Enumeration ieEnum = ni.getInetAddresses();
                while (ieEnum.hasMoreElements()) {
                    InetAddress inetAddr = InetAddress.getLocalHost();
                    inetAddr = (InetAddress) ieEnum.nextElement();
                    log.debug("NIC [" + ni.getDisplayName() + "]" +
                            " Addr hn=[" + inetAddr.getHostName() + "]" +
                            " chn=[" + inetAddr.getCanonicalHostName() + "]" +
                            " ha=[" + inetAddr.getHostAddress() + "]");
                    // hack to skip addresses often provided by Linux...
                    if (!"127.0.0.1".equals(inetAddr.getHostAddress())
                            && inetAddr.getHostAddress().indexOf(':') == -1
                            ) {
                        if (usefulCount == 0) returnInetAddr = inetAddr;
                        usefulCount++;
                    } else {
                        // 
                    }
                }
            }
        } catch (SocketException e) {
            log.error("getHostName", e);
        }

        if (usefulCount == 0) {
            log.warn("Only the loopback InetAddress could be found");
        }
        if (usefulCount > 1) {
            log.warn("More than one non-loopback InetAddrss was found; using the first one found.");
        }

        log.debug("Returning inet addr [" + returnInetAddr.toString() + "]");
        return returnInetAddr;
    }

    public static String computeUqName(Class clazz) {
        if (clazz != null)
            return Strings.restAfterLast(clazz.getName(), ".");
        else
            return null;
    }

    public static String computeUqName(Object obj) {
        if (obj != null)
            return computeUqName(obj.getClass());
        else
            return null;
    }

    public static List reverse(List inList) {
        List retList = null;

        if (inList != null) {
            if (!inList.isEmpty()) {
                retList = new Vector();
                Object[] arr = inList.toArray();
                for (int idx = arr.length - 1; idx >= 0; idx--) {
                    retList.add(arr[idx]);
                }
            } else {
                retList = inList;
            }
        }

        return retList;
    }

    public static void out(String line) {
        System.out.println(line);
    }
}
