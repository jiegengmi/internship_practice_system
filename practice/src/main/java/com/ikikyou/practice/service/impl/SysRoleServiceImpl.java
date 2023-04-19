package com.ikikyou.practice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ikikyou.practice.dto.query.RoleQueryDTO;
import com.ikikyou.practice.entity.system.SysRole;
import com.ikikyou.practice.service.SysRoleService;
import com.ikikyou.practice.mapper.SysRoleMapper;
import com.ikikyou.practice.utils.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 25726
* @description 针对表【sys_role(角色信息表)】的数据库操作Service实现
* @createDate 2023-04-19 15:56:42
*/
@Service
@Slf4j
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService{

    final SysRoleMapper roleMapper;

    @Override
    public Result<List<SysRole>> getRolesByUid(Long uid) {
        if (uid == null) {
            return Result.fail("查询失败");
        }
        return Result.ok(roleMapper.getRoleByUserId(uid));
    }

    @Override
    public Result<List<SysRole>> getAllEnableRoles() {
        return Result.ok(roleMapper.selectList(new LambdaQueryWrapper<SysRole>()
                .eq(SysRole::getStatus, 1)));
    }

    @Override
    public Result<Page<SysRole>> getAllRoles(RoleQueryDTO roleQuery) {
        return null;
    }
}





