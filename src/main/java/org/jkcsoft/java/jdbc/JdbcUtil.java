/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2016. through present.
 *
 * Licensed under the following license agreement:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.jdbc;

import org.jkcsoft.java.util.Dates;
import org.jkcsoft.java.util.Strings;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import java.io.Reader;
import java.sql.*;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

public class JdbcUtil {
    /**
     *
     */
    public static java.sql.Date getTodaySQLDate() {
        return new java.sql.Date(System.currentTimeMillis());
    }

    /**
     *
     */
    public static java.sql.Date createSQLDate(String dateStr) {
        java.util.Date d = Dates.toDate(dateStr);
        if (d != null) {
            return new java.sql.Date(d.getTime());
        } else {
            return null;
        }
    }

    public static Timestamp toTimestamp(String strDate, DateFormat formatter) {
        Timestamp retVal = null;
        java.util.Date date = Dates.toDate(strDate, formatter);
        if (date != null)
            retVal = new Timestamp(date.getTime());
        return retVal;
    }

    public static Timestamp toTimestamp(String strDate) {
        Timestamp retVal = null;
        java.util.Date date = Dates.toDate(strDate);
        if (date != null)
            retVal = new Timestamp(date.getTime());
        return retVal;
    }

    /**
     *
     */
    public static String toDateString(Timestamp ts, DateFormat formatter) {
        String retVal = "";
        if (ts != null) {
            retVal = formatter.format(ts);
        }
        return retVal;
    }

    public static String toDateString(Timestamp ts) {
        return toDateString(ts, Dates.FMTR_DEFAULT_SHORT_FORMAT);
    }

    public static Timestamp truncToDayStart(Timestamp timestamp) {
        return toTimestamp(
                toDateString(timestamp, Dates.FMTR_DEFAULT_SHORT_FORMAT),
                Dates.FMTR_DEFAULT_SHORT_FORMAT);
    }

    public static Timestamp add(Timestamp ts, int field, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(ts);
        cal.add(field, amount);
        Timestamp ts2 = new Timestamp(cal.getTime().getTime());
        return ts2;
    }

    /**
     * @return timestamp at midnight for current day (this is the very
     * beginning of a day NOT the end)
     */
    public static Timestamp getTodayTimestamp() {
        Calendar cal = new GregorianCalendar();
        cal.set(GregorianCalendar.HOUR, 0);
        cal.set(GregorianCalendar.HOUR_OF_DAY, 0);
        cal.set(GregorianCalendar.MINUTE, 0);
        cal.set(GregorianCalendar.SECOND, 0);
        cal.set(GregorianCalendar.MILLISECOND, 0);
        long todayInMillis = cal.getTime().getTime();
        Timestamp ts = new Timestamp(todayInMillis);
        return ts;
    }

    /**
     * Convert a JDBC ResultSet to an XML Document based on ResultSet meta
     * data.  Approach used:  create xml element with element name equal to column name and
     * a child CDATA section node for each column. Builds xml Document that will
     * look like this when converted to a string: <br><br>
     * <code>
     * <item-list>
     * <item>
     * <attr1>
     * value
     * </attr1>
     * <attr2>
     * value
     * </attr2>
     * ...
     * </item>
     * <item>
     * ...
     * </item>
     * ...
     * </item-list>
     * </code><br><br>
     * Alternate approaches NOT used:
     * 1. Make each column an Attribute of the Element representing the row/item.<br>
     * 2. Use fixed element names of <name> and <value> with the column name and
     * value as text nodes: <name> colname </name>...<value> thevalue </value>
     */
    public static Document toXMLDoc(
            ResultSet rs,
            String strItemTagName,
            DocumentBuilder db)
            throws Exception {
        Document retDoc = null;
        String strListTagName = strItemTagName + "-list";
        List lColNames = null;
        Element xelemList = null, xelemItem = null, xelemAttr = null;
        // build start of doc
        retDoc = db.newDocument();
        xelemList = retDoc.createElement(strListTagName);
        retDoc.appendChild(xelemList);
        //
        ResultSetMetaData rsm = rs.getMetaData();
        lColNames = buildColList(rsm);
        int ncols = lColNames.size();
        while (rs.next()) {
            xelemItem = retDoc.createElement(strItemTagName);
            xelemList.appendChild(xelemItem);
            for (int idx = 0, idx1 = 1; idx < ncols; idx++, idx1++) {
                xelemAttr = retDoc.createElement(lColNames.get(idx).toString());
                Object oValue = rs.getObject(idx1);
                Node xValue = null;
                if (oValue != null) {
                    String textValue = null;
                    int coltype = rsm.getColumnType(idx + 1);
                    // NOTE: use either CDATA or Text nodes depending on data type.
                    if (coltype == java.sql.Types.CLOB) {
                        Clob clob = (Clob) oValue;
                        textValue = clobToString(clob, "");
                        xValue = retDoc.createCDATASection(textValue);
                    } else if (coltype == java.sql.Types.DATE) {
                        textValue =
                                Long.toString(((java.sql.Date) oValue).getTime());
                        xValue = retDoc.createTextNode(textValue);
                    } else if (coltype == java.sql.Types.TIMESTAMP) {
                        textValue =
                                Long.toString(
                                        ((java.sql.Timestamp) oValue).getTime());
                        xValue = retDoc.createTextNode(textValue);
                    } else if (coltype == java.sql.Types.TIME) {
                        textValue =
                                Long.toString(((java.sql.Time) oValue).getTime());
                        xValue = retDoc.createTextNode(textValue);
                    } else {
                        textValue = Strings.toStringDef(oValue.toString(), "");
                        xValue = retDoc.createCDATASection(textValue);
                    }
                    xelemAttr.appendChild(xValue);
                }
                xelemItem.appendChild(xelemAttr);
            }
        }
        return retDoc;
    }

    public static String getString(ResultSet rs, String name, String ifnull)
            throws SQLException {
        String val = rs.getString(name);
        return ((val != null) ? val : ifnull);
    }

    public static String getClobAsString(
            ResultSet rs,
            String name,
            String ifnull)
            throws Exception {
        String strClob = null;
        Clob clob = rs.getClob(name);
        if (clob != null)
            strClob = clobToString(clob, ifnull);
        return strClob;
    }

    public static String clobToString(Clob clob, String ifnull)
            throws Exception {
        String retVal = null;
        if (clob != null) {
            long pos = 1L;
            int chunksize = 100; // totally arbitrary
            char aChars[] = new char[chunksize];
            StringBuilder sb = new StringBuilder(chunksize);
            Reader reader = clob.getCharacterStream();
            try {
                int num = reader.read(aChars);
                while (num > 0) {
                    sb.append(aChars, 0, num);
                    num = reader.read(aChars);
                }
            } finally {
                reader.close();
            }
            retVal = sb.toString();
        }
        retVal = ((retVal != null) ? retVal : ifnull);
        return retVal;
    }

    public static List buildColList(ResultSetMetaData meta)
            throws SQLException {
        List lCols = new Vector();
        int ncols = meta.getColumnCount();
        for (int idx = 0; idx < ncols; idx++) {
            String colname = meta.getColumnLabel(idx + 1);
            lCols.add((colname == null) ? "(no name)" : colname);
        }
        return lCols;
    }

    public static void safeClose(Connection conn) throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    public static void safeClose(Statement st) throws SQLException {
        if (st != null) {
            st.close();
        }
    }

    public static void safeClose(ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
    }

    public static void safeCommit(Connection conn) throws SQLException {
        if (conn != null) {
            try {
                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
            }
        }
    }
}