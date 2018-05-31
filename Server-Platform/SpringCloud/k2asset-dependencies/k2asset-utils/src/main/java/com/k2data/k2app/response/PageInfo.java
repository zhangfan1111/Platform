package com.k2data.k2app.response;

import lombok.Data;

/**
 * @author lidong9144@163.com 17-4-18.
 */
@Data
public class PageInfo {

    private Integer pageNum;
    private Integer pageSize;
    private Integer total;
    private Integer pages;

    public PageInfo(Integer pageNum, Integer pageSize, Integer total) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.pages = getPages(pageSize, total);
    }

    private Integer getPages(Integer pageSize, Integer total) {
        if (pageSize >= total) {
            return 1;
        }

        Integer pages = total / pageSize;
        if (pages * pageSize < total) {
            pages = pages + 1;
        }

        return pages;
    }

}
