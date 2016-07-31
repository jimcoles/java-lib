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

import org.jkcsoft.java.systems.contexts.BehavioralContext;

public interface World extends BehavioralContext {
    public Universe getUniverse();
}