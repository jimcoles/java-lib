/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2024 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.util;

import org.jkcsoft.java.util.Time;
import org.junit.Test;

import java.time.Instant;

import static org.jkcsoft.java.testing.TestUtils.out;

/**
 * @author Jim Coles
 */
public class DateTimeTests {
    
    @Test
    public void testTimeFormat() {
        out("formatted time {0}", Time.format(Instant.now(), "HH:mm:ss.SSS"));
    }
}
