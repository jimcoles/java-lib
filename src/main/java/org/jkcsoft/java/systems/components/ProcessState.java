/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2016. through present.
 *
 * Licensed under the following license agreement:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */
package org.jkcsoft.java.systems.components;


/**
 * Generalization for process states.  See subtypes for usage.
 *
 * @author coles
 */
public abstract class ProcessState {

    //------------------------------------------------------------------------------
    // Static members
    //------------------------------------------------------------------------------

    private String codeName;
    private String displayName;
    private String imageName;
    private WarningLevel warningLevel;
    private String message;

    //------------------------------------------------------------------------------
    // Constructor(s)
    //------------------------------------------------------------------------------
    protected ProcessState(String name, String displayName, String imageName, WarningLevel level) {
        this.codeName = name;
        this.displayName = displayName;
        this.imageName = imageName;
        this.warningLevel = level;
    }

    //------------------------------------------------------------------------------
    // Instance methods
    //------------------------------------------------------------------------------

    public String getCodeName() {
        return codeName;
    }


    public String getDisplayName() {
        return displayName;
    }


    public String toString() {
        return "name [" + codeName + "]";
    }


    public String getImageName() {
        return imageName;
    }


    public WarningLevel getWarningLevel() {
        return warningLevel;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
