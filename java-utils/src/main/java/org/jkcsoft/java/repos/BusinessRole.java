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

/**
 * This class generated by a code generation process.
 * <p>
 * RDB Table: BC_BUSINESS_ROLE
 */
public class BusinessRole {
    // useful constants
    public static final String PROP_oid = "oid";
    public static final String PROP_codename = "codename";
    public static final String PROP_description = "description";

    // properties
    private Long oid; // OID
    private String codename; // CODENAME
    private String description; // DESCRIPTION

    // getters and setters
    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public void setOid(long oid) {
        this.oid = new Long(oid);
    }

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // override Object.toString()
    public String toString() {
        String s = "BusinessRole => "
                + " [oid] " + oid
                + " [codename] " + codename
                + " [description] " + description
                + " | Super => " + super.toString();

        return s;
    }
}

  
   
