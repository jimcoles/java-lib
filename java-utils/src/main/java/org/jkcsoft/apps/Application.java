/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2018 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.apps;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jim Coles
 */
public class Application {

    private final String name;
    private final File homeDir;
    private final File configDir;
    private final File libDir;
    private Map<Integer, String> errorCodeMap = new HashMap<Integer, String>();
    private Options clOptions;  // a declaration of the options, not the instance of a cl.
    private CommandLine commandLine;

    public Application(String name) {
        this.name = name;
        homeDir = FileUtils.getFile(".");
        configDir = FileUtils.getFile(homeDir, "conf");
        libDir = FileUtils.getFile(homeDir, "lib");
    }

    public String getName() {
        return name;
    }

    public Options getClOptions() {
        return clOptions;
    }

    public CommandLine getCommandLine() {
        return commandLine;
    }

    public void loadCommandLine(Options cl) {
        this.clOptions = cl;
    }

    public Map<Integer, String> getErrorCodeMap() {
        return errorCodeMap;
    }

    public Configuration getConfig() {
        return null;
    }

    public File getHomeDir() {
        return homeDir;
    }

    public File getConfigDir() {
        return configDir;
    }

    public File getLibDir() {
        return libDir;
    }
}
