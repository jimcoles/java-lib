/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2018 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.util;

// List, Vector, Comparable, Collections

/**
 * Does efficient replacement of Strings in an existing String.
 *
 * @author
 * @version 1.0
 */
public class CharReplacer {

    private Pair[] _pairs = null;

    public CharReplacer(String aFindReplace[][]) {
        if (aFindReplace == null)
            throw new IllegalArgumentException("Null or mismatched arrays in CharReplacer.init().");

        _pairs = new Pair[aFindReplace.length];
        for (int idx = 0; idx < aFindReplace.length; idx++) {
            String aPair[] = aFindReplace[idx];
            if (aPair.length != 2)
                throw new IllegalArgumentException("String pair did not have length of 2.");
            char find = aPair[0].charAt(0);
            String replace = aPair[1];
            if (replace == null) replace = "";
            _pairs[idx] = new Pair(find, replace);
        }
    }

    /**
     * The useful method.
     */
    public String replace(String in) {
        String retVal = in;
        if (in != null) {
            int idxInPos = 0;
            int lenIn = in.length();
            StringBuilder sb = new StringBuilder(lenIn);
//      char aIn[] = in.toCharArray();
            // For each char in the 'in' String, see if any of the chars in the
            // 'find' array are a match.
            while (idxInPos < lenIn) {
                char cIn = in.charAt(idxInPos);
                boolean bFound = false;
                for (int idxPair = 0; idxPair < _pairs.length; idxPair++) {
                    Pair pair = _pairs[idxPair];
                    if (cIn == pair.find) {
                        bFound = true;
                        sb.append(pair.replace);
                        idxInPos++;
                        break;
                    }
                }
                if (!bFound) {
                    sb.append(cIn);
                    idxInPos++;
                }
            }
            retVal = sb.toString();
        }
        return retVal;
    }

    private static class Pair implements Comparable {
        public char find = Character.MIN_VALUE;
        public String replace = null;
        public int lenReplace = 0;

        public Pair(char find, String replace) {
            this.find = find;
            this.replace = ((replace == null) ? "" : replace);
            this.lenReplace = this.replace.length();
        }

        public int compareTo(Object obj) {
            if (!(obj instanceof Pair))
                throw new IllegalArgumentException("Arg type must be " + this.getClass().getName());

            Pair pairObj = (Pair) obj;
            return pairObj.find - this.find;
        }
    }
}