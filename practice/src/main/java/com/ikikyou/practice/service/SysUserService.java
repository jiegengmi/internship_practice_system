package com.ikikyou.practice.service;

import com.ikikyou.practice.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author ikikyou
 * @description 针对表【sys_user】的数据库操作Service
 * @date 2023-03-21 10:02:03
 */
public interface SysUserService extends IService<SysUser> {

    SysUser findByName(String username);
}
