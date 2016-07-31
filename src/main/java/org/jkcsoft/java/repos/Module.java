/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2016. through present.
 *
 * Licensed under the following license agreement:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.repos;

/**
 * @author coles
 */
public class Module {
    /**
     * @clientCardinality 0..*
     * @link aggregation
     * @supplierCardinality 1
     * @supplierRole application
     */

    Application application;

}
