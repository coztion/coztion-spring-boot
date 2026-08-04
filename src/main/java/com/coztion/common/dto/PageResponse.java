package com.coztion.common.dto;

import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PageResponse<T> {

    @Schema(description = "Page Number")
    private final int pageNum;

    @Schema(description = "Page Size")
    private final int pageSize;

    @Schema(description = "Total Pages")
    private final int pages;

    @Schema(description = "Total Count")
    private final long total;

    @Schema(description = "Data List")
    private final List<T> list;

    public static <T> PageResponse<T> ofPage(Page<T> page) {
        PageInfo<T> pageInfo = PageInfo.of(page);
        return PageResponse.<T>builder()
                .pageNum(pageInfo.getPageNum())
                .pageSize(pageInfo.getPageSize())
                .pages(pageInfo.getPages())
                .total(pageInfo.getTotal())
                .list(pageInfo.getList())
                .build();
    }
}
