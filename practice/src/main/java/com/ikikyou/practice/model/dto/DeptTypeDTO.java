package com.ikikyou.practice.model.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 部门类型
 *
 * @author hongx
 * @date 2023/06/01 16:09
 */
@Data
public class DeptTypeDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -6551503143991018044L;

    private Long id;

    /**
     * 部门（组织机构）类型名称
     */
    private String typeName;

    /**
     * 描述
     */
    private String typeDescription;

    /**
     * 顺序
     */
    private Integer typeOrder;

    /**
     * 类型父级
     */
    private Long typeParent;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 操作人
     */
    private String updateBy;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 子类型
     */
    private List<DeptTypeDTO> children;
}
