package com.ikikyou.practice.controller;

import com.ikikyou.practice.service.AuthService;
import com.ikikyou.practice.utils.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 校验控制层
 * @author ikikyou
 * @date 2023/04/13 14:05
 */
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 获取验证码
     *
     * @return uuid（验证码redis的key）和验证码
     */
    @GetMapping("/captchaImage")
    private Result<Map<String, Object>> getCaptcha() {
        return authService.getCaptchaImage();
    }
}
