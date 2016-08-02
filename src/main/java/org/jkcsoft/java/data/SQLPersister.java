/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2016. through present.
 *
 * Licensed under the following license agreement:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.data;


import org.jkcsoft.java.beans.*;
import org.jkcsoft.java.util.Strings;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Helps hide complexity of SQL for insert, update, and deletes of an IEntityBean
 * from a single RDB table.
 * <p>
 * Contract Notes:
 * <p>
 * This object does not call Connection.commit() or Connection.close().
 * Calling object(s) must do those things.
 * <p>
 * This object does not perform bean validation.  It only tries to perform
 * the proper datastore action with bean data.
 *
 * @author
 * @version 1.0
 */
public class SQLPersister {
    public SQLPersister() {
    }

    /**
     * Loads the bean with data.  Bean must have primary key values set.
     */
    public void load(IEntityBean bean, Connection conn)
            throws Exception {
        IEntityMeta em = bean.getMetaData();
        PropertyList props = em.getPropMetaList();
        StringBuffer sb = new StringBuffer(100); // arbitrary init size=100
        sb.append("SELECT * FROM " + em.getTableName() + " ");
        // append "where pk1=? and pk2=? ..."
        sb.append(" WHERE ");
        PropertyList keys = em.getPKList();
        PropertyListIterator pkli = keys.propIterator();
        int idx1 = 1;
        while (pkli.hasNext()) {
            IPropMeta pm = pkli.next();
            sb.append(pm.getColName() + "=? " + ((idx1 == keys.size()) ? "" : " AND "));
            idx1++;
        }
        String strSql = sb.toString();
        //
        PreparedStatement ps = conn.prepareStatement(strSql);
        idx1 = 1;
        pkli = keys.propIterator();
        while (pkli.hasNext()) {
            IPropMeta pm = pkli.next();
            Object value = DataTypes.java2Jdbc(pm, bean.get(pm.getProgId()));
            if (value == null)
                throw new Exception("Value of primary key property not set. progid='" + pm.getProgId() + "'");
            ps.setObject(idx1, value, pm.getColType());
            idx1++;
        }
        //
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            throw new Exception("Row not found for bean.");
        }
        if (!rs.isLast()) {
            throw new Exception("More than one row found for entity primary key.");
        }

        // Use dispatch method on bean to load from result set...
        PropertyListIterator iProps = props.propIterator();
        while (iProps.hasNext()) {
            IPropMeta pm = iProps.next();
            Object jdbcValue = rs.getObject(pm.getColName());
            bean.set(pm.getProgId(), DataTypes.jdbc2Java(pm, jdbcValue));
        }
    }

    public void persist(IEntityBean bean, Connection conn)
            throws Exception {
        String lifeState = bean.getLifeState();
        if (LifeState.ST_NEW.equals(lifeState)) {
            insert(bean, conn);
        } else if (LifeState.ST_MOD.equals(lifeState)) {
            update(bean, conn);
        } else if (LifeState.ST_DEL.equals(lifeState)) {
            delete(bean, conn);
        } else {
            throw new Exception("Unrecognized life state in " + this.getClass() + ".persist() --> " + lifeState);
        }
    }

    public void insert(IEntityBean bean, Connection conn)
            throws Exception {
        IEntityMeta em = bean.getMetaData();
        PropertyList props = em.getPropMetaList();
        String strSql = "INSERT INTO " + em.getTableName()
                + " (" + props.getColListAsString() + ") VALUES ("
                + Strings.multiplyString("?", props.size(), ",") + ")";
        try {
            PreparedStatement ps = conn.prepareStatement(strSql);
            PropertyListIterator pli = props.propIterator();
            int idx1 = 1;
            while (pli.hasNext()) {
                IPropMeta pm = pli.next();
                Object javaValue = bean.get(pm.getProgId());
                Object jdbcValue = DataTypes.java2Jdbc(pm, javaValue);
                ps.setObject(idx1, jdbcValue, pm.getColType());
                idx1++;
            }
            int nrows = ps.executeUpdate();
            if (nrows != 1)
                throw new Exception("Insert should affect one and only one row.  Affected " + nrows + " rows.  SQL string: " + strSql);
        } catch (SQLException ex) {
            throw ex;
        }
    }

    public void update(IEntityBean bean, Connection conn)
            throws Exception {
        IEntityMeta em = bean.getMetaData();
        PropertyList props = em.getPropMetaList();
        StringBuffer sb = new StringBuffer(100); // arbitrary init size=100
        sb.append("UPDATE " + em.getTableName() + " SET ");
        PropertyListIterator pli = props.propIterator();
        // append "... COL1=?, COL2=?, ..."
        int idx1 = 1;
        while (pli.hasNext()) {
            IPropMeta pm = pli.next();
            sb.append(pm.getColName() + "=? " + ((idx1 == props.size()) ? "" : ", "));
            idx1++;
        }
        // append "where pk1=? and pk2=? ..."
        sb.append(" WHERE ");
        PropertyList keys = em.getPKList();
        PropertyListIterator pkli = keys.propIterator();
        idx1 = 1;
        while (pkli.hasNext()) {
            IPropMeta pm = pkli.next();
            sb.append(pm.getColName() + "=? " + ((idx1 == keys.size()) ? "" : " AND "));
            idx1++;
        }
        String strSql = sb.toString();
        //
        PreparedStatement ps = conn.prepareStatement(strSql);
        //
        pli = props.propIterator();
        idx1 = 1;
        while (pli.hasNext()) {
            IPropMeta pm = pli.next();
            Object javaValue = bean.get(pm.getProgId());
            Object jdbcValue = DataTypes.java2Jdbc(pm, javaValue);
            ps.setObject(idx1, jdbcValue, pm.getColType());
            idx1++;
        }
        pkli = keys.propIterator();
        // Note: Carry existing value of idx1 through next loop, i.e., don't reset.,
        //       since we are tracking the sequence number of the '?'s.
        while (pkli.hasNext()) {
            IPropMeta pm = pkli.next();
            Object javaValue = bean.get(pm.getProgId());
            if (javaValue == null)
                throw new Exception("Value of primary key property not set. progid='" + pm.getProgId() + "'");
            Object jdbcValue = DataTypes.java2Jdbc(pm, javaValue);
            ps.setObject(idx1, jdbcValue, pm.getColType());
            idx1++;
        }
        //
        int nrows = ps.executeUpdate();
        if (nrows > 1) {
            conn.rollback();
            throw new Exception("Update should affect one and only one row.  Affected " + nrows + " rows.  SQL string: " + strSql);
        }
        if (nrows == 0) {
            conn.rollback();
            throw new Exception("Saving of bean did not update any rows.  Apprently, the corresponding row was not found. Check the where clause in the following SQL string: " + strSql);
        }
    }

    /**
     * Deletes one and only one row from the database corresponding to <code>bean</code>.
     * Rolls back transaction and throws exception if number or rows affected is not equal to 1.
     * <p>
     * Caller is responsible for connection commit and close.
     */
    public void delete(IEntityBean bean, Connection conn)
            throws Exception {
        IEntityMeta em = bean.getMetaData();
        PropertyList props = em.getPropMetaList();
        StringBuffer sb = new StringBuffer(100); // arbitrary init size=100
        sb.append("DELETE FROM " + em.getTableName() + " ");
        // append "where pk1=? and pk2=? ..."
        sb.append(" WHERE ");
        PropertyList keys = em.getPKList();
        PropertyListIterator pkli = keys.propIterator();
        int idx1 = 1;
        while (pkli.hasNext()) {
            IPropMeta pm = pkli.next();
            sb.append(pm.getColName() + "=? " + ((idx1 == keys.size()) ? "" : " AND "));
            idx1++;
        }
        String strSql = sb.toString();
        //
        PreparedStatement ps = conn.prepareStatement(strSql);
        idx1 = 1;
        pkli = keys.propIterator();
        while (pkli.hasNext()) {
            IPropMeta pm = pkli.next();
            Object javaValue = bean.get(pm.getProgId());
            if (javaValue == null)
                throw new Exception("Value of primary key property not set. progid='" + pm.getProgId() + "'");
            Object jdbcValue = DataTypes.java2Jdbc(pm, javaValue);
            ps.setObject(idx1, jdbcValue, pm.getColType());
            idx1++;
        }
        //
        int nrows = ps.executeUpdate();
        if (nrows > 1) {
            conn.rollback();
            throw new Exception("Delete should affect one and only one row.  Affected " + nrows + " rows.  SQL string: " + strSql);
        }
        if (nrows == 0) {
            conn.rollback();
            throw new Exception("Delete of bean did not affect any rows.  Apprently, the corresponding row was not found. Check the where clause in the following SQL string: " + strSql);
        }
    }
}