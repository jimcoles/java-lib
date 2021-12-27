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
 * Defines an existing interface that needs adapting
 *
 * @role __Adaptee
 */
public class Adaptee {
/**
 * @link
 * @shapeType PatternLink
 * @pattern gof.Adapter
 * @supplierRole Adapter
 */
/*# private Adapter lnkAdapter;*/

    /**
     * Some adaptee-specific behavior
     */
    public void anotherRequest() {
        /* put your specific code here */
    }

}