package com.pragma.hogar360.servicesvisits.domain.utils.validation;
import com.pragma.hogar360.servicesvisits.domain.model.TimeSlotQueryModel;
public class TimeSlotPaginationRequest {

    private TimeSlotQueryModel query;
    private Integer page;
    private Integer size;
    private String sortBy;
    private String sortDirection;

    public TimeSlotPaginationRequest(TimeSlotQueryModel query, Integer page, Integer size, String sortBy, String sortDirection) {
        this.query = query;
        this.page = page;
        this.size = size;
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
    }

    public TimeSlotQueryModel getQuery() {
        return query;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getSize() {
        return size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setQuery(TimeSlotQueryModel query) {
        this.query = query;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }
}
