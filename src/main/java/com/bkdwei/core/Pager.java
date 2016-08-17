/**
 *
 */
package com.bkdwei.core;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;

/**
 * @author bkd
 * @Date 2016年8月5日
 */
public class Pager {
    private final int     pageNo;
    private final int     pageSize;
    private final List<T> result;
    private final int     rowCount;

    /**
     * @param
     */
    public Pager(final int pageSize, final int pageNo, final int rowCount, final List result) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.rowCount = rowCount;
        this.result = result;

    }
}
