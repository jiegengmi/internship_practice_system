package com.ikikyou.practice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikikyou.practice.auth.UserDetailsServiceImpl;
import com.ikikyou.practice.dto.UserDetail;
import com.ikikyou.practice.filter.JWTAuthFilter;
import com.ikikyou.practice.filter.VerifyCodeFilter;
import com.ikikyou.practice.utils.Result;
import com.ikikyou.practice.utils.SecurityUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.util.HashMap;
import java.util.Map;

import static com.ikikyou.practice.constant.CommonConstant.JSON_CONTENT_TYPE;

/**
 * @author ikikyou
 * @date 2023/03/21 14:09
 */
@Configuration
@Slf4j
@EnableWebSecurity
public class SecurityConfig {

    @Resource
    private UserDetailsServiceImpl userDetailsService;
    @Resource
    private VerifyCodeFilter verifyCodeFilter;
    @Resource
    private JWTAuthFilter jwtAuthFilter;

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
            response.setContentType(JSON_CONTENT_TYPE);
            UserDetail userDetail = SecurityUtil.getUser(authentication);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("token", jwtAuthFilter.createToken(userDetail));
            response.getOutputStream()
                    .write(new ObjectMapper().writeValueAsString(Result.ok(resultMap,"登录成功")).getBytes());
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
