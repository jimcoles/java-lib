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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.jkcsoft.java.util.Strings.fmt;

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
                
                File toFile = new File(authFile.getAbsolutePath() + VERSION_DELIM + (idxFile + 1));
                boolean renamed = archFile.renameTo(toFile);
                
                if (!renamed) {
                    log.warn("Failed to rename archive file "
                                 + archFile.getAbsolutePath() + " to "
                                 + toFile.getAbsolutePath());
                }
                else {
                    log.debug("Renamed archive file "
                                  + archFile.getAbsolutePath() + " to "
                                  + toFile.getAbsolutePath());
                }
            }
        }
        
        // Copy current file to *;1 version...
        File archFile1 = new File(authFile.getAbsolutePath() + VERSION_DELIM + 1);
        
        FileUtils.copyFile(authFile, archFile1);
        
    }
    
    /**
     * Tries to load path name as a system-dependent file name. May be relative or absolute file path. If that fails,
     * tries to load it as a classpath resource.
     *
     * @param filePath
     * @return The input stream to the local file or resource.
     */
    public static InputStream findInputStream(ClassLoader classLoader, String filePath) {
        InputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
        }
        catch (FileNotFoundException e) {
            fis = getResourceFis(classLoader, filePath);
        }
        return fis;
    }
    
    public static InputStream getResourceFis(Object caller, String cpRelativePath) {
        return getResourceFis(caller.getClass().getClassLoader(), cpRelativePath);
    }
    
    public static InputStream getResourceFis(final ClassLoader classLoader, final String cpRelativePath) {
        InputStream stream = classLoader.getResourceAsStream(cpRelativePath);
        if (stream == null) {
            throw new IllegalArgumentException(fmt("resource not found [{0}]", cpRelativePath));
        }
        return stream;
    }
    
}
