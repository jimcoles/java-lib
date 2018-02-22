/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2018 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.patterns.adapter;


/**
 * This class defines domain-specific interface used by client
 *
 * @role __Target
 */
public abstract class Target {
/**
 * @link
 * @shapeType PatternLink
 * @pattern gof.Adapter
 * @supplierRole Adapter
 */
/*# private Adapter lnkAdapter;*/

    /**
     * This method is called by client when he needs some domain-specific stuff
     */
    public abstract void sampleRequest();

}