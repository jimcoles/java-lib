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

import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.TrustManagerFactorySpi;
import java.security.Provider;

/**
 * @author coles
 */
public class NaiveTrustManagerFactory extends TrustManagerFactory {

    /**
     * @param arg0
     * @param arg1
     * @param arg2
     */
    protected NaiveTrustManagerFactory(TrustManagerFactorySpi arg0, Provider arg1, String arg2) {
        super(arg0, arg1, arg2);
    }

}
