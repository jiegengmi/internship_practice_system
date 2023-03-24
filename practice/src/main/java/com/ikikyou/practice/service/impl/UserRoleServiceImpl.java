package com.ikikyou.practice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ikikyou.practice.entity.UserRole;
import com.ikikyou.practice.service.UserRoleService;
import com.ikikyou.practice.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author 25726
* @description 针对表【user_role_link】的数据库操作Service实现
* @createDate 2023-03-24 12:00:03
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




