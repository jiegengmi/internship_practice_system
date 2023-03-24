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
 * 权限
 * @TableName sys_menu
 */
@TableName(value ="sys_menu")
@Data
public class SysMenu implements Serializable {
    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 2752691307856126309L;
    /**
     * 
     */
    @TableId("id")
    private Long id;

    /**
     * 菜单名称
     */
    @TableField("name")
    private String name;

    /**
     * 菜单权限标识
     */
    @TableField("permission")
    private String permission;

    /**
     * 父菜单id
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 前端路由标识路径
     */
    @TableField("path")
    private String path;

    /**
     * 路由名称
     */
    @TableField("component")
    private String component;

    /**
     * 排序值
     */
    @TableField("orders")
    private Integer orders;

    /**
     * 菜单类型
     */
    @TableField("type")
    private Integer type;

    /**
     * 
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 是否隐藏
     */
    @TableField("is_hidden")
    private Integer isHidden;

    /**
     * 状态
     */
    @TableField("status")
    private Integer status;
}