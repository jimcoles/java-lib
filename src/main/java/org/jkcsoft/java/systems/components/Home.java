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

/**
 * Framework elements need at least one concrete class to get basic singleton objects
 * such as the Application (interface).  The alternative is to have all objects that need
 * the Application store a reference variable.
 */
public class Home {
    private static Home instance;

    public static Home getHome() {
        if (instance == null) {
            instance = new Home();
        }
        return instance;
    }

    private Application application;

    public Application getTheApplication() {
        return application;
    }
}
