package com.daledev.mockhousingapp.rest.dto;

import java.util.List;

/**
 * @author dale.ellis
 * @since 15/12/2017
 */
public class ResultPageDto<T> {

    private List<T> page;
    private long total;
    private int totalPages;

    public List<T> getPage() {
        return page;
    }

    public void setPage(List<T> page) {
        this.page = page;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
