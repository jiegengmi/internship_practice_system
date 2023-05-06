package com.ikikyou.practice.dto;

import com.ikikyou.practice.entity.system.SysPost;
import com.ikikyou.practice.entity.system.SysRole;
import com.ikikyou.practice.entity.system.SysUser;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户新增或者更新时返回对象
 * @author hongx
 * @date 2023/04/23 10:09
 */
@Data
public class UserUpdateDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7837474546991480525L;

    private Long userId;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 用户账号
     */
    @NotNull
    private String userName;

    /**
     * 用户昵称
     */
    @NotNull
    private String nickName;

    /**
     * 用户类型（00系统用户）
     */
    private String userType;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String tel;

    /**
     * 用户性别（0男 1女 2未知）
     */
    private String sex;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private Date loginDate;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 所有角色
     */
    private List<SysRole> roles;

    /**
     * 所有职位
     */
    private List<SysPost> posts;

    /**
     * 当前用户角色
     */
    private List<Long> roleIds;

    /**
     * 当前用户职位
     */
    private List<Long> postIds;
}
