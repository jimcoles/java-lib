/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2018 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */
package org.jkcsoft.java.systems.components;

import org.apache.commons.configuration.Configuration;
import org.jkcsoft.java.systems.contexts.BehavioralContext;

/**
 * @author Jim Coles
 * @version 1.0
 */
public class GenericApplication implements Application {
    //----------------------------------------------------------------------------
    // Private instance vars
    //----------------------------------------------------------------------------

    //----------------------------------------------------------------------------
    // Constructor(s) (private, package, protected, public)
    //----------------------------------------------------------------------------
    public GenericApplication() {
    }

    //----------------------------------------------------------------------------
    // Public methods - accessors, mutators, other
    //----------------------------------------------------------------------------

    //----------------------------------------------------------------------------
    // Private methods
    //----------------------------------------------------------------------------

    public String getDefaultProductName() {
        return null;
    }

    public String getHomeDir() {
        return null;
    }

    public Configuration getConfig() {
        return null;
    }

    public String getDisplayName() {
        return "comp this";
    }

    public String getCodeName() {
        return "comp-nent";
    }

    //    public Set<Component> getDependencies();
    public Version getVersion() {
        return null;
    }

    public BehavioralContext getParentContext() {
        return null;
    }

}
