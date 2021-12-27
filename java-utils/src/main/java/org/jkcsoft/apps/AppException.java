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

import java.text.Format;

/**
 * Adds these notions:
 * <ul>
 *     <li>Messages that use the Java standard localization and substitution scheme.</li>
 *     <li>An exception code number that apps can use to reference messages and for
 *     more precise exception handling and user support.</li>
 * </ul>
 * @author Jim Coles
 */
public class AppException extends Exception {

    private int errorCodeId;

    public AppException(String msg, Exception cause, Object ... args) {
//        super(String.f);
    }
}
