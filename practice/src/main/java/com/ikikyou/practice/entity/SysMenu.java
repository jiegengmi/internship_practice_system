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
    @TableId
    private Long id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单权限标识
     */
    private String permission;

    /**
     * 父菜单id
     */
    private Long parentId;

    /**
     * 图标
     */
    private String icon;

    /**
     * 前端路由标识路径
     */
    private String path;

    /**
     * 路由名称
     */
    private String component;

    /**
     * 排序值
     */
    private Integer order;

    /**
     * 菜单类型
     */
    private Integer type;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 是否隐藏
     */
    private Integer isHidden;

    /**
     * 状态
     */
    private Integer status;
}