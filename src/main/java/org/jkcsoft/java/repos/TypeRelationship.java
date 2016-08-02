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
 * RDB Table: BC_TYPE_RELATIONSHIP
 */
public class TypeRelationship extends FlexModelElement {
    // useful constants
    public static final String PROP_requiredFlag = "requiredFlag";
    public static final String PROP_fromEntityTypeOid = "fromEntityTypeOid";
    public static final String PROP_toEntityTypeOid = "toEntityTypeOid";
    public static final String PROP_relationshipTypeOid = "relationshipTypeOid";

    // properties
    private Long requiredFlag; // REQUIRED_FLAG
    private Long fromEntityTypeOid; // FROM_ENTITY_TYPE_OID
    private String fromEntityTypeName; // By Name ref for FROM_ENTITY_TYPE_OID
    private Long toEntityTypeOid; // TO_ENTITY_TYPE_OID
    private String toEntityTypeName; // By Name ref for TO_ENTITY_TYPE_OID
    private Long relationshipTypeOid; // RELATIONSHIP_TYPE_OID
    private String relationshipTypeName; // By Name ref for RELATIONSHIP_TYPE_OID

    // getters and setters
    public Long getRequiredFlag() {
        return requiredFlag;
    }

    public void setRequiredFlag(Long requiredFlag) {
        this.requiredFlag = requiredFlag;
    }

    public void setRequiredFlag(long requiredFlag) {
        this.requiredFlag = new Long(requiredFlag);
    }

    public Long getFromEntityTypeOid() {
        return fromEntityTypeOid;
    }

    public void setFromEntityTypeOid(Long fromEntityTypeOid) {
        this.fromEntityTypeOid = fromEntityTypeOid;
    }

    public void setFromEntityTypeOid(long fromEntityTypeOid) {
        this.fromEntityTypeOid = new Long(fromEntityTypeOid);
    }

    public String getFromEntityTypeName() {
        return fromEntityTypeName;
    }

    public void setFromEntityTypeName(String fromEntityTypeName) {
        this.fromEntityTypeName = fromEntityTypeName;
    }


    public Long getToEntityTypeOid() {
        return toEntityTypeOid;
    }

    public void setToEntityTypeOid(Long toEntityTypeOid) {
        this.toEntityTypeOid = toEntityTypeOid;
    }

    public void setToEntityTypeOid(long toEntityTypeOid) {
        this.toEntityTypeOid = new Long(toEntityTypeOid);
    }

    public String getToEntityTypeName() {
        return toEntityTypeName;
    }

    public void setToEntityTypeName(String toEntityTypeName) {
        this.toEntityTypeName = toEntityTypeName;
    }


    public Long getRelationshipTypeOid() {
        return relationshipTypeOid;
    }

    public void setRelationshipTypeOid(Long relationshipTypeOid) {
        this.relationshipTypeOid = relationshipTypeOid;
    }

    public void setRelationshipTypeOid(long relationshipTypeOid) {
        this.relationshipTypeOid = new Long(relationshipTypeOid);
    }

    public String getRelationshipTypeName() {
        return relationshipTypeName;
    }

    public void setRelationshipTypeName(String relationshipTypeName) {
        this.relationshipTypeName = relationshipTypeName;
    }


}

  
   

