package com.ikikyou.practice.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ikikyou.practice.dto.query.RoleQueryDTO;
import com.ikikyou.practice.entity.system.SysRole;
import com.ikikyou.practice.service.SysRoleService;
import com.ikikyou.practice.mapper.SysRoleMapper;
import com.ikikyou.practice.utils.ParamUtil;
import com.ikikyou.practice.utils.Result;
import com.ikikyou.practice.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
        Page<SysRole> page = new Page<>(roleQuery.getPageNum(), roleQuery.getPageSize());
        List<SysRole> roleList = roleMapper.getByQueryInfo(roleQuery);
        page.setRecords(roleList);
        page.setTotal(roleList.size());
        return Result.ok(page);
    }

    @Override
    @Transactional
    public Result<Void> insertOrUpdate(SysRole role) {
        if (ObjectUtil.isEmpty(role)) {
            return Result.fail("请求为空");
        }
        if (role.getRoleId() == null) {
            role.setCreateTime(new Date());
            role.setCreateBy(SecurityUtil.getUserName());
        } else {
            role.setUpdateBy(SecurityUtil.getUserName());
            role.setUpdateTime(new Date());
        }
        saveOrUpdate(role);
        return Result.ok("操作成功");
    }

    @Override
    @Transactional
    public Result<Void> deleteById(Long roleId) {
        ParamUtil.checkId(roleId);
        SysRole role = this.getById(roleId);
        if (ObjectUtil.isEmpty(role)) {
            return Result.fail("无效操作");
        }
        role.setDelFlag("1");
        saveOrUpdate(role);
        return Result.ok("删除成功");
    }

    @Override
    public Result<SysRole> getRoleById(Long roleId) {
        ParamUtil.checkId(roleId);
        return Result.ok(getById(roleId));
    }
}





