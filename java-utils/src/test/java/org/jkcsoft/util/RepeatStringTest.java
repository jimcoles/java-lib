/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2019 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.util;

import java.util.ArrayList;

/**
 * @author Jim Coles
 */
public class RepeatStringTest {

    // Complete the repeatedString function below.
    static long repeatedString(String s, long totalLength) {
        long count = 0;
        String find = "a";

        int fromIdx = 0;
        ArrayList<Integer> occIdexes = new ArrayList<>();
        while (fromIdx < s.length()) {
            int idxNextOcc = s.indexOf(find, fromIdx);
            if (idxNextOcc > -1)
                occIdexes.add(idxNextOcc);
            else
                break;

            fromIdx = idxNextOcc + 1;
        }

        long numOccsWholeStrings = occIdexes.size() * (totalLength / s.length());
        count = numOccsWholeStrings + numOccsPartialString( occIdexes, totalLength % s.length() );
        return count;
    }

    private static int numOccsPartialString(ArrayList<Integer> occIndexes, long numOverflowChars) {
        int num = 0;
        for (Integer occurence : occIndexes) {
            if (occurence < numOverflowChars)
                num++;
            else
                break;
        }
        return num;
    }

    public static void main(String[] args) {
        testRepeatedString("single a", "a", 12, 12);
        testRepeatedString("simple", "abacab", 10, 5);
        testRepeatedString("left edge", "abacab", 7, 4);
        testRepeatedString("right edge", "abacab", 9, 5);
        testRepeatedString("no remainder", "abacab", 12, 6);
        testRepeatedString("all a's", "aaa", 13, 13);
    }

    private static void testRepeatedString(String name, String shortString, int totalLength, int expected) {
        long occCountTotal = repeatedString(shortString, totalLength);
        System.out.println("Test [" + name + "] base string [" + shortString + "] total length [" + totalLength + "] " +
                               " expected =" + expected + " output: " + occCountTotal +
                               " " + (occCountTotal == expected ? "PASS" : "FAIL *****"));
    }
}
