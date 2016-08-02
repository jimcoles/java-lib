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
 * Each colleague knows its Mediator object. Communicates with its mediator whenever it would have otherwise communicated with another colleague.
 *
 * @role __Colleague
 */
public abstract class Colleague {
    private Mediator myMediator;

/**
 * @link
 * @shapeType PatternLink
 * @pattern gof.Mediator
 * @supplierRole Abstract Mediator
 */
/*# private Mediator lnkMediator;*/

    /**
     * Create colleague which knows about supplied mediator
     */
    protected Colleague(Mediator mediator) {
        this.myMediator = mediator;
    }

    /**
     * @return mediator this colleague knows about
     */
    protected Mediator getMediator() {
        return myMediator;
    }

}