package com.ikikyou.practice.model.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * 用户与岗位关联表
 * @TableName sys_user_post
 */
@TableName(value ="sys_user_post")
@Data
public class SysUserPost implements Serializable {
    /**
     * 用户ID
     */
    @TableId
    private Long userId;

    /**
     * 岗位ID
     */
    @TableId
    private Long postId;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}