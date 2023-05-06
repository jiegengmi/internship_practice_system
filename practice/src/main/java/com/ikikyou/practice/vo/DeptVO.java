package com.ikikyou.practice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ikikyou.practice.entity.system.SysDept;
import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hongx
 * @date 2023/04/21 10:18
 */
@Data
public class DeptVO {

    private Long id;

    private String label;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<DeptVO> children;

    public DeptVO (SysDept dept){
        this.id = dept.getDeptId();
        this.label = dept.getDeptName();
        this.children = dept.getChildren() == null ? Collections.emptyList() : dept.getChildren().stream().map(DeptVO::new).collect(Collectors.toList());
    }
}
