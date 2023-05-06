package com.ikikyou.practice.mapper;

import com.ikikyou.practice.dto.UserDTO;
import com.ikikyou.practice.dto.query.UserQueryDTO;
import com.ikikyou.practice.entity.system.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 25726
* @description 针对表【sys_user(用户信息表)】的数据库操作Mapper
* @createDate 2023-04-21 11:07:14
* @Entity com.ikikyou.practice.entity.system.SysUser
*/
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<UserDTO> getUserList(UserQueryDTO userQueryDTO);
}




