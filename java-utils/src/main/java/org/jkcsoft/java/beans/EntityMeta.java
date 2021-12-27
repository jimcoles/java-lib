/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2018 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.beans;

/**
 * Generic impl for IEntityMeta.
 *
 * @author Jim Coles
 * @version 1.0
 */
public class EntityMeta implements IEntityMeta {
    //---------------------------------------------------------------------------
    //---------------------------------------------------------------------------
    private String _progId = null;
    private String _tableName = null;

    private PropertyList _proplist = new PropertyList();
    private PropertyList _pklist = new PropertyList();

    //---------------------------------------------------------------------------
    //---------------------------------------------------------------------------
    public EntityMeta(String progId, String tableName) {
        _progId = progId;
        _tableName = tableName;
    }

    //---------------------------------------------------------------------------
    //---------------------------------------------------------------------------
    public String getProgId() {
        return _progId;
    }

    public String getTableName() {
        return _tableName;
    }

    public PropertyList getPropMetaList() {
        return _proplist;
    }

    public PropertyList getPKList() {
        return _pklist;
    }

    public void addProp(IPropMeta pm) {
        _proplist.add(pm);
        if (pm.isPK()) {
            _pklist.add(pm);
        }
    }
}