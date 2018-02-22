/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2018 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */
package org.jkcsoft.java.systems.messages;

import org.jkcsoft.java.repos.ReposManager;
import org.jkcsoft.java.systems.procedures.Procedure;
import org.jkcsoft.java.util.LogHelper;

import java.util.Map;
import java.util.TreeMap;

/**
 * <p>This class</p>
 * <p>
 * <p>Responsibilities:
 * <ul>
 * <li>Dispatcher - Sees all access into the Procedures by consuming objects including calls from one
 * Procedure to another.
 * <li>Enforces singleton instance for each DMAP class.  Does lazy instantiation.
 * <li>Transaction Monitor - does transaction start/commit/rollback
 * </ul>
 * </p>
 *
 * @author J. Coles
 */
public class MessageBroker {
    //------------------------------------------------------------------------------
    // Class-level members
    //------------------------------------------------------------------------------
    private static MessageBroker _instance = new MessageBroker();

    private static Map<String, Procedure> procsMap = new TreeMap<String, Procedure>();

    public static RequestImpl buildRequest() {
        return _instance._buildRequest();
    }

    public static Response invoke(Class clazz, RequestImpl requestImpl)
            throws Exception {
        return _instance._invoke(clazz, requestImpl);
    }

    public static Response invoke(Class clazz, Object input)
            throws Exception {
        RequestImpl requestImpl = buildRequest();
        requestImpl.setInputMessage(input);
        return invoke(clazz, requestImpl);
    }

    //------------------------------------------------------------------------------
    // Instance-level members
    //------------------------------------------------------------------------------
    private RequestImpl _buildRequest() {
        RequestImpl requestImpl = new RequestImpl(this);
        requestImpl.setResponse(new Response());
        return requestImpl;
    }

    private Response _invoke(Class clazz, RequestImpl requestImpl)
            throws Exception {

        if (requestImpl == null) {
            LogHelper.warn(this, "Null request object");
            return null;
        }

        if (clazz == null) {
            LogHelper.error(this, "Null Dmap class argument");
            return null;
        }

        try {

            // control DMAP instances -- one per procName
            Procedure procedure = procsMap.get(clazz.getName());

            if (procedure == null) {
                try {
                    procedure = (Procedure) clazz.newInstance();
                } catch (ClassCastException ex) {
                    // should not get this due to pre-condition test
                    String msg = "The specified class [" + clazz.getName() + "] is not a Dmap (" + Procedure.class.getName() + ")";
                    LogHelper.error(this, msg);
                    throw new Exception(msg);
                }
                procsMap.put(clazz.getName(), procedure);
            }

            // Push stack:  p1 -> p2 -> p3 -> ...
            requestImpl.pushCall(procedure);
            LogHelper.debug(this, "Pushed Dmap " + procedure.getClass().getName());

            // call the Dmap
            LogHelper.info(procedure, "Entering execute()");
            procedure.execute(requestImpl, requestImpl.getResponse());
            LogHelper.info(procedure, "Normal exit execute()");

            // Pop stack: p1 <- p2 <- p3 
            Procedure popped = (Procedure) requestImpl.popCall();
            LogHelper.debug(this, "Popped Dmap " + popped.getClass().getName());

            // Transaction control
            if (requestImpl.getCallStack().isEmpty()) {
                if (requestImpl.getPersistenceBroker() != null) {
                    try {
                        if (requestImpl.getPersistenceBroker().isInTransaction()) {
                            try {
                                requestImpl.getPersistenceBroker().commitTransaction();
                            } catch (Exception ex) {
                                LogHelper.error(this, "Commiting transaction; will rollback", ex);
                                safeRollback(requestImpl);
                            }
                        }
                    } catch (Exception ex) {
                        LogHelper.error(this, "Finalizing transaction", ex);
                    } finally {
                        safeClose(requestImpl);
                    }
                }
            }

        } catch (Exception e) {
            LogHelper.error(this, "Error while invoking Dmap", e);
            requestImpl.getResponse().addError(new Error("error.runtime", e.getMessage()));
            if (requestImpl.getPersistenceBroker() != null) {
                try {
                    if (requestImpl.getPersistenceBroker().isInTransaction()) {
                        LogHelper.info(this, "Rolling back transaction due to error");
                        safeRollback(requestImpl);
                    }
                } catch (Exception ex) {
                    LogHelper.error(this, "Finalizing transaction", ex);
                } finally {
                    safeClose(requestImpl);
                }
            }
        }

        return requestImpl.getResponse();
    }

    private void safeClose(RequestImpl requestImpl) {
        try {
            requestImpl.getPersistenceBroker().close();
            requestImpl.setPersistenceBroker(null);
        } catch (Exception ex) {
            LogHelper.warn(this, "Problem closing broker", ex);
        }
    }

    private void safeRollback(Request request) {
        try {
            request.getPersistenceBroker().abortTransaction();
        } catch (Exception ex) {
            LogHelper.warn(this, "Problem rolling back transaction", ex);
        }
    }

    /**
     * @param requestImpl
     * @throws Exception
     */
    void addBroker(RequestImpl requestImpl) throws Exception {
        if (requestImpl.getPersistenceBroker() == null) {
            requestImpl.setPersistenceBroker(getReposManager().getBroker());
        }
    }

    // TODO: add factory interface and impls
    ReposManager getReposManager() {
        return null;
    }
}
