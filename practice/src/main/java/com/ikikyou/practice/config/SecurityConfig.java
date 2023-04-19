package com.ikikyou.practice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikikyou.practice.auth.UserDetailsServiceImpl;
import com.ikikyou.practice.filter.VerifyCodeFilter;
import com.ikikyou.practice.utils.Result;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import static com.ikikyou.practice.constant.CommonConstant.JSON_CONTENT_TYPE;

/**
 * @author ikikyou
 * @date 2023/03/21 14:09
 */
@Configuration
@Slf4j
public class SecurityConfig {

    @Resource
    private UserDetailsServiceImpl userDetailsService;
    @Resource
    private VerifyCodeFilter verifyCodeFilter;

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
     * 登录成功 TODO token处理 ？ jwt还是？
     */
    @Bean
    public AuthenticationSuccessHandler successLoginHandler() {
        return (request, response, authentication) -> {
            response.setContentType(JSON_CONTENT_TYPE);
            response.getOutputStream().write(new ObjectMapper().writeValueAsString(Result.ok("登录成功")).getBytes());
        };
    }

    @Bean
    public AuthenticationFailureHandler failLoginHandler() {
        return ((request, response, exception) -> {
            log.warn(exception.getMessage());
            response.setContentType(JSON_CONTENT_TYPE);
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
