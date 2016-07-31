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

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author coles
 */
public class NaiveTrustManager implements X509TrustManager {

    private static NaiveTrustManager instance = null;

    public static NaiveTrustManager getInstance() {
        if (instance == null) {
            instance = new NaiveTrustManager();
        }
        return instance;
    }

    /* (non-Javadoc)
     * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
     */
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

    /* (non-Javadoc)
     * @see javax.net.ssl.X509TrustManager#checkClientTrusted(java.security.cert.X509Certificate[], java.lang.String)
     */
    public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        LogHelper.info(this, "Called checkClientTrusted()");
    }

    /* (non-Javadoc)
     * @see javax.net.ssl.X509TrustManager#checkServerTrusted(java.security.cert.X509Certificate[], java.lang.String)
     */
    public void checkServerTrusted(X509Certificate[] serverCerts, String authType) throws CertificateException {
        LogHelper.info(this, "Called checkServerTrusted() - auth type [" + authType + "]");
        for (int idxCert = 0; idxCert < serverCerts.length; idxCert++) {
            X509Certificate certificate = serverCerts[idxCert];
            LogHelper.info(this, "  Cert => " + certificate.toString());
        }
    }


}
