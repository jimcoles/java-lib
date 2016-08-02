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

import org.jkcsoft.java.systems.components.Home;
import org.jkcsoft.java.util.JndiHelper;
import org.jkcsoft.java.util.LogHelper;

import javax.naming.AuthenticationException;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * A JAAS LoginModule that authenticates a username/pw against an
 * Active Directory server via an LDAP bind.
 * <p>
 * <p>@author coles
 */
public class AdOverLdapLoginModule implements LoginModule {

    //-------------------------------------------------------------------------
    // Instance variables
    //-------------------------------------------------------------------------
    private Subject subject;
    private TsessPrincipal tsessPrincipal;
    private CallbackHandler callbackHandler;
    private Map sharedState;
    private Map options;

    //-------------------------------------------------------------------------
    // Constructor(s)
    //-------------------------------------------------------------------------
    public AdOverLdapLoginModule() {
    }

    //-------------------------------------------------------------------------
    // Instance methods
    //-------------------------------------------------------------------------
    public boolean abort() throws LoginException {
        LogHelper.info(this, "Login aborted for subject [" + subject + "]");
        return true;
    }

    public boolean commit() throws LoginException {
        LogHelper.info(this, "Login commited for subject [" + subject + "]");
        if (!subject.getPrincipals().contains(tsessPrincipal)) {
            subject.getPrincipals().add(tsessPrincipal);
        }
        return true;
    }

    public boolean login() throws LoginException {
        boolean login = false;
        NameCallback nameCallback = new NameCallback("Windows Username");
        PasswordCallback pwCallback = new PasswordCallback("Windows Password", false);

        try {
            callbackHandler.handle(new Callback[]{nameCallback, pwCallback});
        } catch (IOException e) {
            LogHelper.error(this, "", e);
        } catch (UnsupportedCallbackException e) {
            LogHelper.error(this, "", e);
        }

        try {
            try {
                // Creating the JNDI directory context (with LDAP context
                // factory), performs an LDAP bind to the LDAP provider thereby
                // authenticating the username/pw.
                DirContext dirCtx =
                        JndiHelper.getDirContext(Home.getHome().getTheApplication(),
                                nameCallback.getName(),
                                pwCallback.getPassword());
                login = dirCtx != null;
                JndiHelper.safeClose(dirCtx);
            } catch (AuthenticationException ex) {
                LogHelper.warn(this, "AD authenticate failed: " + ex.getMessage(), ex);
                throw new LoginException("Active Directory authetication failed.  Please re-enter" +
                        " your Username and Password.");
            } catch (NamingException ex) {
                LogHelper.warn(this, "General JNDI exepction upon LDAP bind: " + ex.getMessage(), ex);
                throw new LoginException("A general directory exception occurred during User" +
                        " authentication.  Details: " + ex.getExplanation());
            }

            if (login) {
                tsessPrincipal = new TsessPrincipal(nameCallback.getName());
            }
        } catch (LoginException e1) {
            LogHelper.error(this, "LoginException", e1);
            throw e1;
        } catch (Exception e1) {
            LogHelper.error(this, "Authenticating user against AD", e1);
            throw new LoginException("Error accessing AD to find user [" + nameCallback.getName() + "]" +
                    "; message from AD [" + e1.getMessage() + "]");
        }

        return login;
    }


    public boolean logout() throws LoginException {
        subject.getPrincipals().remove(tsessPrincipal);
        tsessPrincipal = null;
        return true;
    }

    public void initialize(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.sharedState = sharedState;
        this.options = options;
    }

    public Map getSharedState() {
        return sharedState;
    }

    public Subject getSubject() {
        return subject;
    }

    public static class TsessPrincipal implements Principal, Serializable {

        private String name;
        private String domainName;

        public TsessPrincipal(String name) {
            StringTokenizer st = new StringTokenizer(name, "\\");
            if (st.countTokens() > 1) {
                this.domainName = st.nextToken();
            }
            this.name = st.nextToken();
        }

        /**
         * FYI, Impl Principal.getName().
         */
        public String getName() {
            return name;
        }

        public String getDomainName() {
            return domainName;
        }
    }
}
