package com.ikikyou.practice.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ikikyou.practice.model.entity.system.SysDept;
import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ikikyou
 * @date 2023/04/21 10:18
 */
@Data
public class DeptTreeDTO {

    private Long id;

    private String label;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<DeptTreeDTO> children;

    public DeptTreeDTO(DeptDTO dept){
        this.id = dept.getDeptId();
        this.label = dept.getDeptName();
        this.children = dept.getChildren() == null ? Collections.emptyList() : dept.getChildren().stream().map(DeptTreeDTO::new).collect(Collectors.toList());
    }
}
