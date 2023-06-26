package com.ikikyou.practice.service.impl;

import com.ikikyou.practice.model.dto.UserInfoDTO;
import com.ikikyou.practice.model.entity.system.SysMenu;
import com.ikikyou.practice.model.entity.system.SysRole;
import com.ikikyou.practice.model.entity.system.SysUser;
import com.ikikyou.practice.mapper.SysMenuMapper;
import com.ikikyou.practice.mapper.SysRoleMapper;
import com.ikikyou.practice.service.SysUserService;
import com.ikikyou.practice.service.UserInfoService;
import com.ikikyou.practice.utils.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ikikyou
 * @date 2023/03/21 11:26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl extends AbstractUserInfo implements UserInfoService {

    final SysUserService userService;
    final SysMenuMapper menuMapper;
    final SysRoleMapper roleMapper;

    @Override
    @Cacheable(cacheNames = "roles")
    public List<SysRole> getRoles(Long userId) {
        return roleMapper.getRoleByUserId(userId);
    }

    @Override
    @Cacheable(cacheNames = "menus")
    public List<SysMenu> getMenu(List<Long> roleIds) {
        return this.menuMapper.getByRoleIds(roleIds);
    }

    @Override
    @Cacheable(cacheNames = "userInfo")
    public SysUser getSysUser(String username) {
        return this.userService.findByName(username);
    }

    @Override
    public Result<UserInfoDTO> getInfoByUserName(String username) {
        return this.buildUserInfo(username);
    }
}
