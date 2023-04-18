package com.ikikyou.practice.entity.system;

import com.baomidou.mybatisplus.annotation.*;

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
    private String title;

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
    private Integer orders;

    /**
     * 菜单类型
     */
    private Integer type;

    /**
     * 
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 是否隐藏
     */
    @TableField("is_hidden")
    private Integer isHidden;

    /**
     * 状态
     */
    private Integer status;
}