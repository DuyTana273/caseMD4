package com.example.demo.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaginationResult {
    private int totalPages;
    private int currentPage;
    private List<Integer> pageNumbers;

    public PaginationResult(int totalPages, int currentPage, List<Integer> pageNumbers) {
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.pageNumbers = pageNumbers;
    }
}
