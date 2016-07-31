/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2016. through present.
 *
 * Licensed under the following license agreement:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.patterns.command;


/**
 * Declares an interface for executing an operation
 *
 * @role __Command
 */
public interface Command1 {
    /**
     * this method is called by client to execute this command
     */
    void execute();

}