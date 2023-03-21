package com.ikikyou.practice.service.impl;

import com.ikikyou.practice.entity.SysUser;
import com.ikikyou.practice.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author hongx
 * @date 2023/03/21 10:20
 */
@RequiredArgsConstructor
@Component(value = "UserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    final SysUserService userService;

    final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userService.findByName(username);
        if (null == user) {
            throw new UsernameNotFoundException("用户不存在");
        }
        Collection<? extends GrantedAuthority> authorities =
                processRolePermissions(user.getData().getRoleName(), user.getData().getPermissions());
        String password = user.getData().getSysUser().getUsrPassword();
        return new org.springframework.security.core.userdetails.User(username, password, authorities);
    }

    /**
     * 处理角色和权限
     *
     * @param roleName    角色名称
     * @param permissions 权限列表
     * @return the GrantedAuthority
     */
    public Collection<? extends GrantedAuthority> processRolePermissions(String roleName, String[] permissions) {
        Set<String> dbAuthsSet = new HashSet<>();
        if (roleName != null) {
            // 获取角色
            dbAuthsSet.add(roleName);
            // 获取资源
            dbAuthsSet.addAll(Arrays.asList(permissions));
        }
        return AuthorityUtils.createAuthorityList(dbAuthsSet.toArray(new String[0]));
    }
}
