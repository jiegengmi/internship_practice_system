package com.ikikyou.practice.model.entity.system;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息表
 * @TableName sys_user
 */
@TableName(value ="sys_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUser implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    @ExcelProperty(value = "用户ID")
    @ColumnWidth(10)
    private Long userId;

    /**
     * 组织ID
     */
    @ExcelProperty(value = "组织ID")
    @ColumnWidth(10)
    private Long deptId;

    /**
     * 用户账号
     */
    @NotNull
    @ExcelProperty(value = "用户名")
    @ColumnWidth(20)
    private String userName;

    /**
     * 用户昵称
     */
    @NotNull
    @ExcelProperty(value = "用户昵称")
    @ColumnWidth(20)
    private String nickName;

    /**
     * 用户类型（00系统用户）
     */
    @ExcelIgnore
    private String userType;

    /**
     * 用户邮箱
     */
    @ExcelProperty(value = "用户邮箱")
    @ColumnWidth(20)
    private String email;

    /**
     * 手机号码
     */
    @ExcelProperty(value = "用户手机号")
    @ColumnWidth(20)
    private String tel;

    /**
     * 用户性别（0男 1女 2未知）
     */
    @ExcelProperty(value = "用户性别")
    @ColumnWidth(20)
    private String sex;

    /**
     * 头像地址
     */
    @ExcelIgnore
    private String avatar;

    /**
     * 密码
     */
    @ExcelIgnore
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    @ExcelIgnore
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @ExcelIgnore
    private String delFlag;

    /**
     * 创建者
     */
    @ExcelIgnore
    private String createBy;

    /**
     * 创建时间
     */
    @ExcelIgnore
    private Date createTime;

    /**
     * 更新者
     */
    @ExcelIgnore
    private String updateBy;

    /**
     * 更新时间
     */
    @ExcelIgnore
    private Date updateTime;

    /**
     * 备注
     */
    @ExcelProperty(value = "用户备注")
    @ColumnWidth(20)
    private String remark;
}