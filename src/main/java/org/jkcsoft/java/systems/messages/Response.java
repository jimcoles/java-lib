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

import java.util.List;

/**
 * A generic response class to support loose coupling of clients to DMAP layer.
 *
 * @author coles
 */
public class Response {
    private List<Error> errors = null;
    private Object data = null;

    public Response() {
    }

    public boolean hasErrors() {
        return (errors != null);
    }

    public List<Error> getErrors() {
        return errors;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void addError(Error error) {
        if (errors == null) {
            errors = new ErrorList();
        }
        errors.add(error);
    }
}
