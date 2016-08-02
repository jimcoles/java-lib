package org.jkcsoft.java.util;

/*
 * Title:
 * Copyright:    Copyright (c) 2001
 * Company:
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Enables consumer to store and then lookup pairs of associated strings by
 * either the first or second string.  Essentially, a two-column, in-memory
 * RDB table of only strings.
 * Primary usage will be string pairs to achieve a mapping of names, e.g.,
 * - Mapping database column names to programatic attribute identifiers.
 * - Mapping programatic identifiers to attribute display labels.
 *
 * @author Jim Coles
 * @version 1.0
 */
public class BinStringTable {
    //---------------------------------------------------------------------------
    // Instance vars
    //---------------------------------------------------------------------------
    List _table = new Vector(); // basic table of string pair entries
    Map _s1Map = new HashMap(); // index on string 1
    Map _s2Map = new HashMap(); // index on string 2

    //---------------------------------------------------------------------------
    // Constructor(s)
    //---------------------------------------------------------------------------
    public BinStringTable() {
    }

    //---------------------------------------------------------------------------
    //
    //---------------------------------------------------------------------------

    /**
     * Add a tuple of strings.
     */
    public void add(String one, String two)
            throws Exception {
        Entry e = new Entry(one, two);
        _table.add(e);
        _s1Map.put(one, e);
        _s2Map.put(two, e);
    }

    /**
     * Get the string that maps to the provided string. Value of <code>selIndex</code>
     * determines which string in the pair is acting as the 'where'
     * column and which is acting as the selection column.  If <code>selIndex</code>
     * equals 0, return value of first string where second string equals
     * <code>whereVal</code>.  Vice versa, if <code>selIndex</code> equals 1.
     */
    public String get(int selIndex, String whereVal)
            throws Exception {
        String retVal = null;
        int keyIndex = 1 - selIndex;
        if (selIndex > 1 | selIndex < 0) throw new IllegalArgumentException("Index must be 0 or 1.");
        Entry e = null;
        if (keyIndex == 0) {
            e = (Entry) _s1Map.get(whereVal);
        } else {
            e = (Entry) _s2Map.get(whereVal);
        }
        if (e == null) throw new Exception("No key found with value equal to '" + whereVal + "'.");
        retVal = e.get(selIndex);
        return retVal;
    }

    /**
     * Encapsulate a string table entry which is just a pair of strings.
     */
    private static class Entry {
        private String _s1 = null;
        private String _s2 = null;

        public Entry(String s1, String s2) {
            _s1 = s1;
            _s2 = s2;
        }

        public String get(int selIndex) {
            String retVal = null;
            if (selIndex > 1 | selIndex < 0)
                throw new IllegalArgumentException("Index must be 0 or 1.");
            if (selIndex == 0) retVal = _s1;
            if (selIndex == 1) retVal = _s2;
            return retVal;
        }

    }
}