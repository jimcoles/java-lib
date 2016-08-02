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

public interface Version extends Comparable {
    int getMajor();

    int getMinor();

    int getRelease();

    int getBuild();
}
