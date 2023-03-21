package com.ikikyou.practice.service.impl;

import com.ikikyou.practice.entity.SysMenu;
import com.ikikyou.practice.entity.SysUser;
import com.ikikyou.practice.service.SysMenuService;
import com.ikikyou.practice.service.SysRoleService;
import com.ikikyou.practice.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hongx
 * @date 2023/03/21 11:26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl extends AbstractUserInfo{

    final SysUserService userService;
    final SysMenuService menuService;
    final SysRoleService roleService;

    @Override
    protected List<Long> getRoleIds(Long userId) {
        return null;
    }

    @Override
    protected List<SysMenu> getMenu(List<Long> roleIds) {
        return this.menuService.listByIds(roleIds);
    }

    @Override
    protected SysUser getSysUser(String username) {
        return this.userService.findByName(username);
    }
}
