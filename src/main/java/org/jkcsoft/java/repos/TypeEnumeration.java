/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2016. through present.
 *
 * Licensed under the following license agreement:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.repos;

/**
 * This class generated by a code generation process:
 * Gen template: table-2-meta-class.vm
 * Gen date: Thu Aug 04 18:24:52 CDT 2005
 * <p>
 * RDB Table: BC_TYPE_ENUMERATION
 */
public class TypeEnumeration extends FlexModelElement {
    // useful constants
    public static final String PROP_entityTypeOid = "entityTypeOid";
    public static final String PROP_sequenceNumber = "sequenceNumber";

    // properties
    private Long entityTypeOid; // ENTITY_TYPE_OID
    private String entityTypeName; // By Name ref for ENTITY_TYPE_OID
    private Long sequenceNumber; // SEQUENCE_NUMBER

    // getters and setters
    public Long getEntityTypeOid() {
        return entityTypeOid;
    }

    public void setEntityTypeOid(Long entityTypeOid) {
        this.entityTypeOid = entityTypeOid;
    }

    public void setEntityTypeOid(long entityTypeOid) {
        this.entityTypeOid = new Long(entityTypeOid);
    }

    public String getEntityTypeName() {
        return entityTypeName;
    }

    public void setEntityTypeName(String entityTypeName) {
        this.entityTypeName = entityTypeName;
    }


    public Long getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public void setSequenceNumber(long sequenceNumber) {
        this.sequenceNumber = new Long(sequenceNumber);
    }


}

  
   

