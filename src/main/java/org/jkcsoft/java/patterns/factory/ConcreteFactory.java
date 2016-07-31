/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2016. through present.
 *
 * Licensed under the following license agreement:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.patterns.factory;


/**
 * Concrete Factory implements operations of AbstractFactory
 * to create Concrete product objects.
 */
public class ConcreteFactory implements AbstractFactory {
    /**
     * Holds sole instance of the factory
     */
    private static ConcreteFactory instance;

    /**
     * prevents instantiation
     */
    private ConcreteFactory() {
        // prevent creation
    }

    /**
     * Returns an instance of concrete factory.
     *
     * @return the singleton instance
     */
    static public ConcreteFactory getInstance() {
        if (instance == null) {
            instance = new ConcreteFactory();
        }
        return instance;
    }

    /**
     * Creates concrete product AbstractProductImpl
     */
    public AbstractProduct createAbstractProduct() {
        return new AbstractProductImpl();
    }

}