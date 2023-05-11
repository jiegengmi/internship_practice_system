package com.ikikyou.practice.model.mapper;

import com.ikikyou.practice.model.dto.UserDTO;
import com.ikikyou.practice.model.query.UserQuery;
import com.ikikyou.practice.model.entity.system.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 25726
* @description 针对表【sys_user(用户信息表)】的数据库操作Mapper
* @createDate 2023-04-21 11:07:14
* @Entity com.ikikyou.practice.model.entity.system.SysUser
*/
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<UserDTO> getUserList(UserQuery userQuery);
}




