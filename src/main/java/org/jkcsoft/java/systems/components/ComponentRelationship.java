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

import java.util.Set;

public interface ComponentRelationship {
    public Component getToComponent();

    /**
     * Lower version definitely fail
     */
    public Version getMinVersion();

    /**
     * Higher versions might fail
     */
    public Version getMaxTestedVersion();

    /**
     * Higher version definitely fail
     */
    public Version getHardMaxVersion();

    public Set<Version> getCompatibleVersions();

}
