package coz.springboot.common.model;

import java.util.Objects;

import com.github.pagehelper.PageHelper;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import coz.springboot.common.dto.PageRequest;

@Getter
@SuperBuilder
public class PageFilter {

    private Integer pageNum;

    private Integer pageSize;

    public abstract static class PageFilterBuilder<C extends PageFilter, B extends PageFilterBuilder<C, B>> {
        public B page(PageRequest request) {
            this.pageNum(request.getPageNum());
            this.pageSize(request.getPageSize());
            return self();
        }
    }

    public void validatePageable() {
        if (Objects.nonNull(pageNum) && pageNum > 0 && Objects.nonNull(pageSize) && pageSize > 0) {
            PageHelper.startPage(pageNum, pageSize);
        }
    }
}
