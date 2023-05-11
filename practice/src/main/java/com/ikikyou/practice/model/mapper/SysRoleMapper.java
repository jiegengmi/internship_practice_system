package com.ikikyou.practice.model.mapper;

import com.ikikyou.practice.model.query.RoleQuery;
import com.ikikyou.practice.model.entity.system.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 25726
* @description 针对表【sys_role(角色信息表)】的数据库操作Mapper
* @createDate 2023-04-19 15:56:42
* @Entity com.ikikyou.practice.model.entity.system.SysRole
*/
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 查询角色
     *
     * @param roleQuery 查询对象
     */
    List<SysRole> getByQueryInfo(RoleQuery roleQuery);

    /**
     * 查询用户的角色
     * @param userId 用户id
     */
    List<SysRole> getRoleByUserId(Long userId);

    List<Long> getRoleIdByUserId(Long userId);
}




