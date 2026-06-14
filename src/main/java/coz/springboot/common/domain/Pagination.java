package coz.springboot.common.domain;

import java.util.Objects;

import com.github.pagehelper.PageHelper;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Pagination {

    @Schema(description = "Page Number")
    private Integer pageNum;

    @Schema(description = "Page Size")
    private Integer pageSize;

    public void validatePageable() {
        if (Objects.nonNull(pageNum) && pageNum > 0 && Objects.nonNull(pageSize) && pageSize > 0) {
            PageHelper.startPage(pageNum, pageSize);
        }
    }
}
