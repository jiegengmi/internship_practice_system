package com.ikikyou.practice.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.ikikyou.practice.model.dto.UserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author ikikyou
 */
@Slf4j
public final class SecurityUtil {

    /**
     * 判断是否超级管理员
     *
     * @param userId 用户id
     */
    public static boolean isAdmin(Long userId) {
        return null != userId && 1L == userId;
    }

    /**
     * 判断当前用户是否超级管理员
     *
     */
    public static boolean isAdmin(){
        return isAdmin(getUserId());
    }
    /**
     * 获取当前登录用户
     *
     * @return 用户名
     */
    public static String getUserName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetail) {
            username = ((UserDetail) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

    /**
     * 获取当前登录用户id
     *
     * @return 用户名id
     */
    public static Long getUserId() {
        Authentication authentication = getAuthentication();
        assert authentication != null;
        return ObjectUtil.isEmpty(getUser(authentication)) ? null : getUser(authentication).getUserId();
    }

    /**
     * 角色集合
     */
    public static List<Long> getRoles() {
        Authentication authentication = getAuthentication();
        assert authentication != null;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<Long> roleIds = new ArrayList<>();
        authorities.stream()
                .filter((granted) -> StrUtil.startWith(granted.getAuthority(), "ROLE_"))
                .forEach((granted) -> {
                    String id = StrUtil.removePrefix(granted.getAuthority(), "ROLE_");
                    roleIds.add(Long.parseLong(id));
                });
        return roleIds;
    }

    public static Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (ObjectUtil.isEmpty(authentication)) {
            log.error("认证失败，没有认证信息");
            return null;
        } else {
            return authentication;
        }
    }

    public static UserDetail getBaseUser() {
        Authentication authentication = getAuthentication();
        assert authentication != null;
        return getUser(authentication);
    }

    /**
     * 获取自定义用户信息对象
     *
     * @param authentication 认证信息
     */
    public static UserDetail getUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if ("anonymousUser".equals(principal)) {
            principal = null;
        }

        return (UserDetail) principal;
    }

}
