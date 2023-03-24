package com.ikikyou.practice.auth;

import cn.hutool.core.util.ArrayUtil;
import com.ikikyou.practice.constant.SecurityConstants;
import com.ikikyou.practice.dto.UserDetail;
import com.ikikyou.practice.dto.UserInfoDTO;
import com.ikikyou.practice.entity.SysUser;
import com.ikikyou.practice.service.UserInfoService;
import com.ikikyou.practice.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author ikikyou
 * @date 2023/03/21 10:20
 */
@Component(value = "UserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    UserInfoService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Result<UserInfoDTO> result = userService.getInfoByUserName(username);
        if (!result.isSuccess()) {
            throw new UsernameNotFoundException("用户不存在");
        }
        Collection<? extends GrantedAuthority> authorities = processRolePermissions(result.getData().getRoleIds(), result.getData().getPermissions());
        SysUser user = result.getData().getUser();
        return new UserDetail(user.getId(), username, user.getPassword(), authorities);
    }

    /**
     * 处理角色和权限
     *
     * @param roleIds    角色集合
     * @param permissions 权限列表
     */
    public Collection<? extends GrantedAuthority> processRolePermissions(List<Long> roleIds, Set<String> permissions) {
        Set<String> dbAuthsSet = new HashSet<>();
        if (ArrayUtil.isNotEmpty(roleIds)) {
            // 获取角色
            roleIds.forEach(roleId -> dbAuthsSet.add(SecurityConstants.ROLE + roleId));
            // 获取资源
            dbAuthsSet.addAll(permissions);
        }
        return AuthorityUtils.createAuthorityList(dbAuthsSet.toArray(new String[0]));
    }
}
