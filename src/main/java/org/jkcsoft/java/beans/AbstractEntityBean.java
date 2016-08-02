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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An abstract IEntityBean that can be used as the base class for most concrete
 * implementations of IEntityBean.
 *
 * @author Jim Coles
 * @version 1.0
 */
public abstract class AbstractEntityBean implements IEntityBean {
    //---------------------------------------------------------------------------
    // Static stuff
    //---------------------------------------------------------------------------
    private static Map _DISPATCHERS = new HashMap();

    //---------------------------------------------------------------------------
    // Private instance vars
    //---------------------------------------------------------------------------
    private String _lifeState = null;
    private IEntityMeta _meta = null;

    //---------------------------------------------------------------------------
    // Constructor(s)
    //---------------------------------------------------------------------------
    public AbstractEntityBean(ISystemMeta sys)
            throws Exception {
        _meta = sys.getEntityMeta(this.getClass().getName());
    }


    //---------------------------------------------------------------------------
    // Public instance methods
    //---------------------------------------------------------------------------

    // <IEntityBean>

    public void setLifeState(String lifeState) {
        _lifeState = lifeState;
    }

    /**
     * Supports persistence.
     */
    public String getLifeState() {
        return _lifeState;
    }

    /**
     * Validates the object.
     *
     * @return A <code>java.util.List</code> of 1 or more Strings representing distinct
     * object validation error messages suitable for display to the end-user.
     * Null if the object is valid.
     */
    public List validate() {
        List retList = null;
        // TODO: add generic validation logic based on class and field meta data, e.g., non-null fields.
        return retList;
    }

    public Object get(String prop)
            throws Exception {
        return getDispatcher().get(this, prop);
    }

    public void set(String prop, Object value)
            throws Exception {
        getDispatcher().set(this, prop, value);
    }

    public IEntityMeta getMetaData() {
        return _meta;
    }

    // </IEntityBean>

    public IDispatcher getDispatcher()
            throws Exception {
        IDispatcher retObj = null;
        retObj = (IDispatcher) _DISPATCHERS.get(this.getClass());
        if (retObj == null) {
            retObj = new BeanDispatcher(this.getClass());
            _DISPATCHERS.put(this.getClass(), retObj);
        }
        return retObj;
    }

    //---------------------------------------------------------------------------
    // Private instance methods
    //---------------------------------------------------------------------------
    // <java.io.Serializable>
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
    }
    // </java.io.Serializable>
}