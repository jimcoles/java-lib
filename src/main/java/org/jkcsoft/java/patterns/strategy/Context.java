/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2018 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.patterns.strategy;


/**
 * Is configured with a ConcreteStrategy object.
 * Maintains a reference to strategy object.
 * May define an interface that lets Strategy access its data.
 *
 * @role __StrategyContext
 */
public class Context {
    private Strategy strategy;

    /**
     * @link
     * @shapeType PatternLink
     * @pattern gof.Strategy
     * @supplierRole Strategy Abstraction
     */
/*# private Strategy lnkStrategy;*/
    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public void contextRequest() {
        strategy.sample();
    }

}