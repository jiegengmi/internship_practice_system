package com.ikikyou.practice.service;

import com.ikikyou.practice.dto.UserDTO;
import com.ikikyou.practice.dto.query.UserQueryDTO;
import com.ikikyou.practice.entity.system.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ikikyou.practice.utils.PageResult;
import com.ikikyou.practice.utils.Result;

/**
 * @author ikikyou
 * @description 针对表【sys_user】的数据库操作Service
 * @date 2023-03-21 10:02:03
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户实体
     */
    SysUser findByName(String username);

    /**
     * 新增一个用户
     * @param userDTO 用户信息
     */
    Result<Void> insert(UserDTO userDTO);

    Result<Void> update(UserDTO userDTO);
    /**
     * 分页查询用户列表
     * @param userQueryDTO 用户查询对象
     */
    PageResult<SysUser> getUsers(UserQueryDTO userQueryDTO);

    /**
     * 根据用户id获取用户信息
     * @param uid  用户id
     */
    Result<UserDTO> getUserById(Long uid);

    /**
     * 根据用户获取用户信息
     * @param username 用户名
     */
    Result<UserDTO> getUserByName(String username);

    /**
     * 删除用户
     * @param id 用户id
     * @return 删除是否成功
     */
    Result<Void> deleteById(Long id);
}
