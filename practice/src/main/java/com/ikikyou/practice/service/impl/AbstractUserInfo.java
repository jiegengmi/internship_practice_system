package com.ikikyou.practice.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.ikikyou.practice.dto.UserInfoDTO;
import com.ikikyou.practice.entity.SysMenu;
import com.ikikyou.practice.entity.SysUser;
import com.ikikyou.practice.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author hongx
 * @date 2023/03/21 11:15
 */
@Slf4j
public abstract class AbstractUserInfo {

    protected abstract List<Long> getRoleIds(Long userId);

    protected abstract List<SysMenu> getMenu(List<Long> roleIds);
    protected abstract SysUser getSysUser(String username);

    /**
     * 构建用户信息
     *
     * @param username 用户名
     * @return userInfo
     */
    public Result<UserInfoDTO> buildUserInfo(String username) throws UsernameNotFoundException {
        UserInfoDTO userInfo = new UserInfoDTO();
        SysUser sysUser = getSysUser(username);
        if (ObjectUtil.isNull(sysUser)) {
            log.error("登录用户不存在！username ：{}", username);
            return Result.fail("登录用户不存在！");
        }
        userInfo.setUser(sysUser);
        List<Long> roleIds = this.getRoleIds(sysUser.getId());

        List<SysMenu> menuList = this.getMenu(roleIds);

        //设置角色权限
        userInfo.setRoleIds(roleIds);
        if (CollectionUtils.isEmpty(menuList)) {
            userInfo.setPermissions(new HashSet<>());
            return Result.ok(userInfo);
        }
        Set<String> permissions = menuList.stream()
                .map(SysMenu::getPermission)
                .filter(StrUtil::isNotBlank)
                .collect(Collectors.toSet());
        // 设置权限列表（menu.permission）
        userInfo.setPermissions(permissions);
        return Result.ok(userInfo);
    }
}
