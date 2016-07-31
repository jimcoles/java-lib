/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2016. through present.
 *
 * Licensed under the following license agreement:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.java.systems.procedures;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;


/**
 * Encapsulates information for a 'page' of a given list of objects.  Used to
 * support pagination of long lists when querying the database and when displaying
 * list results.
 */
public class PageInfo implements Serializable {
    List pagerList = new Vector(); // of int[2]; start and stop limits of page suitable for display
    protected int count;
    protected int numpages;
    protected int pagesize;
    protected int idxThisPage;  // 0-based
    protected String sortCol;
    protected boolean sortAsc;
    protected int idxStart;  // 0-based
    protected int idxEnd;  // 0-based

    public PageInfo(int pagesize, int idxThisPage0Based, String sortCol, boolean sortAsc) {
        this.pagesize = pagesize;
        this.idxThisPage = idxThisPage0Based;
        this.sortCol = sortCol;
        this.sortAsc = sortAsc;
    }

    public int getCount() {
        return count;
    }

    public int getIdxEnd() {
        return idxEnd;
    }

    public int getIdxStart() {
        return idxStart;
    }

    public int getIdxThisPage() {
        return idxThisPage;
    }

    public int getNumpages() {
        return numpages;
    }

    public int getPagesize() {
        return pagesize;
    }

    public boolean isSortAsc() {
        return sortAsc;
    }

    public String getSortCol() {
        return sortCol;
    }

    public List getPagerList() {
        return pagerList;
    }

    /**
     * Allows reset of total after first query
     */
    public void setCount(int count) {
        this.count = count;
        // computer number of pages
        if (count != -1) {
            int rem = count % pagesize;
            numpages = (rem == 0) ? (count / pagesize) : (count / pagesize) + 1;
        }

        // compute start/end for each page
        for (int idxPage = 0; idxPage < (numpages - 1); idxPage++) {
            int start = (idxPage * pagesize) + 1;
            int end = (idxPage + 1) * pagesize;
            pagerList.add(new int[]{start, end});
        }
        if (this.numpages > 0) {
            pagerList.add(new int[]{((numpages - 1) * pagesize) + 1, count});
            int[] currentPageLimits = (int[]) pagerList.get(idxThisPage);
            idxStart = currentPageLimits[0] - 1;
            idxEnd = currentPageLimits[1] - 1;
        }
    }

//    /** Helper */
//    public void setQueryLimits(Query query) throws Exception
//    {
//        if (pagerList != null && idxThisPage < pagerList.size()) {
//            int[] limits = (int[]) this.getPagerList().get(this.idxThisPage);
//            query.setStartAtIndex(limits[0]);
//            query.setEndAtIndex(limits[1] + 1);
//        }
//    }

}
