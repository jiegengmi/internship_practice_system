package com.ikikyou.practice.filter;

import cn.hutool.core.util.ObjectUtil;
import com.ikikyou.practice.constant.SecurityConstants;
import com.ikikyou.practice.model.dto.UserDetail;
import com.ikikyou.practice.utils.DateUtil;
import com.ikikyou.practice.utils.SecurityUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * jwt校验
 * @author ikikyou
 * @date 2023/04/20 09:28
 */
@Slf4j
@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Resource
    private RedisTemplate<String, UserDetail> redisTemplate;

    @Value("${practice.system.jwt.signingKey}")
    private String secret;

    @Value("${practice.system.jwt.expireTime}")
    private Long expireTime;

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain) throws ServletException, IOException {
        UserDetail currUser = getLoginUser(request);
        if (ObjectUtil.isNotNull(currUser) && ObjectUtil.isNull(SecurityUtil.getAuthentication())) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(currUser, null, currUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }

    private UserDetail getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER);
        if (StringUtils.isNotEmpty(token) && token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            token = token.replace(SecurityConstants.TOKEN_PREFIX, "");
        }
        if (StringUtils.isNotEmpty(token)) {
            try {
                Claims claims = Jwts.parser().setSigningKey(secret)
                        .parseClaimsJws(token)
                        .getBody();
                // 解析对应的权限以及用户信息
                String id = (String) claims.get(SecurityConstants.JWT_USER_KEY);
                String userKey = SecurityConstants.JWT_USER_KEY + id;
                return redisTemplate.opsForValue().get(userKey);
            } catch (Exception e) {
                log.warn("校验异常token:" + e.getMessage());
            }
        }
        return null;
    }

    public String createToken(UserDetail userDetail) {
        if (ObjectUtil.isNotEmpty(userDetail)) {
            String token = userDetail.getToken();
            if (expireTime == null) {
                expireTime = DateUtil.getRemainSecondsOneDay();
            }
            redisTemplate.opsForValue().set(userDetail.getToken(), userDetail, expireTime, TimeUnit.SECONDS);
            Map<String, Object> claims = new HashMap<>();
            claims.put(SecurityConstants.JWT_USER_KEY, token);
            return Jwts.builder()
                    .setClaims(claims)
                    .signWith(SignatureAlgorithm.HS512, secret)
                    .compact();
        }
        return null;
    }
}
