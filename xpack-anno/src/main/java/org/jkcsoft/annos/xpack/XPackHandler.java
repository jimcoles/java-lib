/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2021 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.annos.xpack;

import java.util.List;

/**
 * @author Jim Coles
 */
public interface XPackHandler {

    void handle(List<Class> packageClasses);
    
}
