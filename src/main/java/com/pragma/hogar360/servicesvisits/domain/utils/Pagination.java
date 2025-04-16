package com.pragma.hogar360.servicesvisits.domain.utils;
import java.util.List;
import java.util.Objects;

public class Pagination<T> {
    private List<T> items;
    private  long totalElements;
    private  int totalPages;
    private  int pageNumber;
    private  int pageSize;
    public Pagination(){}

    public Pagination(List<T> items, long totalElements, int totalPages, int pageNumber, int pageSize) {
        this.items = items;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;

    }


    public List<T> getItems() {
        return items;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pagination<?> that = (Pagination<?>) o;
        return totalElements == that.totalElements &&
                totalPages == that.totalPages &&
                pageNumber == that.pageNumber &&
                pageSize == that.pageSize &&
                Objects.equals(items, that.items);
    }
    @Override
    public int hashCode() {
        return Objects.hash(items, totalElements, totalPages, pageNumber, pageSize);
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
