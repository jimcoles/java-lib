/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2016. through present.
 *
 * Licensed under the following license agreement:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.patterns.mediator;


/**
 * Defines an interface for communicating with Colleague objects.
 *
 * @role __Mediator
 */
public interface Mediator {
/**
 * @link
 * @shapeType PatternLink
 * @pattern gof.Mediator
 * @supplierRole Abstract Colleague
 */
/*# private Colleague lnkColleague;*/

    /**
     * Colleagues calls this method to notify Mediator that something changed
     */
    void changed(Colleague colleague);

}