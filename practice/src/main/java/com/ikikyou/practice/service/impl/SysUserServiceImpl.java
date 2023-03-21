package com.ikikyou.practice.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ikikyou.practice.entity.SysUser;
import com.ikikyou.practice.service.SysUserService;
import com.ikikyou.practice.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
* @author 25726
* @description 针对表【sys_user】的数据库操作Service实现
* @createDate 2023-03-21 10:02:03
*/
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService{

    final SysUserMapper userMapper;

    @Override
    public SysUser findByName(String username) {
        Objects.requireNonNull(username);
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("u_name", username);
        List<SysUser> users = userMapper.selectList(queryWrapper);
        return CollectionUtil.isEmpty(users) ? null : users.get(0);
    }
}




