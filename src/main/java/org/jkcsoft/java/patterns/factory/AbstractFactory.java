/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2018 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.patterns.factory;


/**
 * Abstract factory declares an interface for operations
 * that create abstract product objects.
 *
 * @role __Factory
 */
public interface AbstractFactory {
    /**
     * Creates product
     */
    AbstractProduct createAbstractProduct();

}