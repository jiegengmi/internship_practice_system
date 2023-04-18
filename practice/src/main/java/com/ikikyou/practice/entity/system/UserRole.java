package com.ikikyou.practice.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName user_role_link
 */
@TableName(value ="user_role_link")
@Data
public class UserRole implements Serializable {
    /**
     * 
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 
     */
    @TableField("role_id")
    private Long roleId;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}