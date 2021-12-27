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

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;

import java.io.File;

/**
 * @author coles
 */
public class Files {
    private static final Log log = LogHelper.getLogger(Files.class);

    private static final String VERSION_DELIM = ".";
    private static final int MAX_ARCHIVE_FILES = 10;

    public static void archiveFile(File authFile) throws Exception {
        log.info("Archiving file '" + authFile.getAbsolutePath() + "'");

        // [file-root];nn

        // Delete oldest archive file if it exists...
        File archFileMax = new File(authFile.getAbsolutePath() + VERSION_DELIM
                + MAX_ARCHIVE_FILES);
        if (archFileMax.exists()) {
            log.debug("Deleting oldest archive file " + archFileMax.getAbsolutePath());
            archFileMax.delete();
        }

        // Traverse backwards to rename existing archive files
        // from MAX_FILES-1 thru 1
        for (int idxFile = MAX_ARCHIVE_FILES - 1; idxFile >= 1; idxFile--) {
            File archFile = new File(authFile.getAbsolutePath() + VERSION_DELIM
                    + idxFile);
            if (archFile.exists()) {

                File toFile = new File(authFile.getAbsolutePath()
                        + VERSION_DELIM + (idxFile + 1));
                boolean renamed = archFile.renameTo(toFile);

                if (!renamed) {
                    log.warn("Failed to rename archive file "
                            + archFile.getAbsolutePath() + " to "
                            + toFile.getAbsolutePath());
                } else {
                    log.debug("Renamed archive file "
                            + archFile.getAbsolutePath() + " to "
                            + toFile.getAbsolutePath());
                }
            }
        }

        // Copy current file to *;1 version...
        File archFile1 = new File(authFile.getAbsolutePath() + VERSION_DELIM
                + 1);

        FileUtils.copyFile(authFile, archFile1);

    }

}
