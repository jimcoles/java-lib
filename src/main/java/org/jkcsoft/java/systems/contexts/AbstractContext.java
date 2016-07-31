/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2016. through present.
 *
 * Licensed under the following license agreement:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.systems.contexts;

import org.apache.commons.configuration.Configuration;

public class AbstractContext implements BehavioralContext {
    private BehavioralContext parent;

    public Configuration getConfig() {
        // TODO Auto-generated method stub
        return null;
    }

    public BehavioralContext getParentContext() {
        // TODO Auto-generated method stub
        return null;
    }

    public BehavioralContext getParent() {
        return parent;
    }


}
