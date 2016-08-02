/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2016. through present.
 *
 * Licensed under the following license agreement:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */
package org.jkcsoft.java.tools.codegen;

import java.sql.Types;

/**
 * @author coles
 */
public class ColumnInfo {
    String name;
    int dataType;
    int length;
    String jdbcType;
    boolean isNullable;
    String fkToTable; // if non-null, then this col is an FK col and this
    // value contains the 'to' table name in the FK
    boolean isStandardMetaCol;


    /**
     * @param name
     * @param dataType
     * @param length
     * @param precision
     */
    public ColumnInfo(String name, int type, int length, boolean isNullable, String fkToColumn,
                      boolean isStandardMetaCol) {
        super();
        this.name = name;
        this.dataType = type;
        this.length = length;
        this.jdbcType = toJdbcString(type);
        this.isNullable = isNullable;
        this.fkToTable = fkToColumn;
        this.isStandardMetaCol = isStandardMetaCol;
    }

    /**
     * Used to tell OJB what JDBC type name to use for a given Oracle
     * JDBC type.  Could be a driver-specific mapping.
     *
     * @param type
     * @return
     */
    private String toJdbcString(int type) {
        String retVal = null;
        switch (type) {
            case Types.BIT:
                retVal = "BIT";
                break;
            case Types.BIGINT:
                retVal = "BIGINT";
                break;
            case Types.BOOLEAN:
                retVal = "BOOLEAN";
                break;
            case Types.BLOB:
                retVal = "BLOB";
                break;
            case Types.CHAR:
                retVal = "CHAR";
                break;
            case Types.CLOB:
                retVal = "CLOB";
                break;
            case Types.DATE:
                retVal = "DATE";
                break;
            case Types.DOUBLE:
                retVal = "DOUBLE";
                break;
            case Types.DECIMAL:
//            retVal = "DECIMAL";
                retVal = "BIGINT";
                break;
            case Types.FLOAT:
                retVal = "FLOAT";
                break;
            case Types.INTEGER:
                retVal = "INTEGER";
                break;
            case Types.LONGVARCHAR:
                retVal = "LONGVARCHAR";
                break;
            case Types.NUMERIC:
                retVal = "NUMERIC";
                break;
            case Types.REAL:
                retVal = "REAL";
                break;
            case Types.SMALLINT:
                retVal = "SMALLINT";
                break;
            case Types.TIME:
                retVal = "TIME";
                break;
            case Types.TIMESTAMP:
                retVal = "TIMESTAMP";
                break;
            case Types.TINYINT:
                retVal = "TINYINT";
                break;
            case Types.VARCHAR:
                retVal = "VARCHAR";
                break;
            case Types.VARBINARY:
                retVal = "VARBINARY";
                break;
            default:
                break;
        }
        return retVal;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int type) {
        this.dataType = type;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String rdbType) {
        this.jdbcType = rdbType;
    }

    public boolean isReference() {
        return fkToTable != null;
    }


    /**
     * Getter
     */
    public boolean isStandardMetaCol() {
        return isStandardMetaCol;
    }

    /**
     * @return Returns the fkToTable.
     */
    public String getFkToTable() {
        return fkToTable;
    }

    /**
     * @return Returns the isNullable.
     */
    public boolean isNullable() {
        return isNullable;
    }
}
