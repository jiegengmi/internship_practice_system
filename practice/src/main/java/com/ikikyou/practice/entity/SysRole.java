package com.ikikyou.practice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName sys_role
 */
@TableName(value ="sys_role")
@Data
public class SysRole implements Serializable {

    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = -2198140203455691974L;
    /**
     *
     */
    @TableId("r_id")
    private Long id;

    /**
     * 角色名称
     */
    @TableField("r_name")
    private String name;

    /**
     * 角色编码
     */
    @TableField("r_code")
    private Integer code;

    /**
     * 角色描述
     */
    @TableField("r_desc")
    private String desc;

    /**
     * 角色状态（0：未启用；1：启用）
     */
    @TableField("status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
}