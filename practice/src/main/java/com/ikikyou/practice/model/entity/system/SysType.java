package com.ikikyou.practice.model.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 类型表
 * @TableName sys_type
 */
@TableName(value ="sys_type")
@Data
public class SysType implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 4922436325452046361L;
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 业务类型定义
     */
    private String typeDefine;

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
}