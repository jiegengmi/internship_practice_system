package com.ikikyou.practice.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * 角色权限中间表
 * @TableName role_menu_link
 */
@TableName(value ="role_menu_link")
@Data
public class RoleMenu implements Serializable {
    /**
     * 
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * 
     */
    @TableField("menu_id")
    private Long menuId;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}