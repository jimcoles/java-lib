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

import java.util.List;
import java.util.Vector;

public class HeartbeatLogger implements Runnable {

    //-------------------------------------------------------------------------
    // Static members
    //-------------------------------------------------------------------------
    private static List<Thread> threads = new Vector<Thread>();

    public static Thread startLoggerThread(String threadName, Log log, int initDelayMillis, long waitTimeMillis, Checkup checkup) {
        Thread t = new Thread(new HeartbeatLogger(log, initDelayMillis, waitTimeMillis, checkup), threadName);
        threads.add(t);
        t.setDaemon(true);
        t.start();
        return t;
    }

    public static void stopThreads() {
        for (Thread thread : threads) {
            if (thread != null) {
                thread.interrupt();
            }
        }
    }

    //-------------------------------------------------------------------------
    // Instance vars
    //-------------------------------------------------------------------------
    private Log log;
    private long initDelayMillis;
    private long waitTimeMillis;
    private Checkup checkup;


    //-------------------------------------------------------------------------
    // Constructor(s)
    //-------------------------------------------------------------------------
    private HeartbeatLogger(Log log, long initDelayMillis, long waitTimeMillis, Checkup checkup) {
        this.log = log;
        this.initDelayMillis = initDelayMillis;
        this.waitTimeMillis = waitTimeMillis;
        this.checkup = checkup;
    }

    //-------------------------------------------------------------------------
    // Instance methods
    //-------------------------------------------------------------------------
    public void run() {
        boolean isInterrupted = false;
        try {
            Thread.sleep(initDelayMillis);
            while (!isInterrupted) {
                log.info(checkup.getStatus());
                Thread.sleep(waitTimeMillis);  // sleep for time or until notify() 'ed.
                isInterrupted = isInterrupted || Thread.currentThread().isInterrupted();
            }
        } catch (InterruptedException ex) {
            log.info("Caught interrupt => " + ex.getMessage());
            isInterrupted = true;
        }
        log.warn("Monitor thread interrupted; returning from run(); thread should stop.");
    }

    /**
     * Consuming objects will impl the Checkup interface to let this thread
     * log the status.
     *
     * @author coles
     */
    public static interface Checkup {
        public String getStatus();
    }
}
