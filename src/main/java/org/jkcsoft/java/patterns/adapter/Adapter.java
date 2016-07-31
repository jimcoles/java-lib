/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2016. through present.
 *
 * Licensed under the following license agreement:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.patterns.adapter;


/**
 * This class adapts the interface of Adaptee to the interface of Target
 */
public class Adapter extends Target {
    /**
     * reference to the object being adapted
     */
    private Adaptee adaptee;

/**
 * @link
 * @shapeType PatternLink
 * @pattern gof.Adapter
 * @supplierRole Target
 */
/*# private Target lnkTarget;*/
/**
 * @link
 * @shapeType PatternLink
 * @pattern gof.Adapter
 * @supplierRole Adaptee
 */
/*# private Adaptee lnkAdaptee;*/

    /**
     * Implementation of target method that uses adaptee to perform task
     */
    public void sampleRequest() {
        adaptee.anotherRequest();
    }

    /**
     * @param adaptee class to adapt whis this adapter
     */
    public Adapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

}