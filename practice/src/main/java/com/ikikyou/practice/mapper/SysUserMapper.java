package com.ikikyou.practice.mapper;

import com.ikikyou.practice.entity.system.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author ikikyou
* @description 针对表【sys_user】的数据库操作Mapper
* @createDate 2023-03-21 10:02:03
* @Entity com.ikikyou.practice.entity.system.SysUser
*/
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据角色获取用户
     * @param roleId 角色id
     */
    List<SysUser> getUsersByRoleId(Long roleId);
}




