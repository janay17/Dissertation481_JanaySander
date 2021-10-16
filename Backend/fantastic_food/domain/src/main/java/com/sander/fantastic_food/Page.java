package com.sander.fantastic_food;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Page<Type> {
    private List<Type> data = new ArrayList<>();
    private int page;
    private int pageSize;
    private long totalRecords;

    public Page(List<Type> data, int page, int pageSize, long totalRecords) {
        if (data != null) {
            this.data = data;
        }

        this.page = page;
        this.pageSize = pageSize;
        this.totalRecords = totalRecords;
    }

    public List<Type> getData() {
        return Collections.unmodifiableList(data);
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public int previousPage() {
        if (page == 0) {
            return 0;
        }

        return page - 1;
    }

    public int nextPage() {
        if (page * pageSize + pageSize < totalRecords) {
            return page + 1;
        }

        return page;
    }
}
