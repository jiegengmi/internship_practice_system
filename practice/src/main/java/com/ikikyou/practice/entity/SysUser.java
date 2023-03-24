package com.ikikyou.practice.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName sys_user
 */
@TableName(value ="sys_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysUser implements Serializable {

    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 
     */
    @TableId("id")
    private Long id;

    /**
     * 用户名
     */
    @TableField("name")
    private String name;

    /**
     * 年龄
     */
    @TableField("age")
    private Integer age;

    /**
     * 性别
     */
    @TableField("sex")
    private Integer sex;

    /**
     * 联系方式
     */
    @TableField("tel")
    private String tel;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 
     */
    @TableField("password")
    private String password;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 最后一次修改时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 状态（-1：注销；0，停用；1：启用）
     */
    @TableField("status")
    private Integer status;
}