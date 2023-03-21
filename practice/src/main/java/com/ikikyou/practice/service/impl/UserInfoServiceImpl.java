package com.ikikyou.practice.service.impl;

import com.ikikyou.practice.dto.UserInfoDTO;
import com.ikikyou.practice.entity.SysMenu;
import com.ikikyou.practice.entity.SysRole;
import com.ikikyou.practice.entity.SysUser;
import com.ikikyou.practice.mapper.SysMenuMapper;
import com.ikikyou.practice.mapper.SysRoleMapper;
import com.ikikyou.practice.service.SysMenuService;
import com.ikikyou.practice.service.SysRoleService;
import com.ikikyou.practice.service.SysUserService;
import com.ikikyou.practice.service.UserInfoService;
import com.ikikyou.practice.utils.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hongx
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
    protected List<Long> getRoleIds(Long userId) {
        return roleMapper.getRoleByUserId(userId)
                .stream()
                .map(SysRole::getId)
                .collect(Collectors.toList());
    }

    @Override
    protected List<SysMenu> getMenu(List<Long> roleIds) {
        return this.menuMapper.getByRoleIds(roleIds);
    }

    @Override
    protected SysUser getSysUser(String username) {
        return this.userService.findByName(username);
    }

    @Override
    public Result<UserInfoDTO> getInfoByUserName(String username) {
        return this.buildUserInfo(username);
    }
}
