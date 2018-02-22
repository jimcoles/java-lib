/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2018 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.repos;

import org.jkcsoft.java.util.Beans;

/**
 * @author coles
 */
public class FlexEntity {

    public static final String PROP_oid = "oid";

    private EntityType entityType;

    private Module module;

    private Long oid;


    public Module getModule() {
        return module;
    }

    void setModule(Module module) {
        this.module = module;
    }

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public void setOid(long oid) {
        this.oid = new Long(oid);
    }

    public String toString() {
        String val = Beans.toBeanString(this);
        return val;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }
}
