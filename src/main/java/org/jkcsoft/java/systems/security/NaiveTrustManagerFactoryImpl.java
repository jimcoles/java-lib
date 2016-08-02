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

import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactorySpi;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;

/**
 * @author coles
 */
public class NaiveTrustManagerFactoryImpl extends TrustManagerFactorySpi {

    /* (non-Javadoc)
     * @see javax.net.ssl.TrustManagerFactorySpi#engineInit(java.security.KeyStore)
     */
    protected void engineInit(KeyStore arg0) throws KeyStoreException {
        LogHelper.info(this, "engineInit(KeyStore arg0) called");
    }

    /* (non-Javadoc)
     * @see javax.net.ssl.TrustManagerFactorySpi#engineInit(javax.net.ssl.ManagerFactoryParameters)
     */
    protected void engineInit(ManagerFactoryParameters arg0)
            throws InvalidAlgorithmParameterException {
        LogHelper.info(this, "engineInit(ManagerFactoryParameters arg0) called");
    }

    /* (non-Javadoc)
     * @see javax.net.ssl.TrustManagerFactorySpi#engineGetTrustManagers()
     */
    private TrustManager[] theArray =
            new TrustManager[]{NaiveTrustManager.getInstance()};

    protected TrustManager[] engineGetTrustManagers() {
        LogHelper.info(this, "engineGetTrustManagers() called");
        return theArray;
    }

}
