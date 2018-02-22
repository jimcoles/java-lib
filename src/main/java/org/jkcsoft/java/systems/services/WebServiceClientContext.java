/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2018 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */
package org.jkcsoft.java.systems.services;

import org.jkcsoft.java.systems.components.AppConfigHelper;

/**
 * Initial motivation is to provide web service client 'Locators' a way to determine
 * which host machine and port to connect to since the default IBM stub gen logic
 * embeds host:port in the .java files.
 *
 * @author coles
 */
public class WebServiceClientContext {

    AppConfigHelper _conf;

    /**
     *
     */
    public WebServiceClientContext(String configFile) {
        _conf = new AppConfigHelper(configFile);
    }

    /**
     * Return as "host:port"
     */
    public String getHostPort() throws Exception {
        return _conf.getConfigProperty("/web-services/host", null) +
                _conf.getConfigProperty("/web-services/port", null);
    }
}
