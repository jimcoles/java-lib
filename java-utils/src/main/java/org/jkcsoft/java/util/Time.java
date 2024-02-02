/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2024 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jim Coles
 */
public class Time {
    
    private static final Map<String, DateTimeFormatter> formatterMemos = new HashMap<>();
    
    public static String format(Instant instant, String pattern) {
        DateTimeFormatter formatter = formatterMemos.computeIfAbsent(
            pattern,
            pattern2 ->
                DateTimeFormatter
                    .ofPattern(pattern2)
                    .withZone(ZoneId.systemDefault())
        );
        return formatter.format(instant);
    }
}
