/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2016. through present.
 *
 * Licensed under the following license agreement:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */
package org.jkcsoft.java.systems.components;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;


/**
 * Basic warning levelNumber: ok, warn, problem
 *
 * @author coles
 */
public class WarningLevel extends ProcessState {

    //------------------------------------------------------------------------------
    // Static members
    //------------------------------------------------------------------------------
    public static final String IMG_OK_GREEN_GIF = "ok-green.gif";
    public static final String IMG_WARN_AMBER_GIF = "warn-amber.gif";
    public static final String IMG_PROBLEM_RED_GIF = "problem-red.gif";

    private static Map levelByIntKey = new TreeMap();
    private static List orderedList = new Vector();

    public static final WarningLevel LEVEL_OK = new WarningLevel("ok", "Normal", IMG_OK_GREEN_GIF, 1);
    public static final WarningLevel LEVEL_WARN = new WarningLevel("warn", "Warning", IMG_WARN_AMBER_GIF, 2);
    public static final WarningLevel LEVEL_PROBLEM = new WarningLevel("problem", "Problem", IMG_PROBLEM_RED_GIF, 3);


    public static WarningLevel getStatusLevelByNum(int iLevel) {
        WarningLevel level = (WarningLevel) levelByIntKey.get(new Integer(iLevel));
        return level;
    }

    public static List getList() {
        return orderedList;
    }

    //------------------------------------------------------------------------------
    // Instance vars
    //------------------------------------------------------------------------------
    private int levelNumber;

    //------------------------------------------------------------------------------
    // Constructor(s)
    //------------------------------------------------------------------------------
    private WarningLevel(String name, String displayName, String imageName, int levelNumber) {
        super(name, displayName, imageName, null);
        this.levelNumber = levelNumber;
        levelByIntKey.put(new Integer(levelNumber), this);
        orderedList.add(this);
    }

    //------------------------------------------------------------------------------
    // Instance methods
    //------------------------------------------------------------------------------

    public int getLevelNumber() {
        return levelNumber;
    }


}
