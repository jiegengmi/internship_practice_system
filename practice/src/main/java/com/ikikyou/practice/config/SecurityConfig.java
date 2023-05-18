package com.ikikyou.practice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikikyou.practice.auth.UserDetailsServiceImpl;
import com.ikikyou.practice.constant.CommonConstant;
import com.ikikyou.practice.enums.LogEnum;
import com.ikikyou.practice.model.dto.UserDetail;
import com.ikikyou.practice.filter.JWTAuthFilter;
import com.ikikyou.practice.filter.VerifyCodeFilter;
import com.ikikyou.practice.model.entity.system.SysLog;
import com.ikikyou.practice.model.mapper.SysLogMapper;
import com.ikikyou.practice.utils.IpUtil;
import com.ikikyou.practice.utils.Result;
import com.ikikyou.practice.utils.SecurityUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ikikyou
 * @date 2023/03/21 14:09
 */
@Configuration
@Slf4j
@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfig {

    @Resource
    private UserDetailsServiceImpl userDetailsService;
    @Resource
    private VerifyCodeFilter verifyCodeFilter;
    @Resource
    private JWTAuthFilter jwtAuthFilter;
    @Resource
    private SysLogMapper logMapper;

    @Bean
    public AuthenticationProvider systemAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    /**
     * 安全过滤
     * <a href="https://www.jianshu.com/p/b7c6b0cc87f0">作用参考</a>
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //jwt处理
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                //验证码校验
                .addFilterBefore(verifyCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((auth) -> auth
                        //登录和验证码接口放过
                        .requestMatchers("/captchaImage", "/login").permitAll()
                        .anyRequest().authenticated())
                .authenticationProvider(systemAuthenticationProvider())
                .csrf().disable()
                .cors(Customizer.withDefaults())
                //表单登录
                .formLogin()
                //登录成功或者失败处理
                .successHandler(successLoginHandler()).failureHandler(failLoginHandler());
        return http.build();
    }

    /**
     * 登录成功
     */
    @Bean
    public AuthenticationSuccessHandler successLoginHandler() {
        return (request, response, authentication) -> {
            response.setContentType(CommonConstant.JSON_CONTENT_TYPE);
            UserDetail userDetail = SecurityUtil.getUser(authentication);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("token", jwtAuthFilter.createToken(userDetail));
            //登录日志
            writeLog(request, userDetail);
            response.getOutputStream().write(
                    new ObjectMapper().writeValueAsString(Result.ok(resultMap, "登录成功")).getBytes()
            );
        };
    }

    /**
     * 登录日志
     *
     * @param request    请求
     * @param userDetail 当前登录用户
     */
    private void writeLog(HttpServletRequest request, UserDetail userDetail) {
        // 请求IP
        String ipAddress = IpUtil.getIpAddress(request);
        SysLog sysLog = SysLog.builder()
                .id(System.currentTimeMillis())
                .type(LogEnum.LOGIN_IN.getType())
                .opMethod("/login")
                .userId(userDetail.getUserId())
                .userNickName(userDetail.getNickName())
                .userIp(ipAddress)
                .userIpSource(IpUtil.getIpSource(ipAddress))
                .opUrl(request.getRequestURI())
                .result("登录成功")
                .createTime(new Date())
                .build();
        logMapper.insert(sysLog);
    }

    @Bean
    public AuthenticationFailureHandler failLoginHandler() {
        return ((request, response, exception) -> {
            log.warn(exception.getMessage());
            response.setContentType(CommonConstant.JSON_CONTENT_TYPE);
            Object result;
            if (exception instanceof LockedException) {
                result = Result.fail("账户被锁定");
            } else if (exception instanceof CredentialsExpiredException) {
                result = Result.fail("账户过期");
            } else if (exception instanceof AccountExpiredException) {
                result = Result.fail("密码已过期");
            } else if (exception instanceof DisabledException) {
                result = Result.fail("账户被禁止");
            } else if (exception instanceof BadCredentialsException) {
                result = Result.fail("用户名或者密码错误");
            } else {
                result = Result.fail("发生未知异常！");
            }
            response.getOutputStream().write(new ObjectMapper().writeValueAsString(result).getBytes());
        });
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
