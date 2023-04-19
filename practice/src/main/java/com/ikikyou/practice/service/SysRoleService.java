package com.ikikyou.practice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ikikyou.practice.dto.query.RoleQueryDTO;
import com.ikikyou.practice.entity.system.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ikikyou.practice.utils.Result;

import java.util.List;

/**
* @author 25726
* @description 针对表【sys_role(角色信息表)】的数据库操作Service
* @createDate 2023-04-19 15:56:42
*/
public interface SysRoleService extends IService<SysRole> {

    /**
     * 根据用户id获取对应角色
     * @param uid 用户id
     */
    Result<List<SysRole>> getRolesByUid(Long uid);

    /**
     * 获取所有已启用的角色
     */
    Result<List<SysRole>> getAllEnableRoles();

    Result<Page<SysRole>> getAllRoles(RoleQueryDTO roleQuery);
}
