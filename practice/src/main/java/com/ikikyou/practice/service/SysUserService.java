package com.ikikyou.practice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ikikyou.practice.dto.UserDTO;
import com.ikikyou.practice.dto.UserUpdateDTO;
import com.ikikyou.practice.dto.query.UserQueryDTO;
import com.ikikyou.practice.entity.system.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ikikyou.practice.utils.PageResult;
import com.ikikyou.practice.utils.Result;

/**
 * @author 25726
 * @description 针对表【sys_user(用户信息表)】的数据库操作Service
 * @createDate 2023-04-21 11:07:14
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return 用户实体
     */
    SysUser findByName(String username);

    /**
     * 新增一个用户
     *
     * @param userDTO 用户信息
     */
    Result<Void> insert(UserUpdateDTO userDTO);

    /**
     * 修改用户信息
     *
     * @param userDTO 用户信息
     */
    Result<Void> update(UserUpdateDTO userDTO);

    /**
     * 分页查询用户列表
     *
     * @param userQueryDTO 用户查询对象
     */
    Page<UserDTO> getUserList(UserQueryDTO userQueryDTO);

    /**
     * 根据用户id获取用户信息
     *
     * @param uid 用户id
     */
    Result<UserDTO> getUserById(Long uid);

    /**
     * 根据用户获取用户信息
     *
     * @param username 用户名
     */
    Result<UserDTO> getUserByName(String username);

    /**
     * 删除用户
     *
     * @param id 用户id
     * @return 删除是否成功
     */
    Result<Void> deleteById(Long id);

    /**
     * 获取用户、用户角色、职位
     *
     * @param userId 用户id
     */
    Result<UserUpdateDTO> getUserInfo(Long userId);
}
