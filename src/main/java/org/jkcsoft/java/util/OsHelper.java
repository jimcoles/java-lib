/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2018 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */
package org.jkcsoft.java.util;

import org.apache.commons.logging.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class OsHelper {

    private static Log log = LogHelper.getLogger(OsHelper.class);

    public static void runOsCommand(String command, Log log) throws IOException {
        try {

            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec(command);

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            // read the output from the command

            log.info("Here is the standard output of the command:");
            String stLine = null;
            while ((stLine = stdInput.readLine()) != null) {
                log.info(stLine);
            }

            // read any errors from the attempted command
            log.info("Here is the standard error of the command (if any):");
            while ((stLine = stdError.readLine()) != null) {
                log.info(stLine);
            }
        } catch (IOException e) {
            log.error("running OS command", e);
            throw e;
        }
    }

    public static boolean isLinux() {
        String stOsName = System.getProperty("os.name");
        log.info("os.name = " + stOsName);
        return stOsName.equalsIgnoreCase("linux");
    }

    public static boolean isExecutable(File file, Log log) throws Exception {
        boolean is = true;

        if (isLinux()) {
            // e.g., /usr/bin/test -x /home/coles/build/build-tsess.sh && echo yes || echo no
            try {

                Runtime rt = Runtime.getRuntime();
                String stTest = "/usr/bin/test -x " + file.getAbsolutePath() + " && echo yes || echo no";
                log.debug("Command=" + stTest);

                Process p = rt.exec(stTest);

                BufferedReader stdInput = new BufferedReader(new
                        InputStreamReader(p.getInputStream()));

                BufferedReader stdError = new BufferedReader(new
                        InputStreamReader(p.getErrorStream()));

                String stLine = null;

                // read any errors from the attempted command
                log.info("Here is the standard error of the command (if any):");
                while ((stLine = stdError.readLine()) != null) {
                    log.warn(stLine);
                }

                // read the output from the command
                StringBuilder sbResponse = new StringBuilder();
                while ((stLine = stdInput.readLine()) != null) {
                    sbResponse.append(stLine);
                }

                is = "yes".equalsIgnoreCase(sbResponse.toString());

            } catch (IOException e) {
                log.error("In isExecutable()", e);
                throw e;
            }
        } else {
            log.info("Not Linux -- assume executable");
        }

        return is;
    }
}
