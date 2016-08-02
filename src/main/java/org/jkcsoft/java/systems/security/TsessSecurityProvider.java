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

import java.security.Provider;

/**
 * @author coles
 */
public class TsessSecurityProvider extends Provider {

    /**
     * A java.security.Provider that registers the TSESS
     * NaiveTrustManagerFactory, which trusts all server certificates
     * to which an SSL socket connection is made, i.e., we're not
     * worried about authenticity of server; we just want to encrypt
     * data we send.
     * <p>
     * Only current application is SSL connection to Active Directory.
     */
    public TsessSecurityProvider() {
        super("TsessSec", 1, "TSESS Custom Security Components");

        this.setProperty("TrustManagerFactory.TsessTrustAll",
                NaiveTrustManagerFactoryImpl.class.getName());
    }


}
