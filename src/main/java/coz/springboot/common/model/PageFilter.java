package coz.springboot.common.model;

import java.util.Objects;

import com.github.pagehelper.PageHelper;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class PageFilter {

    private Integer pageNum;

    private Integer pageSize;

    public void validatePageable() {
        if (Objects.nonNull(pageNum) && pageNum > 0 && Objects.nonNull(pageSize) && pageSize > 0) {
            PageHelper.startPage(pageNum, pageSize);
        }
    }
}
