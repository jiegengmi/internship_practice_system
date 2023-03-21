package com.ikikyou.practice.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
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
public class SysUser implements Serializable {

    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 联系方式
     */
    private String tel;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 
     */
    private String password;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后一次修改时间
     */
    private Date updateTime;

    /**
     * 状态（-1：注销；0，停用；1：启用）
     */
    private Integer status;
}