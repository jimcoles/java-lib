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
 * Concrete Command that executes a sequence of Commands.
 */
public class MacroCommand implements Command1 {
    /**
     * Commands to execute
     */
    private Command1[] commands;

/**
 * @link
 * @shapeType PatternLink
 * @pattern gof.Command
 * @supplierRole Command interface
 */
/*# private Command1 lnkCommand1;*/

    /**
     * @param commands Commands to execute
     */
    public MacroCommand(Command1[] commands) {
        this.commands = commands;
    }

    /**
     * Executes sequence of Commands
     */
    public void execute() {
        if (commands != null) {
            for (int i = 0; i < commands.length; i++) {
                commands[i].execute();
            }
        }
    }

}