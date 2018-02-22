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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Use this model to parse command args[]
 * <p>
 * Command Message
 * Parameters ("-name value")
 * Name / Value
 */
public class CommandLine {
    private List _validNames = null;

    /**
     * A dictionary of all the options and their associated values
     */
    private Map _parameters = new HashMap();

    /**
     * Construct an instance of this class with the specified string
     * array.
     *
     * @param args       command line argument
     * @param validNames
     * @throws Exception if parameter names are not in the list
     */
    public CommandLine(String[] args, String[] validNames)
            throws Exception {
        if (validNames != null && validNames.length > 0) {
            _validNames = Arrays.asList(validNames);
        }
        processCommandLine(args);
    }

    /**
     * Default constructor which simply initialised the class
     */
    public CommandLine() {
    }

    /**
     * Check if the following parameter has been specified.
     *
     * @param name name of the parameter
     * @return boolean     true if it has been specified
     */
    public boolean isParameter(String name) {
        return _parameters.containsKey(name);
    }

    /**
     * Return the value of the parameter or option. If the string nominates
     * an option then return null
     *
     * @param name name of option or parameter
     * @return String      value of parameter or null
     */
    public String value(String name) {
        String result = null;

        if (_parameters.containsKey(name)) {
            result = (String) _parameters.get(name);
        }

        return result;
    }

    /**
     * Return the value of the parameter or option, returning a default
     * value if none is specified
     *
     * @param name         name of option or parameter
     * @param defaultValue the default value
     * @return String      value of parameter
     */
    public String value(String name, String defaultValue) {
        String result = value(name);
        return (result != null) ? result : defaultValue;
    }

    /**
     * Add the following option or parameter to the list. An option will
     * have a null value, whereas a parameter will have a non-null value.
     * <p/>
     * This will automatically overwrite the previous value, if one has been
     * specified.
     *
     * @param name  name of option or parameter
     * @param value value of name
     * @return boolean     true if it was successfully added
     */
    public boolean add(String name, String value) throws Exception {
        return add(name, value, true);
    }

    /**
     * Add the following option or parameter to the list. An option will
     * have a null value, whereas a parameter will have a non-null value.
     * <p/>
     * If the overwrite flag is true then this value will overwrite the
     * previous value. If the overwrite flag is false and the name already
     * exists then it will not overwrite it and the function will return
     * false. In all other circumstances it will return true.
     *
     * @param name      name of option or parameter
     * @param value     value of name
     * @param overwrite true to overwrite previous value
     * @return boolean     true if it was successfully added
     */
    public boolean add(String name, String value, boolean overwrite) throws Exception {
        boolean result = false;

        if (!_validNames.contains(name))
            throw new Exception("Invalid command line parameter name '" + name + "'");

        // parameter
        if ((_parameters.containsKey(name)) &&
                (overwrite)) {
            _parameters.put(name, value);
            result = true;
        } else if (!_parameters.containsKey(name)) {
            _parameters.put(name, value);
            result = true;
        }

        return result;
    }

    /**
     * This method processes the command line and extracts the list of
     * options and command lines. It doesn't intepret the meaning of the
     * entities, which is left to the application.
     *
     * @param args command line as a collection of tokens
     */
    private void processCommandLine(String[] args) throws Exception {
        boolean prev_was_hyphen = false;
        String prev_key = null;

        for (int index = 0; index < args.length; index++) {
            if (args[index].startsWith("-")) {
                // if the previous string started with a hyphen then
                // it was an option store store it, without the hyphen
                // in the _switches vector. Otherwise if the previous was
                // not a hyphen then store key and value in the _options
                // hashtable
                if (prev_was_hyphen) {
                    add(prev_key, null);
                }

                prev_key = args[index].substring(1);
                prev_was_hyphen = true;

                // check to see whether it is the last element in the
                // arg list. If it is then assume it is an option and
                // break the processing
                if (index == args.length - 1) {
                    add(prev_key, null);
                    break;
                }
            } else {
                // it does not start with a hyphen. If the prev_key is
                // not null then set the value to the prev_value.
                if (prev_key != null) {
                    add(prev_key, args[index]);
                    prev_key = null;
                }
                prev_was_hyphen = false;
            }
        }
    }

    public Map getParameterMap() {
        return _parameters;
    }

    public List getValidCommandNames() {
        return _validNames;
    }

}
