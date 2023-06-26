package com.ikikyou.practice.model.dto;

import com.ikikyou.practice.model.entity.system.SysUser;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * 用户基本信息对象
 *
 * @author ikikyou
 * @date 2023/03/21 10:40
 */
@Data
public class UserInfoDTO {

    /**
     * 用户信息
     */
    private SysUser user;

    /**
     * 角色id集合
     */
    private List<Long> roleIds;

    /**
     * 角色名称集合
     */
    private Set<String> roles;

    /**
     * 权限集合
     */
    private Set<String> permissions;
}
