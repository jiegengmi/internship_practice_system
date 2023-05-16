package com.ikikyou.practice.model.query;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ikikyou
 * @date 2023/05/16 11:23
 */
@Setter
@Getter
public class DeptQuery extends BaseQuery{

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 部父级id
     */
    private Long parentId;
}
