package com.ikikyou.practice.auth;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.ikikyou.practice.constant.SecurityConstants;
import com.ikikyou.practice.model.dto.UserDetail;
import com.ikikyou.practice.model.dto.UserInfoDTO;
import com.ikikyou.practice.model.entity.system.SysUser;
import com.ikikyou.practice.service.UserInfoService;
import com.ikikyou.practice.utils.DateUtil;
import com.ikikyou.practice.utils.Result;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author ikikyou
 * @date 2023/03/21 10:20
 */
@Component(value = "UserDetailsServiceImpl")
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserInfoService userService;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Result<UserInfoDTO> result = userService.getInfoByUserName(username);
        if (!result.isSuccess()) {
            log.warn("账户：{} 校验异常:{}", username, result.getMessage());
            throw new UsernameNotFoundException(result.getMessage());
        }
        Collection<? extends GrantedAuthority> authorities = processRolePermissions(result.getData().getRoleIds(), result.getData().getPermissions());
        SysUser user = result.getData().getUser();
        return new UserDetail(createToken(user), user.getUserId(), username, user.getNickName(), user.getPassword(), authorities);
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

    /**
     * 创建token
     * @param user 当前用户
     * @return token值
     */
    private String createToken(SysUser user){
        String redisKey = SecurityConstants.USER_TOKEN_PREFIX + user.getUserName();
        String token = redisTemplate.opsForValue().get(redisKey);
        if (StrUtil.isNotEmpty(token)) {
            return token;
        }
        String id = IdUtil.randomUUID();
        redisTemplate.opsForValue().set(redisKey, id, DateUtil.getRemainSecondsOneDay(), TimeUnit.SECONDS);
        return id;
    }
}
