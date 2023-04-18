package com.ikikyou.practice.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.ikikyou.practice.dto.UserInfoDTO;
import com.ikikyou.practice.entity.system.SysMenu;
import com.ikikyou.practice.entity.system.SysUser;
import com.ikikyou.practice.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 构建用户信息（基本信息、角色、权限）
 * @author ikikyou
 * @date 2023/03/21 11:15
 */
@Slf4j
public abstract class AbstractUserInfo {
    /**
     * 获取用户对应id 的角色集合
     * @param userId 用户id
     */
    protected abstract List<Long> getRoleIds(Long userId);

    /**
     * 获取角色集合所对应的可用菜单（status == 1）
     * @param roleIds 角色集合
     */
    protected abstract List<SysMenu> getMenu(List<Long> roleIds);

    /**
     * 获取用户信息
     * @param username 用户名
     * @return 用户对象
     */
    protected abstract SysUser getSysUser(String username);

    /**
     * 构建用户信息
     *
     * @param username 用户名
     * @return userInfo
     */
    public Result<UserInfoDTO> buildUserInfo(String username) {
        UserInfoDTO userInfo = new UserInfoDTO();
        SysUser sysUser = getSysUser(username);
        if (ObjectUtil.isNull(sysUser)) {
            log.error("登录用户不存在！username ：{}", username);
            return Result.fail("登录用户不存在！");
        }
        userInfo.setUser(sysUser);
        List<Long> roleIds = this.getRoleIds(sysUser.getId());
        if (CollectionUtils.isEmpty(roleIds)) {
            return Result.fail("该用户没有角色！");
        }
        //设置角色权限
        userInfo.setRoleIds(roleIds);
        if (CollectionUtil.isEmpty(roleIds)) {
            userInfo.setPermissions(new HashSet<>());
            return Result.ok();
        }
        List<SysMenu> menuList = this.getMenu(roleIds);
        if (CollectionUtils.isEmpty(menuList)) {
            userInfo.setPermissions(new HashSet<>());
            return Result.ok(userInfo);
        }
        Set<String> permissions = menuList.stream()
                .map(SysMenu::getPermission)
                .filter(StrUtil::isNotBlank)
                .collect(Collectors.toSet());
        userInfo.setPermissions(permissions);
        return Result.ok(userInfo);
    }
}
