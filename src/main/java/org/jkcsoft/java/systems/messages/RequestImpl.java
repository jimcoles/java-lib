/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2016. through present.
 *
 * Licensed under the following license agreement:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

/*
 * Created on Dec 14, 2004
 *
 */
package org.jkcsoft.java.systems.messages;

import org.jkcsoft.java.repos.PersistenceBroker;
import org.jkcsoft.java.systems.contexts.AbstractContext;
import org.jkcsoft.java.systems.procedures.Procedure;

import java.util.Stack;

/**
 * @author coles
 */
public class RequestImpl extends AbstractContext implements Request {
    //------------------------------------------------------------------
    // Instance vars
    //------------------------------------------------------------------
    private MessageBroker messageBroker = null;

    // NOTE: J Coles - I think we can avoid using params and use JavaBean getInputMessage()
    //                 which just returns a Web Service-compatible wrapper bean.

    //    private Map parameters;

    // Input 'message' wrapper
    private Object inputMessage;

    private PersistenceBroker persistenceBroker = null;
    private Stack callStack = new Stack();
    private Response response = null;

    //------------------------------------------------------------------
    // Constructor(s)
    //------------------------------------------------------------------

    /**
     * @param parent
     */
    RequestImpl(MessageBroker parent) {
        super();
        this.messageBroker = parent;
    }

    //------------------------------------------------------------------
    // Instance methods
    //------------------------------------------------------------------

    public Object getInputMessage() {
        return inputMessage;
    }

    public void setInputMessage(Object inputMessage) {
        this.inputMessage = inputMessage;
    }

    MessageBroker getDmapBroker() {
        return messageBroker;
    }

    //    /**
//     * Call parameters as Map of name/value Strings.
//     */
//    public Map getParameters() {
//        return parameters;
//    }


    public PersistenceBroker getPersistenceBroker() throws Exception {
        messageBroker.addBroker(this);
        return this.persistenceBroker;
    }

    public void setPersistenceBroker(PersistenceBroker persistenceBroker) {
        this.persistenceBroker = persistenceBroker;
    }

    Stack getCallStack() {
        if (callStack == null) {
            callStack = new Stack();
        }
        return callStack;
    }

    Object pushCall(Procedure procedure) {
        return getCallStack().push(procedure);
    }

    Object popCall() {
        return getCallStack().pop();
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
//    public Object getParam(String key) {
//        if (parameters != null) {
//            return parameters.get(key);
//        }
//        else {
//            return null;
//        }
//    }
//    
//    public void setParam(String key, Object value) {
//        if (parameters == null) {
//            parameters = new TreeMap();
//        }
//        parameters.put(key, value);
//    }


}
