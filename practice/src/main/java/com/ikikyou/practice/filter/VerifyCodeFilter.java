package com.ikikyou.practice.filter;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikikyou.practice.common.BusinessException;
import com.ikikyou.practice.constant.CacheConstants;
import com.ikikyou.practice.utils.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.*;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.io.PrintWriter;

import static com.ikikyou.practice.constant.CommonConstant.JSON_CONTENT_TYPE;


/**
 * 校验验证码
 *
 * @author ikikyou
 * @date 2023/04/14 11:18
 */
@Component
public class VerifyCodeFilter extends GenericFilterBean {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if ("/login".equals(request.getServletPath())) {
            String uuid = request.getParameter("uuid");
            String code = request.getParameter("code");
            response.setContentType(JSON_CONTENT_TYPE);
            PrintWriter out = response.getWriter();
            Object result = null;
            if (StrUtil.isAllEmpty(uuid)) {
                result = Result.fail("缺少登录参数");
            }
            String redisCode = redisTemplate.opsForValue().get(CacheConstants.CAPTCHA_CODE_KEY + uuid);
            if (StrUtil.isEmpty(redisCode)) {
                result = Result.fail("验证码超时");
            } else if (!code.equals(redisCode)) {
                result = Result.fail("无效的验证码");
            }
            if (result != null) {
                out.write(new ObjectMapper().writeValueAsString(result));
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
