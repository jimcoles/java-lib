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

import org.jkcsoft.java.util.Strings;

/**
 * Base for 'meta' entities / model elements
 *
 * @author coles
 */
public class FlexModelElement extends FlexEntity {
    // useful constants
    public static final String PROP_codeName = "codeName";
    public static final String PROP_description = "description";
    public static final String PROP_displayName = "displayName";
    public static final String PROP_creationDate = "creationDate";
    public static final String PROP_lastUpdatedDate = "lastUpdatedDate";
    public static final String PROP_activeFlag = "activeFlag";

    // properties
    private String codeName; // CODE_NAME
    private String description; // DESCRIPTION
    private String displayName; // DISPLAY_NAME
    private java.util.Date creationDate; // CREATION_DATE
    private java.util.Date lastUpdatedDate; // LAST_UPDATED_DATE
    private Long activeFlag; // ACTIVE_FLAG

    // getters and setters
    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public java.util.Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(java.util.Date creationDate) {
        this.creationDate = creationDate;
    }

    public java.util.Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(java.util.Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Long activeFlag) {
        this.activeFlag = activeFlag;
    }

    public void setActiveFlag(long activeFlag) {
        this.activeFlag = new Long(activeFlag);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toIdString() {
        StringBuilder sb = new StringBuilder(Strings.restAfterLast(this.getClass().getName(), "."));
        sb.append(" codeName=" + codeName + " oid=" + getOid());

        return sb.toString();
    }
}
