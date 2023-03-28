package com.ikikyou.practice.dto.query;

/**
 * @author hongx
 * @date 2023/03/27 15:23
 */
public class BaseQueryDTO {

    private int pageNum;

    private int pageSize;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
