package com.ikikyou.practice.service;

import com.ikikyou.practice.dto.UserDTO;
import com.ikikyou.practice.dto.query.UserQueryDTO;
import com.ikikyou.practice.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ikikyou.practice.utils.PageResult;

/**
 * @author ikikyou
 * @description 针对表【sys_user】的数据库操作Service
 * @date 2023-03-21 10:02:03
 */
public interface SysUserService extends IService<SysUser> {

    SysUser findByName(String username);

    void insert(UserDTO userDTO);

    PageResult<UserDTO> getUsers(UserQueryDTO userQueryDTO);
}
