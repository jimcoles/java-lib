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
 * Concrete Colleague
 */
public class ConcreteColleague extends Colleague {
    public ConcreteColleague(Mediator mediator) {
        super(mediator);
    }

    public void sampleOperation() {
        /*some state change occured, notify mediator*/
        getMediator().changed(this);
    }

}