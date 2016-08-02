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
 * Implements cooperative behavior by coordinating Colleague objects. Knows and maintains its colleagues.
 */
public class ConcreteMediator implements Mediator {
    /**
     * reference to concrete colleague
     */
    private ConcreteColleague aConcreteColleague;

    public void changed(Colleague colleague) {
        /* handle changes of particular colleague */
    }

    public void setConcreteColleague(ConcreteColleague colleague) {
        aConcreteColleague = colleague;
    }

}