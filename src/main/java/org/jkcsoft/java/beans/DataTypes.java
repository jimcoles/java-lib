/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2016. through present.
 *
 * Licensed under the following license agreement:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.beans;

/**
 * @author Jim Coles
 * @version 1.0
 */

import java.sql.Types;

public class DataTypes {
    public static Object java2Jdbc(IPropMeta pm, Object javaDataTypeValue)
            throws Exception {
        Object retJDBCDataTypeValue = javaDataTypeValue;

        if (pm.getColType() == Types.DATE) {
            if (javaDataTypeValue instanceof java.util.Date) {
                java.util.Date jud = (java.util.Date) javaDataTypeValue;
                retJDBCDataTypeValue = new java.sql.Date(jud.getTime());
            }
        }

        return retJDBCDataTypeValue;
    }

    public static Object jdbc2Java(IPropMeta pm, Object jdbcDataTypeValue)
            throws Exception {
        Object retJavaDataTypeValue = jdbcDataTypeValue;

        if (pm.getColType() == Types.DATE) {
            if (jdbcDataTypeValue instanceof java.sql.Date) {
                java.sql.Date jsd = (java.sql.Date) jdbcDataTypeValue;
                retJavaDataTypeValue = new java.util.Date(jsd.getTime());
            }
        }

        return retJavaDataTypeValue;
    }
}