/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2016. through present.
 *
 * Licensed under the following license agreement:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */
package org.jkcsoft.java.util;

import java.util.Iterator;
import java.util.Map;

/**
 * @author coles
 */
public class StringReplacer {

    private Map mapFindReplace;

    public StringReplacer(Map mapFindReplace) {
        if (mapFindReplace == null)
            throw new IllegalArgumentException("Null or mismatched arrays in StringReplacer.");

        this.mapFindReplace = mapFindReplace;
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
//          char aIn[] = in.toCharArray();
            // For each char in the 'in' String, see if any of the Strings in the
            // 'find' array are a match.

            while (idxInPos < lenIn) {
                char cIn = in.charAt(idxInPos);
                boolean bFound = false;
//            for(int idxPair = 0; idxPair < numParams; idxPair++) {
                Iterator iPair = mapFindReplace.entrySet().iterator();
                while (iPair.hasNext()) {
                    Map.Entry pair = (Map.Entry) iPair.next();
                    if (in.startsWith((String) pair.getKey(), idxInPos)) {
                        bFound = true;
                        sb.append((String) pair.getValue());
                        idxInPos += ((String) pair.getKey()).length();
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

}
