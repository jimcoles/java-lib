/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2016. through present.
 *
 * Licensed under the following license agreement:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */
package org.jkcsoft.java.systems.security;

import org.jkcsoft.java.util.LogHelper;

import javax.security.auth.callback.*;
import java.io.IOException;
import java.io.Serializable;

/**
 * Suitable for use by Struts or other Http GUI.  Can be stored in
 * session or put on HttpRequest.
 *
 * @author coles
 */
public class WebCallbackHandler implements CallbackHandler, Serializable {

    //-------------------------------------------------------------------------
    // Instance variables
    //-------------------------------------------------------------------------
    private String username;
    private String password;
    private TextOutputCallback textOutput;


    //-------------------------------------------------------------------------
    // Constructor(s)
    //-------------------------------------------------------------------------
    public WebCallbackHandler(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //-------------------------------------------------------------------------
    // Instance methods
    //-------------------------------------------------------------------------
    public void handle(Callback[] callbacks) throws IOException,
            UnsupportedCallbackException {

        for (int idxCb = 0; idxCb < callbacks.length; idxCb++) {
            Callback cb = callbacks[idxCb];
            if (cb instanceof NameCallback) {
                NameCallback cbName = (NameCallback) cb;
                LogHelper.debug(this, "Received Name Callback [" + cbName.getPrompt() + "]");
                cbName.setName(username);
            } else if (cb instanceof PasswordCallback) {
                PasswordCallback cbPw = (PasswordCallback) cb;
                LogHelper.debug(this, "Receieved Password Callback [" + cb + "]");
                cbPw.setPassword(password.toCharArray());
            } else if (cb instanceof TextOutputCallback) {
                TextOutputCallback cbTextOutput = (TextOutputCallback) cb;
                LogHelper.debug(this, "Received Text Output Callback [" + cbTextOutput.getMessage() + "]");
                textOutput = cbTextOutput;
            } else {
                LogHelper.warn(this, "Received callback type that we are not handling [" + cb.getClass().toString() + "]");
                throw new UnsupportedCallbackException(cb);
            }

        }
    }


    public TextOutputCallback getTextOutput() {
        return textOutput;
    }
}
