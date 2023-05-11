package com.ikikyou.practice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ikikyou.practice.model.query.RoleQuery;
import com.ikikyou.practice.model.entity.system.SysRole;
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

    /**
     * 分页查询所有角色
     *
     * @param roleQuery 查询对象
     */
    Result<Page<SysRole>> getAllRoles(RoleQuery roleQuery);

    /**
     * 新增或者删除一个角色
     *
     * @param role 角色对象
     * @return 新增结果
     */
    Result<Void> insertOrUpdate(SysRole role);

    /**
     * 删除一个角色
     *
     * @param roleId 角色id
     */
    Result<Void> deleteById(Long roleId);

    /**
     * 根据id获取角色
     *
     * @param roleId 角色id
     */
    Result<SysRole> getRoleById(Long roleId);
}
