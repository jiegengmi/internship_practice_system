package com.ikikyou.practice.model.dto;

import com.ikikyou.practice.model.entity.system.SysPost;
import com.ikikyou.practice.model.entity.system.SysRole;
import com.ikikyou.practice.model.entity.system.SysUser;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@EqualsAndHashCode(callSuper = true)
public class UserUpdateDTO extends SysUser implements Serializable {

    @Serial
    private static final long serialVersionUID = -7837474546991480525L;

    /**
     * 部门ID
     */
    private Long deptId;

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
