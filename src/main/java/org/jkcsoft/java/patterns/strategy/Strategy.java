/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2016. through present.
 *
 * Licensed under the following license agreement:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.patterns.strategy;


/**
 * Declares an interface common to all supported algorithms.
 * Context uses this interface to call the algorithm defined
 * by a ConcreteStrategy
 *
 * @role __Strategy
 */
public interface Strategy {
    void sample();

}