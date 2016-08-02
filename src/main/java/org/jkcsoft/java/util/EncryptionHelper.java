/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2016. through present.
 *
 * Licensed under the following license agreement:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */
package org.jkcsoft.java.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;

/**
 * @author coles
 */
public class EncryptionHelper {
    private static Log log = LogHelper.getLogger(EncryptionHelper.class);

    public static String encrypt(String plainText) throws Exception {
        String md5Hex = DigestUtils.md5Hex(plainText);
        log.debug("Encrypt [" + plainText + "] to [" + md5Hex + "]");
        return md5Hex;
    }


}
