/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2018 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.systems.messages;

/**
 * Error message structure returned within the .Response returned by a call
 * to an .ICommand.
 */
public class Error {
    private String _code = null;
    private String _message = null;

    public Error(String code, String message) {
        _code = code;
        _message = message;
    }

    public String getCode() {
        return _code;
    }

    public String getMessage() {
        return _message;
    }
}