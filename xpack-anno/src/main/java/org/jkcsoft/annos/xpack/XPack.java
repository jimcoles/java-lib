/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2018 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.annos.xpack;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used primarily to generate a list of classes in a given source package.
 * Useful since Java reflection API does not provide this.
 *
 * @author Jim Coles
 */
@Target(ElementType.PACKAGE)
@Retention(RetentionPolicy.RUNTIME)
public @interface XPack {
    /** The handler takes the list of package classes. */
    Class<? extends XPackHandler> handler() default PackageUtils.DefaultPackageHandler.class;
}
